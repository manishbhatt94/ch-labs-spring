# Outputs for Sample Code Runs

## 1. Main01_Stereotypes_ComponentScan.java

- Main class: [com.example.annodemo.mains.Main01_Stereotypes_ComponentScan](./src/com/example/annodemo/mains/Main01_Stereotypes_ComponentScan.java)
- Configuration class(es):
    - [com.example.annodemo.config.StereotypeConfig](./src/com/example/annodemo/config/StereotypeConfig.java)
- Components package:
  [com.example.annodemo.stereotypes](./src/com/example/annodemo/stereotypes/)


### 1.1. Output

```txt
=== Main01: Stereotypes + @ComponentScan ===
============================================

[stereotypes] MyController constructed (@Controller)
[stereotypes] MyRepository constructed (@Repository)
[stereotypes] MyService constructed (@Service)
[stereotypes] PlainComponent constructed (@Component)

Registered bean names found by scanning:
  -> org.springframework.context.annotation.internalConfigurationAnnotationProcessor
  -> org.springframework.context.annotation.internalAutowiredAnnotationProcessor
  -> org.springframework.context.annotation.internalCommonAnnotationProcessor
  -> org.springframework.context.event.internalEventListenerProcessor
  -> org.springframework.context.event.internalEventListenerFactory
  -> stereotypeConfig
  -> myController
  -> myRepository
  -> myService
  -> plainComponent

```

<br>

---

## 2. Main02_ComponentScanFilters.java

- Main class: [com.example.annodemo.mains.Main02_ComponentScanFilters](./src/com/example/annodemo/mains/Main02_ComponentScanFilters.java)
- Configuration class(es):
    - [com.example.annodemo.config.FilterConfig](./src/com/example/annodemo/config/FilterConfig.java)
- Components package:
  [com.example.annodemo.filters](./src/com/example/annodemo/filters/)


### 2.1. Output

```txt
=== Main02: @ComponentScan include/exclude filters ===
======================================================

[filters] StubRepositoryLike constructed (included via REGEX includeFilter)

Registered bean names:
  -> filterConfig
  -> stubRepositoryLike

```

<br>

---

## 3. Main03_EagerVsLazy.java

- Main class: [com.example.annodemo.mains.Main03_EagerVsLazy](./src/com/example/annodemo/mains/Main03_EagerVsLazy.java)
- Configuration class(es):
    - [com.example.annodemo.config.PlainScanConfig](./src/com/example/annodemo/config/PlainScanConfig.java)
    - [com.example.annodemo.config.DefaultLazyScanConfig](./src/com/example/annodemo/config/DefaultLazyScanConfig.java)
- Components package:
  [com.example.annodemo.lazyinit](./src/com/example/annodemo/lazyinit/)


### 3.1. Output

```txt
=== Main03a: PlainScanConfig (only per-bean @Lazy applies) ===
==============================================================

[lazy] EagerBean constructed
[lazy] ForcedEagerBean constructed (@Lazy(false) override)

-- context refreshed, beans above already constructed except LazyBean --

Now calling getBean(LazyBean.class) ...
[lazy] LazyBean constructed (only on first use!)


--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--

=== Main03b: DefaultLazyScanConfig ===
======================================

(@ComponentScan(lazyInit=true) = the real default-lazy-init equivalent for
 SCANNED @Component beans; class-level @Lazy is a SEPARATE mechanism that
 only defers @Bean METHODS declared in this same class - see demoWidgetBean)

[lazy] ForcedEagerBean constructed (@Lazy(false) override)

-- context refreshed; only ForcedEagerBean should have printed above --

Now calling getBean(EagerBean.class) ...
[lazy] EagerBean constructed
Now calling getBean(LazyBean.class) ...
[lazy] LazyBean constructed (only on first use!)
Now calling getBean("demoWidgetBean") ...
[lazy] demoWidgetBean() @Bean method invoked (deferred solely because of THIS class's own @Lazy annotation)
[instantiation] Widget object created, origin=lazy-configuration-class-bean-method, identityHash=1627857534

```

<br>

---


