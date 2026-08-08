# Spring DI (Annotations) Demo

A toy Eclipse project (plain Java project, no Maven/Gradle) covering
**dependency injection via annotations** in Spring Framework 5.3.39 on Java 8.
This is the DI-focused follow-up to two earlier phases: XML-based bean
configuration, and an annotations-based toy project that deliberately
excluded `@Autowired`/DI.

---

## 0. Notes

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

No separate CGLIB/ASM jar is needed — Spring repackages both inside `spring-core`.

---

## Package layout

```
di.main                             Main01..Main07 driver classes
di.beans.constructors                Main01 supporting beans
di.beans.simplecomplex               Main02 supporting beans (simple/complex/array)
di.beans.beanwiring                  Main02 supporting beans (@Bean-to-@Bean wiring)
di.beans.collections                 Main03 supporting beans
di.beans.ambiguity                   Main04 supporting beans
di.beans.ambiguityxml                Main04 Section 3b (XML <qualifier> + Java @Qualifier)
di.beans.optional                    Main05 supporting beans
di.beans.valuespel                   Main06 supporting beans
di.beans.selfinjection               Main07 supporting bean
```

## Running

Each `MainXX_*` class in `di.main` has a `main()` method — run each one
individually as a Java Application in Eclipse, in numeric order. Console
output is grouped into clearly labeled sections describing what each block
demonstrates and what the expected result is.

## Scope notes

- **Main07 (self-injection)** is intentionally shallow: it demonstrates
  Spring's documented self-injection *mechanism* only (a bean holding a
  field that refers back to itself). It does **not** configure real AOP /
  `@EnableAspectJAutoProxy` / `@Transactional` proxying — that's flagged as
  out of scope for this project and left for a dedicated future phase.
- **`@Nullable`** in `di.beans.optional.NullableDemoBean` uses
  `org.springframework.lang.Nullable` (already on the classpath via
  `spring-core`, zero extra JARs) rather than `org.jspecify.annotations.
  Nullable`. Spring's `@Autowired` machinery matches `@Nullable` by simple
  annotation name regardless of package, so behaviorally the two are
  equivalent for DI purposes — JSpecify's value-add is broader static-
  analysis tooling, not anything DI-specific.

