# SpringAnnotationConfigDemo

A toy, single-package-tree **plain Eclipse Java project** (no Maven/Gradle — JARs are
manually added to the build path) that demonstrates the XML → annotation shift for
Spring Framework **5.3.39** on **Java 8**.

**Dependency injection (`@Autowired`) is intentionally excluded** — that's covered in a
separate demo project. Where a concept would normally *require* an injection point to
demonstrate (e.g. `@Primary`, `@Order`, collection aggregation), this project uses
`ApplicationContext.getBean(...)` / `getBeansOfType(...)` / `getBeanProvider(...)`
instead — these use the exact same by-type/primary/ordering resolution logic internally
as `@Autowired` does, without needing an `@Autowired` field or constructor anywhere.

---

## 0. Notes

- Spring XML Syntax - Annotations Sytax -- Concepts Map\
  [spring-xml-to-annotations.md](./spring-xml-to-annotations.md)
- Spring Docs - Annotations related points - Explanations\
  [annotation-config-notes-explained.md](./annotation-config-notes-explained.md)
- @Configuration attribute "proxyBeanMethods"\
  Proxying of @Configuration class, @Bean Lite Mode\
  [study-notes-configuration-proxying-and-lite-mode.md](./study-notes-configuration-proxying-and-lite-mode.md)
- Example project code - sample runs - output\
  [sample-runs-outputs.md](./sample-runs-outputs.md)


---


## 1. JARs Required

Listed as GAV (GroupId, ArtifactId, Version) coordinates `Group:Artifact:Version`:

- **For Spring Core (5 JARs):** \
  (Placed under custom User Library titled: "spring-basics-libs-5.3.39")
  1. `org.springframework:spring-beans:5.3.39`
  1. `org.springframework:spring-context:5.3.39`
  1. `org.springframework:spring-core:5.3.39`
  1. `org.springframework:spring-expression:5.3.39`
  1. `org.springframework:spring-jcl:5.3.39`
- **For JSR-250 Annotations @PostConstruct, @PreDestroy:** \
  (Placed under custom User Library titled: "spring-jsr250-annotations-5.3.39")
  1. `javax.annotation:javax.annotation-api:1.3.2`
  1. `org.springframework:spring-aop:5.3.39`


---

## 2. What each Main class demonstrates

| Class | XML concept being mirrored | Annotation(s) shown |
|---|---|---|
| `Main01_Stereotypes_ComponentScan` | `<bean class=.../>` for your own classes + `<context:component-scan/>` | `@Component`, `@Service`, `@Repository`, `@Controller`, `@ComponentScan` |
| `Main02_ComponentScanFilters` | `<context:include-filter/>` / `<context:exclude-filter/>` | `@ComponentScan(includeFilters=..., excludeFilters=...)`, `@Filter`, `FilterType.REGEX` / `FilterType.ANNOTATION` |
| `Main03_EagerVsLazy` | `lazy-init="true"`, `<beans default-lazy-init="true">` | `@Lazy`, `@Lazy(false)` override, `@ComponentScan(lazyInit=true)` (real default-lazy-init equivalent for scanned `@Component` beans), `@Lazy` on `@Configuration` class (separate mechanism — only affects `@Bean` methods declared in that class) |
| `Main04_InstantiationMethods` | constructor / `factory-method` / `factory-bean`+`factory-method` | `@Bean`, self-referencing `@Bean` methods, `@Configuration(proxyBeanMethods=false)` |
| `Main05_Scopes` | `scope="singleton"` / `scope="prototype"` | `@Scope` |
| `Main06_LifecycleCallbackOrder` | `init-method`/`destroy-method`, `InitializingBean`/`DisposableBean` | `@PostConstruct`, `@PreDestroy`, `@Bean(initMethod=,destroyMethod=)`, and **the combined precedence order** |
| `Main07_BeanPostProcessors` | `<bean class="...BPP"/>` | `BeanPostProcessor` registered via `@Component` (two of them, to show ordering) |
| `Main08_OrderedBeans` | autowiring into a `<list>`/`<set>` in a specific order | `@Order`, `Ordered`, `ObjectProvider#orderedStream()` |
| `Main09_PrimaryAndQualifierDeclaration` | `primary="true"`, `<qualifier>` | `@Primary`, `@Qualifier` (declared; ambiguity resolution shown via `getBean`) |
| `Main10_MixedXmlAnnotationConfig` | `<context:annotation-config/>` | Same JSR-250 annotations, shown firing (or silently not firing) depending on whether the XML activates annotation processing |

Run them roughly in this order — each one builds on ideas from the previous.

---

## 3. Deliberately excluded from this project

- `@Autowired`, `@Value`, `@Resource`, self-injection, constructor/setter/field
  injection — all dependency-injection topics, reserved for the DI-focused demo project.
- Bean definition inheritance (`parent=` in XML) — genuinely has **no** annotation
  equivalent; annotation config relies on ordinary Java class inheritance instead.
- `@Fallback` — Spring 6.2+ only, not available in Spring Framework 5.3.39.

---

## 4. Erratum (found during review)

`Main03`/`DefaultLazyScanConfig` originally assumed `@Lazy` on a `@Configuration` class
also deferred its `@ComponentScan`-discovered `@Component` beans. Per the official
`@Lazy` Javadoc, class-level `@Lazy` only governs `@Bean` **methods** declared in that
same class — it does not cascade into scanned components. The real equivalent of XML's
`<beans default-lazy-init="true">` for scanned beans is `@ComponentScan(lazyInit = true)`
(added in Spring 4.1). The code and comments now demonstrate both mechanisms separately.

## 5. A note on how ambiguity resolution is demoed without `@Autowired`

`ApplicationContext.getBean(Class<T>)` performs **the same type-resolution algorithm**
Spring uses for `@Autowired` injection points internally (`DefaultListableBeanFactory`'s
candidate resolution, including `@Primary` tie-breaking). That's why `Main09` can
legitimately show `@Primary` disambiguating multiple candidates, and show a
`NoUniqueBeanDefinitionException` being thrown when no `@Primary` bean exists — none of
that requires an `@Autowired` field to exist anywhere in the codebase.

Similarly, `Main08` uses `ObjectProvider<T>#orderedStream()`, which applies the exact
same `AnnotationAwareOrderComparator` that a `List<T>`/`Set<T>` `@Autowired` injection
point would use to sort by `@Order`/`Ordered`.
