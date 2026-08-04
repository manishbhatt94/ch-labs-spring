# Study Notes: "@Configuration" Class Proxying, "@Bean" Lite Mode, and Instantiation Styles

## 1. Background: what is a "proxy" in Java?

A proxy is an object that sits in front of a real object, intercepts calls made to it,
and can run extra logic before/after/instead-of forwarding the call to the real thing.
There are two ways the JVM ecosystem builds these:

- **JDK dynamic proxy** — only works when the target class implements an **interface**.
  The proxy is a synthetic class generated at runtime that implements that same
  interface and routes every method call through an `InvocationHandler`.
- **CGLIB (and its modern replacement, ByteBuddy)** — works on a plain **concrete
  class**, no interface required. It generates a runtime **subclass** of the target
  class and overrides its methods to inject interception logic before delegating to
  (or replacing) the original method body.

Spring's `@Configuration` proxying uses the CGLIB approach, since configuration classes
are plain classes, not interfaces.

## 2. Why does "@Configuration" need a proxy at all?

Consider a config class with two `@Bean` methods where one calls the other directly,
in plain Java:

```java
@Configuration
public class AppConfig {
    @Bean
    public Engine engine() {
        return new Engine();
    }

    @Bean
    public Car car() {
        return new Car(engine()); // <-- a direct Java method call
    }
}
```

If `car()`'s call to `engine()` were just a normal Java call, it would execute the
method body again and construct a **second** `Engine` object — completely bypassing
the singleton `engine` bean the container already built. That would silently violate
the default singleton scope the moment two `@Bean` methods reference each other.

Spring solves this by **not registering your literal `AppConfig` class as the bean**.
Instead, at startup it generates a **CGLIB subclass** of `AppConfig`, and that subclass
is what actually gets registered and returned by the container. Every overridden
`@Bean` method on this subclass first checks: *"has this bean already been created and
cached in the container?"* — if yes, it returns the cached singleton instead of running
the original method body; if no, it delegates to the original method body once, and
then caches the result.

This is exactly what my own program output proves:

```
- instantiationConfig [ Class -> com.example.annodemo.config.InstantiationConfig$$EnhancerBySpringCGLIB$$3526c02d ]
```

The bean registered under the name `instantiationConfig` is **not** an instance of
`InstantiationConfig` in the literal sense — it's an instance of a dynamically
generated subclass, `InstantiationConfig$$EnhancerBySpringCGLIB$$3526c02d`. `$$EnhancerBySpringCGLIB$$`
is literally CGLIB's naming convention for a generated proxy/enhancer class. That
proxy class is what makes the "same instance on every direct call" trick work.

## 3. "proxyBeanMethods" — the switch that controls this

`@Configuration` has a `proxyBeanMethods` attribute, `true` by default. This is what
people mean by **"full" `@Configuration` mode** vs **"lite" mode**.

> From the official Javadoc: *"Specify whether `@Bean` methods should get proxied in
> order to enforce bean lifecycle behavior, e.g. to return shared singleton bean
> instances even in case of direct `@Bean` method calls in user code... Turning off
> bean method interception effectively processes `@Bean` methods individually like
> when declared on non-`@Configuration` classes, a.k.a. '`@Bean` Lite Mode'."*

| `proxyBeanMethods` | Config class becomes... | Direct in-code call from one `@Bean` method to another |
|---|---|---|
| `true` (default, "full" mode) | A CGLIB **subclass** of my config class | Intercepted → redirected to the container's singleton cache; no re-instantiation |
| `false` ("lite" mode) | The **literal class itself**, no subclass generated | An ordinary Java method call → re-runs the method body → builds a brand-new, untracked object |

My own output confirms both halves of this table directly:

```
>> @Configuration(proxyBeanMethods = true) class InstantiationConfig -> CGLIB proxy class =
   --> com.example.annodemo.config.InstantiationConfig$$EnhancerBySpringCGLIB$$3526c02d
```

```
>> @Configuration(proxyBeanMethods = false) class InstantiationConfigLiteMode -> NON-CGLIB proxy class =
   --> com.example.annodemo.config.InstantiationConfigLiteMode
```

In "full" mode the resolved class name has the `$$EnhancerBySpringCGLIB$$...` suffix.
In "lite" mode the resolved class name is exactly `InstantiationConfigLiteMode` — the
real class, unmodified, because no proxy was generated at all.

## 4. An important clarification: lite mode does NOT skip the bean lifecycle

It would be easy to assume `proxyBeanMethods=false` means "these beans are less fully
managed by Spring" in some general sense — lifecycle callbacks skipped, no
`BeanPostProcessor` treatment, etc. **That is not what happens.**

Every `@Bean` method, **when invoked by the container itself** (i.e. Spring calling it
via reflection while assembling the `ApplicationContext`, once per singleton bean),
*always* gets the complete treatment regardless of `proxyBeanMethods` — constructed
once, passed through registered `BeanPostProcessor`s, `@PostConstruct` fired, and
properly registered in the bean registry under its bean name. **This part is
unaffected by the flag.**

The *only* thing `proxyBeanMethods=false` changes is what happens when **one `@Bean`
method calls another `@Bean` method directly in Java code, from inside the same
class**. In full mode that call is intercepted and rerouted to the container's cache.
In lite mode it's just... a method call, like any other Java method call, and it
re-executes the body.

So the accurate mental model is:

