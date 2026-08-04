# Spring XML → Annotations: Concept Map (Spring Framework 5.3.39, Java 8)

A recall/revision cheat-sheet mapping the XML-based Spring config concepts to their
annotation-based equivalents. Written for someone who already knows the XML side cold.

> Scope note: This covers **annotation-driven config** (`@Component` + `@Autowired` style,
> plus `@Configuration`/`@Bean` Java-config, since XML `<beans/>` fully disappears only when
> you also drop `<bean/>` in favor of `@Bean` methods). Pure Spring Boot auto-configuration
> is out of scope — this is still vanilla Spring Framework.

---

## 0. Two flavors of "no XML for beans"

There are actually two annotation mechanisms, often used together:

| XML idea | Annotation replacement |
|---|---|
| `<bean id="..." class="..."/>` written by hand for *your own* classes | **Stereotype annotations** on the class itself (`@Component`, `@Service`, `@Repository`, `@Controller`) + `<context:component-scan base-package="..."/>` → `@ComponentScan` |
| `<bean id="..." class="..."/>` for classes you **don't own** (3rd-party libs, need a factory method, etc.) | **`@Bean`** methods inside a **`@Configuration`** class (this *is* the annotation analog of a `<beans/>` XML file) |

Both need one bootstrap difference from XML: instead of `ClassPathXmlApplicationContext`,
you use `AnnotationConfigApplicationContext(AppConfig.class)` (or, if mixing with XML,
`<context:annotation-config/>` / `<context:component-scan/>` inside your XML file).

```java
@Configuration
@ComponentScan("com.example.app")
public class AppConfig { }

// ------

ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
```

---

## 1. Bean definition

| XML | Annotation |
|---|---|
| `<bean id="foo" class="com.x.Foo"/>` | `@Component("foo")` on class `Foo` (discovered via `@ComponentScan`) |
| `<bean class="com.x.Foo"/>` (id auto-generated) | `@Component` (bean name defaults to decapitalized class name, e.g. `foo`) |
| Layered stereotypes for readability (`@Service`, `@Repository`, `@Controller`) are all meta-annotated with `@Component` — functionally identical to `@Component`, just semantic markers. `@Repository` additionally enables exception translation via a `PersistenceExceptionTranslationPostProcessor`. |

For beans you don't own the source of, or that need a factory method / conditional logic:

```java
@Configuration
public class AppConfig {
    @Bean
    public Foo foo() {
        return new Foo();
    }
}
```

