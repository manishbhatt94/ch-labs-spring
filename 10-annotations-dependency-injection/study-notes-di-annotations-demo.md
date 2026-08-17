# Study Notes — Spring DI (Annotations) Demo

Brief reference notes across all 7 `Main` classes. Companion to the project's
own `README.md` (setup/structure) — this file is about *what was actually
learned*, especially the parts that aren't obvious from the code alone.

---


## Main01 — @Autowired Injection Styles & Constructor Rules

- Four valid `@Autowired` targets: constructor, field, setter, and any
  **arbitrary method** — Spring doesn't care about method naming conventions;
  a multi-arg method with a made-up name works exactly like a setter.
- **Since Spring 4.3**: a class with exactly **one constructor** gets it
  autowired implicitly — no `@Autowired` needed at all.
- A class with **multiple constructors** and no annotation is ambiguous;
  exactly one must be marked `@Autowired` (default `required=true`).
- Multiple constructors **all** marked `@Autowired(required=false)`: Spring
  picks the constructor whose dependencies are **most fully satisfiable** by
  beans actually present in the container — not simply "the first one" or
  "the one with the most parameters" in the abstract, but the best *match*.
- Corollary (confirmed in Main05): only **one** constructor may declare
  `required=true`. If more than one constructor carries `@Autowired`, every
  one of them must be `required=false`.


## Main02 — Simple vs. Complex Types, Arrays, @Bean-to-@Bean Wiring

- `@Value` → simple/literal values. `@Autowired` (by type) → complex/bean
  reference types. Different mechanisms, not just different annotations.
- **Array of a complex type** (e.g. `AudioDevice[]`): Spring auto-aggregates
  *all* matching beans into it. This is a multi-valued injection point, so
  having 3+ candidate beans is **not** an ambiguity error — ambiguity
  resolution (`@Primary`/`@Qualifier`) is a single-value-only concept
  (confirmed again in Main04).
- **Array of a simple type** (e.g. `String[]`): Strings can't be
  `@Component`-scanned, so this has to be one explicit `@Bean` method
  returning the array type. The injection point then receives that **one**
  bean directly — this is ordinary single-bean injection, *not* aggregation,
  even though the type happens to be an array.
- `@Bean`-to-`@Bean` dependency wiring — two styles:
  - **Direct call**: `return new Car(engine());`
  - **Parameter injection**: `car(Engine engine) { return new Car(engine); }`
- Default (**full**) `@Configuration` mode CGLIB-proxies the class, so a
  direct in-body call to another `@Bean` method is intercepted and redirected
  to the container — both styles are safe and singleton-consistent here.
- `@Configuration(proxyBeanMethods = false)` (**lite mode**): no CGLIB
  subclass exists, so a direct call is just a plain Java method call —
  **silently creates a brand-new, non-singleton instance every time**.
  Parameter injection is unaffected either way (resolved via normal
  container autowiring, not by intercepting a method call).
  - **Rule of thumb**: prefer parameter injection for inter-bean deps
    whenever `proxyBeanMethods=false` is in play, since it's correct
    regardless of proxy mode.


## Main03 — Collection Injection, Aggregation, Ordering

- `List<T>`/`Set<T>`/`Map<String,T>`/array injection points **aggregate all
  matching beans** automatically. Contrast with a `@Bean` method whose
  *return type itself* is `List<T>`/array — that registers exactly **one**
  bean of that collection type, injected directly, not an aggregation of
  several beans.
- With no `@Order`/`Ordered`, aggregation order follows bean **registration**
  order — this is *not* a documented/guaranteed contract, just an observed
  side effect. Don't rely on it in real code.
- `@Order` (class-level annotation) and `Ordered` (interface, `getOrder()`)
  are functionally interchangeable for controlling aggregation order.
- `@Order` can *also* go on individual **`@Bean` factory methods** — needed
  specifically when the **same bean class** is produced by two different
  `@Bean` methods (class-level `@Order` can't disambiguate two definitions of
  one class; the method-level annotation can).
- **Gotcha**: `@Order` on the `@Configuration` class itself only affects the
  relative startup-evaluation order of configuration classes against each
  other. It does **not** propagate to, or affect the ordering of, the beans
  that class's `@Bean` methods produce.


## Main04 — Ambiguity Resolution

