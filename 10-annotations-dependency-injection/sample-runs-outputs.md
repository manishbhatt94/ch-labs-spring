# Outputs for Sample Code Runs


## 1. Main01_AutowiredInjectionStyles.java

- Main class:\
  [di.main.Main01_AutowiredInjectionStyles](./src/di/main/Main01_AutowiredInjectionStyles.java)
- Components package:\
  [di.beans.constructors](./src/di/beans/constructors/)


### 1.1. Output

```txt
=========================================================
 MAIN01: @Autowired injection styles + constructor rules
=========================================================

⁜        (Battery instance created)
⁜        (Engine instance created)
⁜    [CarFlexibleConstructor] built via (Engine, Battery) constructor -- expected winner: satisfies MORE dependencies
⁜    [CarMultiConstructor] built via @Autowired(Engine) constructor -- this is the one Spring must choose
⁜    [CarSingleConstructor] built via its ONLY constructor (Engine) -- no @Autowired needed
⁜        (FuelTankGauge instance created)
⁜    [Dashboard] setBattery(Battery) called -- setter injection
⁜        (GPS instance created)
⁜    [Dashboard] wireUpEverything(Engine, GPS) called -- arbitrary-named multi-arg method injection
⁜        (Speedometer instance created)


--- Section 1: single-constructor class (implicit autowiring) ---

CarSingleConstructor [engine=di.beans.constructors.Engine@5427c60c]


--- Section 2: multi-constructor class, one @Autowired ---

CarMultiConstructor [engine=di.beans.constructors.Engine@5427c60c]


--- Section 3: multi-constructor class, both required=false ---

CarFlexibleConstructor [engine=di.beans.constructors.Engine@5427c60c, battery=di.beans.constructors.Battery@15bfd87]


--- Section 4: field / setter / arbitrary-method injection ---

    [Dashboard] {
        gps=OK,
        battery=OK,
        engine=OK,
        fuelTankGauge=OK,
        speedometer=NULL
    }

Dashboard [gps=di.beans.constructors.GPS@44f75083, battery=di.beans.constructors.Battery@15bfd87, engine=di.beans.constructors.Engine@5427c60c, fuelTankGauge=di.beans.constructors.FuelTankGauge@2698dc7, speedometer=null]


```

<br>

---


## 2. Main02_SimpleComplexArraysAndBeanWiring.java

- Main class:\
  [di.main.Main02_SimpleComplexArraysAndBeanWiring](./src/di/main/Main02_SimpleComplexArraysAndBeanWiring.java)
- Components package(s):\
    - [di.beans.simplecomplex](./src/di/beans/simplecomplex/)
    - [di.beans.beanwiring](./src/di/beans/beanwiring/)


### 2.1 Output

```txt
================================================================
 MAIN02: simple vs complex types, arrays, @Bean-to-@Bean wiring
================================================================

        (Amplifier instance created)


--- Section 1: @Value simple type vs @Autowired complex type ---

    [ThemeSettings] themeName=Dark, volumeLevel=42
    [SoundSystem] amplifier=OK


--- Section 2: array of a complex type (aggregated from 3 beans) ---

    [AudioDeviceRouter] devices=[Headphones, Speaker, Subwoofer] (count=3)


--- Section 3: array of a simple type (single @Bean, NOT aggregated) ---

    [LocalizationInfo] supportedLanguages=[en, fr, de]



--- Section 4a: @Bean-to-@Bean wiring under FULL mode (both styles safe) ---

        (a new Engine2 object was just constructed)

    containerEngine == directCall.getEngine()     ? true
    containerEngine == paramInjection.getEngine() ? true

    → (expected: both true -- full mode keeps everything singleton-consistent)


--- Section 4b: @Bean-to-@Bean wiring under LITE mode (direct call breaks) ---

        (a new Engine2 object was just constructed)
        (a new Engine2 object was just constructed)

    liteContainerEngine == directCallLite.getEngine()     ? false
    → (expected: FALSE -- direct call bypassed the container)

    liteContainerEngine == paramInjectionLite.getEngine() ? true
    → (expected: TRUE -- parameter injection unaffected by lite mode)


```

<br>

---


## 3. Main03_CollectionInjectionAndOrdering.java

- Main class:\
  [di.main.Main03_CollectionInjectionAndOrdering](./src/di/main/Main03_CollectionInjectionAndOrdering.java)
- Components package(s):\
    - [di.beans.collections](./src/di/beans/collections/)


### 3.1 Output