`@Bean` is the direct analog of `<bean/>`; the method name becomes the bean id unless
`@Bean("customName")` / `@Bean(name = {"n1","n2"})` is given (parallel to XML's `name` alias attribute).

---

## 2. Eager vs. lazy initialization

| XML | Annotation |
|---|---|
| `BeanFactory` = lazy by default | Same underlying rule; not something annotations change — it's about the container type used. |
| `ApplicationContext` = eager singleton init by default | Same default with `AnnotationConfigApplicationContext`. |
| `<bean lazy-init="true"/>` | `@Lazy` on the `@Component` class, or on the `@Bean` method |
| `<beans default-lazy-init="true">` | `@Lazy` on the whole `@Configuration` class (applies to all `@Bean` methods within it) |

```java
@Component
@Lazy
public class Foo { }
```

`@Lazy` can also decorate an **injection point** (`@Autowired @Lazy private Bar bar;`) to inject
a lazy proxy — there's no direct XML equivalent for that finer-grained usage.

---

## 3. Bean instantiation methods

| XML | Annotation |
|---|---|
| Constructor instantiation: `<bean class="com.x.Foo"/>` | `@Component` on `Foo` — Spring calls the constructor (default, or the one marked `@Autowired` if multiple exist) |
| Static factory method: `<bean class="com.x.FooFactory" factory-method="createFoo"/>` | `@Bean` method in a `@Configuration` class that internally calls the static factory method, e.g. `return FooFactory.createFoo();` |
| Instance factory method: `<bean factory-bean="fooFactory" factory-method="createFoo"/>` | Same idea — `@Bean` method body calls the method on an injected/autowired factory instance: `@Bean public Foo foo(FooFactory f) { return f.createFoo(); }` |

There's no dedicated annotation for "factory method" — `@Bean` subsumes all three XML
instantiation styles because you write the actual instantiation logic in the method body.

---

## 4. Property / constructor injection

| XML | Annotation |
|---|---|
| `<property name="x" ref="bean"/>` / `<property name="x" value="..."/>` (setter injection) | `@Autowired` on the setter method, or field-level `@Autowired` directly on the property (no setter needed) |
| `<constructor-arg ref="..."/>` / `<constructor-arg value="..."/>` | `@Autowired` on the constructor (optional if the class has exactly one constructor — Spring 4.3+ implicit autowiring) |
| `value="..."` for literals | `@Value("...")` on field/setter/constructor param — supports SpEL and `${...}` property placeholders |
| `ref="beanName"` | Just declare the parameter/field type; Spring resolves by type. Use `@Qualifier("beanName")` to pin a specific bean (see §11–14) |

```java
@Component
public class OrderService {
    private final PaymentGateway gateway; // constructor injection
    private Notifier notifier;            // field injection

    @Autowired
    public OrderService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @Autowired
    public void setNotifier(Notifier notifier) { // setter injection
        this.notifier = notifier;
    }
}
```

Field injection (`@Autowired` directly on a field) is the closest "shorthand" — it has **no
direct XML equivalent** since XML always goes through a setter or constructor.

---

## 5. Bean scope

| XML | Annotation |
|---|---|
| `<bean scope="singleton"/>` (default) | `@Scope("singleton")` or nothing (default) |
| `<bean scope="prototype"/>` | `@Scope("prototype")` or `@Scope(BeanDefinition.SCOPE_PROTOTYPE)` |

```java
@Component
@Scope("prototype")
public class Cart { }
```

Applies the same on `@Bean` methods: `@Bean @Scope("prototype") public Cart cart() {...}`.

---

## 6. Bean lifecycle — init/destroy

| XML | Annotation |
|---|---|
| `init-method="init"` / `destroy-method="cleanup"` on `<bean/>` | `@Bean(initMethod = "init", destroyMethod = "cleanup")` on the `@Bean` method (only relevant for `@Bean`-style beans, since for `@Component` classes you typically just call the logic directly in the constructor, or use the callback interfaces/annotations below) |
| `default-init-method` / `default-destroy-method` on `<beans/>` | No direct per-`@Configuration` equivalent; set `initMethod`/`destroyMethod` explicitly per `@Bean`, or implement the callback interfaces below |
| `InitializingBean#afterPropertiesSet()` | **Unchanged** — same interface, works identically regardless of XML/annotation config |
| `DisposableBean#destroy()` | **Unchanged** — same interface |
| JSR-250 `@PostConstruct` / `@PreDestroy` | **Unchanged, same annotations** — and this is where annotation-config gets simpler: you no longer need to manually register `CommonAnnotationBeanPostProcessor` in XML. `@ComponentScan` / `AnnotationConfigApplicationContext` register it (and `AutowiredAnnotationBeanPostProcessor`, `CommonAnnotationBeanPostProcessor`, etc.) automatically as part of the standard annotation-config machinery. You still need the same JARs (`javax.annotation:javax.annotation-api:1.3.2` on Java 8, since JSR-250 isn't in the JDK) and `spring-context` (which pulls the required infra) on the classpath. |

```java
@Component
public class Foo {
    @PostConstruct
    public void init() { }

    @PreDestroy
    public void cleanup() { }
}
```

---

## 7. BeanPostProcessor

**Unchanged interface** — `org.springframework.beans.factory.config.BeanPostProcessor` is
implemented the same way (`postProcessBeforeInitialization` / `postProcessAfterInitialization`).
The only thing that changes is *registration*:

| XML | Annotation |
|---|---|
| `<bean class="com.x.MyBPP"/>` | `@Component` on the `MyBPP` class — as long as it's picked up by `@ComponentScan`, Spring's container recognizes any bean implementing `BeanPostProcessor` and auto-registers it as a processor (same auto-detection rule XML `ApplicationContext` already used). |

No new annotation exists for "this is a BPP" — implementing the interface is the marker,
exactly like in XML.

---

## 8. Bean lifecycle overall (class load → destroy)

Sequence is **identical** regardless of XML vs. annotations — annotations don't change the
lifecycle, only how each step is *declared*:

1. Class loading (static block) — once per JVM, same either way.
2. Instantiation — via constructor / `@Bean` factory method logic (§3).
3. Dependency injection — via `@Autowired` instead of `<property>`/`<constructor-arg>` (§4).
4. All registered BPPs' `postProcessBeforeInitialization` (§7) — unchanged.
5. Init callback — `@PostConstruct` / `InitializingBean` / `@Bean(initMethod=...)` (§6).
6. All registered BPPs' `postProcessAfterInitialization` (§7) — unchanged.
7. Bean ready for use.
8. Destroy callback on container close (not for prototype scope) — `@PreDestroy` /
   `DisposableBean` / `@Bean(destroyMethod=...)` (§6).

---

## 9. Bean definition inheritance

**No annotation equivalent.** Parent/child bean definitions (`<bean id="parent" abstract="true">`
+ `<bean parent="parent">`) are an XML-config-only mechanism for sharing property values between
bean *definitions*. Java/annotation config instead relies on plain **Java inheritance/composition**
of the actual classes (subclass a base class, reuse a helper method inside a `@Bean` method, etc.)
— there's no "definition-level" inheritance construct outside XML.

---

## 10. Collection injection

Same idea, no XML tags needed — just declare the field/param as the collection type and
annotate with `@Autowired`; Spring auto-aggregates **all matching beans** of the element type:

| XML | Annotation |
|---|---|
| `<property name="list"><list>...</list></property>` | `@Autowired private List<Foo> foos;` — injects all `Foo` beans, in an order you can control with `@Order` / `Ordered` on each bean |
| `<array>` | `@Autowired private Foo[] foos;` |
| `<set>` | `@Autowired private Set<Foo> foos;` |
| `<map>` | `@Autowired private Map<String, Foo> foos;` — keys are automatically the **bean names** of each `Foo`-typed bean |
| `<props>` | `@Value` with a `Properties`-typed field sourced from a `.properties` file via `@PropertySource`, or just `@Autowired Map<String,String>` if applicable — there isn't a literal 1:1 `<props>` annotation; `<props>` in XML is really just a `Properties` literal, so on the annotation side you'd typically externalize it into a `.properties`/`.yml` file and bind with `@Value`/`@ConfigurationProperties` rather than hardcode it. |
| `<util:list>`, `<util:set>`, `<util:map>`, `<util:props>` (the `util:` namespace exists purely to let you declare a reusable *standalone* collection bean in XML) | Not needed — declare a `@Bean` method that returns a `List<T>`/`Set<T>`/`Map<K,V>` directly; it becomes an ordinary bean like any other: `@Bean public List<String> countries() { return List.of("IN","US"); }` |

For literal/scalar collections of values (not bean refs), combine `@Value` with SpEL, e.g.
`@Value("#{'${csv.prop}'.split(',')}")`.

---

## 11. Autowiring modes

| XML `autowire=` | Annotation equivalent |
|---|---|
| `"no"` (default in XML) | No `@Autowired` present — you inject manually via constructor/setter args you supply yourself |
| `"default"` | N/A — this just delegates to `<beans default-autowire="...">`; nothing to map |
| `"byType"` | **This is what `@Autowired` does by default** — resolves by declared type |
| `"byName"` | Not a distinct mode with annotations. If there are multiple candidates of the same type, Spring **falls back to matching the field/parameter name against bean names** automatically before failing — but you don't declare "byName" explicitly; it's an implicit tiebreaker. To force it, name your field/setter param to match the target bean's name, or use `@Qualifier("beanName")` (§14) for an explicit, readable equivalent. |
| `"constructor"` | Constructor injection with `@Autowired` on the constructor — Spring resolves each constructor param **by type** the same way `byType` autowiring does for setters |

Key mental model shift: with annotations there's no `<beans default-autowire="byType">` toggle —
**`@Autowired` itself always means "autowire this injection point," and resolution is always
by-type-first**, with by-name only as an ambiguity tiebreaker (§12–14).

---

## 12. Resolving ambiguity — "primary"

| XML | Annotation |
|---|---|
| `<bean primary="true"/>` | `@Primary` on the `@Component` class or `@Bean` method |

```java
@Component
@Primary
public class DefaultPaymentGateway implements PaymentGateway { }
```

When multiple beans of the same type exist and one is `@Primary`, `@Autowired` picks it
without needing a qualifier — identical semantics to XML's `primary="true"`.

---

## 13. Collection/array autowiring with multiple beans (aggregation)

Already covered in §10 — this is the annotation-world default behavior, not something you
opt into. Any `List<T>`/`Set<T>`/`Map<String,T>`/`T[]` injection point automatically collects
**all** beans of type `T` in the context; no special XML-like toggle is needed (XML's
autowiring collapsing multiple candidates into a collection had to be explicit — with
`@Autowired` it's implicit purely from the target type being a collection/array).

---

## 14. "\<qualifier\>" requires "@Qualifier" — now it's just "@Qualifier"

| XML | Annotation |
|---|---|
| `<qualifier value="special"/>` inside `<bean>` **plus** `@Qualifier` on the injection point (your finding: `<qualifier>` alone does nothing without the annotation) | Just `@Qualifier("special")` on both ends: on the `@Component`/`@Bean` (to tag it) and on the injection point (to request it) |

```java
@Component
@Qualifier("special")
public class SpecialFoo implements Foo { }

// ---------

@Component
public class Consumer {
    @Autowired
    @Qualifier("special")
    private Foo foo;
}
```

This confirms/extends what you already found in XML: `@Qualifier` is fundamentally an
**annotation-driven** mechanism — XML's `<qualifier>` tag exists only to *set* the qualifier
value on a bean definition for annotation-based *consumers* to later match against; it was
never a self-sufficient XML-only feature.

---

## Quick reference table

| # | Concept | XML | Annotation |
|---|---|---|---|
| 1 | Bean definition | `<bean class=.../>` | `@Component` (+ `@ComponentScan`) or `@Bean` (+ `@Configuration`) |
| 2 | Lazy init | `lazy-init="true"` / `default-lazy-init` | `@Lazy` |
| 3 | Instantiation | constructor / `factory-method` / `factory-bean` | `@Component` / `@Bean` method body |
| 4 | Injection | `<property>` / `<constructor-arg>` | `@Autowired` (field/setter/constructor), `@Value` for literals |
| 5 | Scope | `scope="singleton|prototype"` | `@Scope(...)` |
| 6 | Init/destroy | `init-method`/`destroy-method`, `InitializingBean`/`DisposableBean`, `@PostConstruct`/`@PreDestroy` | `@Bean(initMethod=,destroyMethod=)`; interfaces & JSR-250 annotations unchanged |
| 7 | BeanPostProcessor | `<bean class="...BPP"/>` | `@Component` on the BPP class |
| 8 | Lifecycle order | same sequence | same sequence |
| 9 | Bean def inheritance | `parent="..."` | **no equivalent** — use Java inheritance |
| 10 | Collections | `<list>/<set>/<map>/<props>`, `<util:*>` | `@Autowired List/Set/Map<...>`, or `@Bean` returning a collection |
| 11 | Autowire modes | `no/default/byName/byType/constructor` | `@Autowired` = byType (+ byName tiebreak); constructor `@Autowired` = constructor mode |
| 12 | Ambiguity | `primary="true"` | `@Primary` |
| 13 | Multi-bean aggregation | autowire into collection | implicit via collection-typed `@Autowired` |
| 14 | Qualifiers | `<qualifier>` + `@Qualifier` | `@Qualifier("...")` on both source and target |

---

### What to explore next
- `@Value` + `@PropertySource` (annotation equivalent of `<context:property-placeholder/>`)
- `@Profile` (equivalent of XML `profile` attribute on `<beans>`)
- `@Import` / `@ImportResource` (composing multiple `@Configuration` classes, or mixing in leftover XML)
- `@DependsOn` (equivalent of XML `depends-on`)
- Spring 4.3+ implicit constructor autowiring (single-constructor classes don't need `@Autowired` at all)