- Three ways to resolve ambiguity for a **single-valued** injection point, in
  rough order of directness: `@Primary` → `@Qualifier` (declared on *both*
  the bean and the injection point) → implicit **by-name fallback**
  (field/parameter name matches a candidate bean's name).
- Ambiguity resolution is a **single-value-only** concept. A collection-typed
  injection point of the same ambiguous type simply receives **all**
  matching beans — `@Primary` and a bare `@Qualifier` don't filter a
  collection at all.
- **But** `@Qualifier` *can* be deliberately used on a collection-typed
  injection point as a **filter**: if several beans share the same
  `@Qualifier` value and the injection point carries that same value, only
  that matching subset gets aggregated in (confirmed against docs: "all
  matching beans, according to the declared qualifiers, are injected as a
  collection").
- XML `<qualifier value="x"/>` nested inside a `<bean>` tag does **nothing on
  its own** — it only has effect once a Java-side `@Qualifier("x")` exists on
  the (single-valued) injection point that consumes it. (Same finding as the
  XML-config phase of this study, now completed end-to-end with a real
  injection.)


## Main05 — "required" Semantics, "Optional\<T\>", "@Nullable"

- Default `@Autowired` is `required=true`. For **array/collection/map**
  injection points specifically, `required=true` means "at least one
  matching element is expected" — an *empty* aggregation still throws
  `UnsatisfiedDependencyException` at context refresh; it does **not**
  silently inject an empty collection.
- `required=false` behavior differs by injection style:
  - **Method** (setter or otherwise): the method is simply never called if
    the dependency is unavailable.
  - **Field**: the field is left at whatever value it already had (its
    pre-set "default"), not forcibly nulled.
- `java.util.Optional<T>` is Spring's alternate, type-based way to express
  the same non-required semantics. The official docs only show it on a
  setter parameter, but it was **empirically confirmed here to also work on
  a field and a constructor parameter** — the mechanism isn't location-
  restricted, just under-documented with examples.
- `@Nullable` is matched by Spring purely by **simple annotation name**,
  regardless of package — `org.springframework.lang.Nullable` and
  `org.jspecify.annotations.Nullable` behave identically for `@Autowired`
  purposes. JSpecify's actual value-add is broader static-analysis tooling
  across a whole codebase, not anything DI-specific — not worth an extra JAR
  just for this project.


## Main06 — @Value / @PropertySource / SpEL

- **Part A** confirmed SpEL is a fully standalone library, usable with zero
  Spring container involvement: `ExpressionParser` /
  `SpelExpressionParser` / `Expression` / `EvaluationContext` /
  `StandardEvaluationContext`.
- `Expression` interface: **8** `getValue()` overloads (bare · `+Class` ·
  `+rootObject` · `+rootObject+Class` · `+context` · `+context+Class` ·
  `+context+rootObject` · `+context+rootObject+Class`) and **3**
  `setValue()` overloads (`rootObject+value` · `context+value` ·
  `context+rootObject+value`). Confirmed directly against the current
  `Expression` javadoc — this API has been stable since Spring 3.0.
- **Templating** (`#{...}` mixed with literal text) works **natively**
  inside `@Value` with no extra setup. Standalone use requires explicitly
  passing a `TemplateParserContext` to `parser.parseExpression(...)`.
- All context beans are available as **predefined SpEL variables** by their
  bean name, inside any SpEL expression Spring evaluates — including the
  standard `environment`, `systemProperties`, and `systemEnvironment` beans.
- **Safe navigation (`?.`)** short-circuits to `null` at the *first* null
  link in a chain — whether that's a null property or a null method target —
  confirmed by evaluating the exact same expression before and after a field
  went from `null` to a real instance (`null` → `11`).
- `T()` evaluates to a plain `java.lang.Class` instance (you can call
  `.getSimpleName()` etc. on the result). `java.lang` types need no
  qualification (`T(String)`); every other type must be fully qualified
  (`T(my.package.MyClass)`).


## Main07 — Self-Injection (bonus)

- Self-injection is a **documented, named** Spring feature — `@Autowired`
  explicitly considers a field that refers back to the bean currently being
  injected. Not something hacked together.
- Docs frame it as a **fallback mechanism**, intended as a last resort —
  typically to call another method on the *same* bean **through its AOP
  proxy** (e.g. a `@Transactional`-advised method), which a direct
  `this.method()` call would otherwise silently bypass.
- With no AOP proxying configured in this project, the self-injected
  reference and `this` are literally the **same object** (confirmed via
  identity comparison). A real `@Transactional`/AOP scenario would make them
  *different* (proxy vs. raw instance) — that gap is exactly why the feature
  exists, and it's left for a dedicated future AOP-focused phase.