```txt
=========================================================
 MAIN03: collection injection, aggregation, and ordering
=========================================================


--- Section 1: aggregation with NATURAL (unordered) registration order ---

    [NewsAggregatorUnordered] order=AlJazeera BBC CNN      (natural registration order -- not a guaranteed contract)


--- Section 2a: aggregation ordered via @Order ---

    [WeatherAggregatorOrdered] order=OpenWeather(@Order=1) | AccuWeather(@Order=2) | NOAA(@Order=3) | 


--- Section 2b: aggregation ordered via the Ordered interface ---

    [StockAggregatorOrderedInterface] order=NYSE(Ordered=1) | NASDAQ(Ordered=2) | LSE(Ordered=3) | 


--- Section 3: a @Bean method returning List<String> directly (not aggregated) ---

    [ExchangePreferences] preferredExchanges=[NYSE, NASDAQ]      (this is the ONE @Bean-returned List, not an aggregation)


--- Section 4: @Order on individual @Bean methods (same bean class, two defs) ---

    [AlertDispatcher] order=Email SMS      (expected: Email SMS -- per-@Bean-method @Order)


--- Section 5: aggregated beans in Map, and Set ---

    [AggregateWeatherStockMapSet] weatherSources (java.util.LinkedHashMap)
    [AggregateWeatherStockMapSet] weatherSources={ accuWeatherSource: AccuWeather(@Order=2), noaaWeatherSource: NOAA(@Order=3), openWeatherSource: OpenWeather(@Order=1) }

    [AggregateWeatherStockMapSet] exchanges (java.util.LinkedHashSet)
    [AggregateWeatherStockMapSet] exchanges=[LSE(Ordered=3), NASDAQ(Ordered=2), NYSE(Ordered=1)]


```

<br>

---


## 4. Main04_AmbiguityResolution.java

- Main class:\
  [di.main.Main04_AmbiguityResolution](./src/di/main/Main04_AmbiguityResolution.java)
- Components package(s):
    - [di.beans.ambiguity](./src/di/beans/ambiguity/)
    - [di.beans.ambiguityxml](./src/di/beans/ambiguityxml/)


### 4.1 Output

```txt
==================================================================
 MAIN04: ambiguity resolution (single-value AND collection cases)
==================================================================



--- Section 1: single-value disambiguation via @Primary ---

    [CheckoutServicePrimary] gateway=CreditCard(@Primary)  (expected: CreditCard, via @Primary)


--- Section 2: single-value disambiguation via @Qualifier ---

    [CheckoutServiceQualifier] notifier=Email  (expected: Email, via matching @Qualifier values)


--- Section 3: single-value disambiguation via implicit by-name fallback ---

    [CheckoutServiceByName] expressShipping=Express  (expected: Express, via implicit by-name match, no @Primary/@Qualifier used)


--- Section 3a: collection injection ignores @Primary/@Qualifier entirely ---

    [CheckoutServiceAllGateways] allGateways=CreditCard(@Primary) | DebitCard | NetBanking |  (expected: all 3, @Primary is irrelevant here)


--- Section 3c: @Qualifier as a COLLECTION FILTER (3 of 5 beans match) ---

    [DiscountAggregator] seasonalDiscounts=BlackFriday | NewYear | SummerSale |  (expected: SummerSale, BlackFriday, NewYear only -- 2 of 5 filtered OUT)



--- Section 3b: XML <qualifier> + Java-side @Qualifier, single-valued ---

    [FraudCheckServiceUser] checker=FraudCheckServiceImpl[Thorough]  (expected: FraudCheckServiceImpl[Thorough], via XML <qualifier> + Java @Qualifier)


```

<br>

---


## 5. Main05_OptionalDependencies.java

- Main class:\
  [di.main.Main05_OptionalDependencies](./src/di/main/Main05_OptionalDependencies.java)
- Components package(s):
    - [di.beans.optional](./src/di/beans/optional/)


### 5.1 Output

```txt
=========================================================
 MAIN05: required semantics, Optional<T>, and @Nullable
=========================================================



--- Section 1: required=false on a setter (dependency absent) ---

    [NotifierClient] smsGateway=NULL (sentinel default kept)  (expected: NULL -- setter should never have been invoked)


--- Section 2: default required=true on an EMPTY collection (expected FAILURE) ---

Aug 09, 2026 5:39:16 PM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'requiredCollectionConsumer': Unsatisfied dependency expressed through field 'mustHaveAtLeastOne'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'java.util.List<di.beans.optional.UnregisteredType>' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    Got expected UnsatisfiedDependencyException.
    Message: Error creating bean with name 'requiredCollectionConsumer': Unsatisfied dependency expressed through field 'mustHaveAtLeastOne'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'java.util.List<di.beans.optional.UnregisteredType>' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}


--- Section 3: Optional<T> on FIELD, SETTER, and CONSTRUCTOR PARAMETER ---

    [OptionalDemoBean] premiumSupport   (FIELD)      isPresent=false  (expected: false, no bean registered)
    [OptionalDemoBean] loyaltyProgram   (SETTER)     isPresent=true   (expected: true -> Gold)
    [OptionalDemoBean] conciergeService (CTOR arg)   isPresent=false  (expected: false, no bean registered)


--- Section 4: @Nullable on a method parameter ---

    [NullableDemoBean] backupContact=null  (expected: null -- no bean registered, but no exception either)


```

<br>

---