- Container-invoked `@Bean` method executions → always fully managed, in both modes.
- Direct, in-code, method-to-method calls between `@Bean` methods on the *same
  config class* → only container-managed in full mode; plain, untracked, and
  singleton-breaking in lite mode.

This is exactly why the Javadoc recommends lite mode only when a config class's
`@Bean` methods are "self-contained... plain factory method[s] for container use" —
i.e., they don't call each other. The moment they do, lite mode silently breaks
singleton semantics for that specific call path, which is a subtle bug class (IDE
inspections exist specifically to flag this pattern in lite-mode/`@Component`-declared
`@Bean` methods).

## 5. Reading my own output as proof, line by line

### Part A — full mode ("proxyBeanMethods=true")

```
[instantiation] WidgetInstanceFactory constructed (identityHash=1898220577)
[instantiation] WidgetInstanceFactory#createWidget() invoked
```
Only **one** `WidgetInstanceFactory constructed` line appears, even though the code
has two separate `@Bean` methods: one that builds `widgetInstanceFactory` directly,
and another (`widgetFromInstanceFactory`) that calls `widgetInstanceFactory()` inside
its own body to get a reference to that same factory. If this were a plain Java call,
a second constructor line would appear. It doesn't — proof the call was intercepted
and redirected to the cached singleton.

```
>> Proof the factory itself stayed a true singleton across both direct-lookup
   and in-code self-call:
   identityHash of the registered 'widgetInstanceFactory' bean = 1898220577
   which matches the identityHash printed in the 'WidgetInstanceFactory constructed' line above.
```

`1898220577` matches the identity hash from the earlier "constructed" line exactly —
confirming `ctx.getBean("widgetInstanceFactory", ...)` and the object used internally
by `widgetFromInstanceFactory()`'s `@Bean` method are the literal same object in
memory.

### Part B — lite mode (`proxyBeanMethods=false`)

```
[instantiation] WidgetInstanceFactory constructed (identityHash=758013696)
[instantiation] WidgetInstanceFactory constructed (identityHash=1279309678)
```
Two separate constructor calls, two different identity hashes. Here's what produced
each:

1. The container invoking the `widgetInstanceFactory()` `@Bean` method directly →
   registered as the `widgetInstanceFactory` bean.
2. The container invoking the `anotherBeanThatCallsFactory()` `@Bean` method → whose
   body calls `widgetInstanceFactory()` again, but since lite mode disabled
   interception, this is now an ordinary Java call → re-runs the method body → builds
   a second, uncontrolled `WidgetInstanceFactory` object that becomes the value
   registered under the `anotherBeanThatCallsFactory` bean name.

```
>> registeredBean  identityHash = 758013696
>> viaSelfCall     identityHash = 1279309678
>> same object?    false   <-- singleton guarantee is BROKEN for this call path under lite mode
```
The two bean names resolve to genuinely different objects. Both are still real,
fully-registered Spring beans (each has its own bean name, `widgetInstanceFactory` and
`anotherBeanThatCallsFactory`, and both appear in `ctx.getBeanDefinitionNames()`) — but
the *intended* singleton-sharing between them, expressed as an in-code method call,
silently failed.

## 6. Mapping back to the three XML instantiation styles

This detour into proxying came up while comparing XML's three ways to declare how a
bean gets built, against `@Bean`:

| XML | Annotation equivalent |
|---|---|
| `<bean class="com.x.Foo"/>` (plain constructor instantiation) | `@Component` on `Foo`, or a trivial `@Bean` method that just does `return new Foo();` |
| `<bean class="com.x.FooFactory" factory-method="createFoo"/>` (static factory method) | A `@Bean` method whose body calls a plain **static** method: `return FooFactory.createFoo();` — no proxying involved, since static calls aren't inter-bean references |
| `<bean factory-bean="fooFactory" factory-method="createFoo"/>` (instance factory method) | A `@Bean` method whose body calls an **instance** method on another `@Bean`-produced object obtained via a direct in-code call to a sibling `@Bean` method — **this is precisely the case that needs `proxyBeanMethods=true` to behave correctly**, since it's an inter-bean reference expressed as a plain Java call |

The instance-factory-method style is the one instantiation pattern that quietly
depends on `@Configuration`'s CGLIB proxying to preserve singleton semantics — which
is what makes it a natural jumping-off point into this whole proxying topic.

## 7. Summary / mental checklist

- `@Configuration` classes are, by default, CGLIB-**subclassed** at startup — the
  bean registered in the container is an instance of a generated subclass, visible in
  stack traces / `getClass()` output as `...$$EnhancerBySpringCGLIB$$<hash>`.
- This subclassing exists so that **direct calls between `@Bean` methods in the same
  class** resolve to the container's cached singleton instead of re-running the
  method body.
- `proxyBeanMethods=false` ("lite mode") disables this subclassing. The config class
  is registered as its literal, unmodified self.
- Lite mode does **not** disable the bean lifecycle for beans the container itself
  constructs — only for objects born from **direct in-code calls between `@Bean`
  methods**, which become ordinary, untracked Java objects instead of shared
  singletons.
- Rule of thumb: use lite mode only when a config class's `@Bean` methods are fully
  independent of each other; if they reference each other via direct calls, keep the
  default (`proxyBeanMethods=true`), or better, pass dependencies in as method
  parameters instead of via direct self-calls (letting Spring inject them, which
  works correctly under either mode).
