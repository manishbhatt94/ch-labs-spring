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


## 4. Main04_InstantiationMethods.java

- Main class: [com.example.annodemo.mains.Main04_InstantiationMethods](./src/com/example/annodemo/mains/Main04_InstantiationMethods.java)
- Configuration class(es):
    - [com.example.annodemo.config.InstantiationConfig](./src/com/example/annodemo/config/InstantiationConfig.java)
    - [com.example.annodemo.config.InstantiationConfigLiteMode](./src/com/example/annodemo/config/InstantiationConfigLiteMode.java)
- Components package:
  [com.example.annodemo.instantiation](./src/com/example/annodemo/instantiation/)


### 4.1. Output

```txt
=== Main04a: constructor / static-factory / instance-factory (proxyBeanMethods=true, default) ===
############################################################
# PART A - proxyBeanMethods = true (the default, "full" mode)
############################################################

[instantiation] {!= WidgetStaticFactory.createWidget() =!} -- WidgetStaticFactory.createWidget() invoked
[instantiation] {!= Widget#Widget(String orging) =!} -- Widget object created, origin=static-factory-method, identityHash=1161667116
[instantiation] {!= WidgetInstanceFactory#WidgetInstanceFactory() =!} -- WidgetInstanceFactory constructed (identityHash=1898220577)
[instantiation] {!= WidgetInstanceFactory.createWidget() =!} -- WidgetInstanceFactory#createWidget() invoked
[instantiation] {!= Widget#Widget(String orging) =!} -- Widget object created, origin=instance-factory-method, identityHash=1143371233

>> Listing all user-defined beans in the context:
   - instantiationConfig [ Class -> com.example.annodemo.config.InstantiationConfig$$EnhancerBySpringCGLIB$$3526c02d ]
   - widgetFromStaticFactory [ Class -> com.example.annodemo.instantiation.Widget ]
   - widgetInstanceFactory [ Class -> com.example.annodemo.instantiation.WidgetInstanceFactory ]
   - widgetFromInstanceFactory [ Class -> com.example.annodemo.instantiation.Widget ]

>> @Configuration(proxyBeanMethods = true) class InstantiationConfig -> CGLIB proxy class =
   --> com.example.annodemo.config.InstantiationConfig$$EnhancerBySpringCGLIB$$3526c02d

>> Fetching bean 'widgetFromStaticFactory' (built via a plain static factory method)
   -> origin = static-factory-method

>> Fetching bean 'widgetFromInstanceFactory' (built by calling widgetInstanceFactory().createWidget()
   from WITHIN another @Bean method - watch: NO extra 'WidgetInstanceFactory constructed'
   line appears above, because the CGLIB proxy redirected that call to the SAME
   already-registered singleton instead of re-running the method body.)
   -> origin = instance-factory-method

>> Proof the factory itself stayed a true singleton across both direct-lookup
   and in-code self-call:
   identityHash of the registered 'widgetInstanceFactory' bean = 1898220577
   which matches the identityHash printed in the 'WidgetInstanceFactory constructed' line above.



--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--


=== Main04b: proxyBeanMethods=false ("lite mode") - self-call is a PLAIN java call now ===
############################################################
# PART B - proxyBeanMethods = false ("lite" mode)
############################################################

[instantiation] {!= WidgetInstanceFactory#WidgetInstanceFactory() =!} -- WidgetInstanceFactory constructed (identityHash=758013696)
[instantiation] {!= WidgetInstanceFactory#WidgetInstanceFactory() =!} -- WidgetInstanceFactory constructed (identityHash=1279309678)

>> Listing all user-defined beans in the context:
   - instantiationConfigLiteMode [ Class -> com.example.annodemo.config.InstantiationConfigLiteMode ]
   - widgetInstanceFactory [ Class -> com.example.annodemo.instantiation.WidgetInstanceFactory ]
   - anotherBeanThatCallsFactory [ Class -> com.example.annodemo.instantiation.WidgetInstanceFactory ]

>> @Configuration(proxyBeanMethods = false) class InstantiationConfigLiteMode -> NON-CGLIB proxy class =
   --> com.example.annodemo.config.InstantiationConfigLiteMode

>> Notice TWO separate 'WidgetInstanceFactory constructed' lines printed above:
   1st = the container building the registered 'widgetInstanceFactory' bean
   2nd = 'anotherBeanThatCallsFactory' calling widgetInstanceFactory() as a
         PLAIN Java method (no interception in lite mode) -> re-runs the body
         -> builds a brand-new, UNTRACKED WidgetInstanceFactory.

>> registeredBean  identityHash = 758013696
>> viaSelfCall     identityHash = 1279309678
>> same object?    false   <-- singleton guarantee is BROKEN for this call path under lite mode

```

<br>

---


## 5. Main05_Scopes.java

- Main class: [com.example.annodemo.mains.Main05_Scopes](./src/com/example/annodemo/mains/Main05_Scopes.java)
- Configuration class(es):
    - [com.example.annodemo.config.ScopeConfig](./src/com/example/annodemo/config/ScopeConfig.java)
- Components package:
  [com.example.annodemo.scope](./src/com/example/annodemo/scope/)


### 5.1. Output

```txt
=== Main05: singleton vs prototype scope ===
============================================

[scope] {! SingletonScopedBean#SingletonScopedBean() !} -- SingletonScopedBean constructed

-- context refreshed, singleton bean(s) have been constructed --


>> Calling getBean(SingletonScopedBean.class) twice -- Note that constructor WON'T be called at all.

> Singleton: s1==s2 ? true  (expect true).


>> Calling getBean(PrototypeScopedBean.class) twice -- Note that constructor GETS CALLED twice.

[scope] {! PrototypeScopedBean#PrototypeScopedBean() !} -- PrototypeScopedBean constructed
[scope] {! PrototypeScopedBean#PrototypeScopedBean() !} -- PrototypeScopedBean constructed

> Prototype: p1==p2 ? false  (expect false).


```

