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


## 6. Main06_ValuePropertySourceSpEL.java

- Main class:\
  [di.main.Main06_ValuePropertySourceSpEL](./src/di/main/Main06_ValuePropertySourceSpEL.java)
- Components package(s):
    - [di.beans.valuespel](./src/di/beans/valuespel/)


### 6.1 Output

```txt
=========================================================
 MAIN06: @Value, @PropertySource, and SpEL
=========================================================



--- Part A, Section 1: ExpressionParser/Expression/EvaluationContext -- all getValue/setValue overloads ---

    getValue()                                  -> Hello World
    getValue(Class)                             -> Hello World
    getValue(rootObject)                        -> Nikola Tesla
    getValue(rootObject, Class)                 -> Nikola Tesla
    getValue(context)  [root preset on context] -> Nikola Tesla
    getValue(context, Class)                    -> Nikola Tesla
    getValue(context, rootObject)  [override]   -> Nikola Tesla
    getValue(context, rootObject, Class)        -> Nikola Tesla
    setValue(rootObject, value)          -> tesla.name is now: Nikola Tesla #1
    setValue(context, value)             -> tesla.name is now: Nikola Tesla #2
    setValue(context, rootObject, value) -> tesla.name is now: Nikola Tesla #3


--- Part A, Section 2: property navigation and indexing (arrays/strings/maps) ---

    placeOfBirth.name                             -> Smiljan
    inventions[0]  (array indexing)               -> Tesla Coil
    'Hello World'[0]  (string indexing)           -> H
    {'India':'Delhi','France':'Paris'}['France']  -> Paris


--- Part A, Section 3: inline lists, inline maps, array construction ---

    {1,2,3,4}  (inline list)               -> [1, 2, 3, 4]
    {'India':'Delhi','France':'Paris'}     -> {India=Delhi, France=Paris}
    new int[]{1,2,3}  (array construction) -> [1, 2, 3]
    (note: SpEL cannot take an initializer for a MULTI-dimensional array construction)


--- Part A, Section 4: invoking methods (on a literal, and on a root object) ---

    'Hello World'.concat('!')  (on a literal)   -> Hello World!
    describe()  (instance method on rootObject) -> Nikola Tesla #3 (Serbian)


--- Part A, Section 5: the T() operator (types + static members) ---

    T(java.lang.Math).PI                       -> 3.141592653589793
    T(java.lang.Math).random()                 -> 0.9062204289065221
    T(String) == T(java.lang.String)?  (java.lang needs no qualification) -> true
    T(Math).toDegrees(3.141592653589793)       -> 180.0
    T(di.beans.valuespel.GreetingHelper).randomGreeting()   (non-java.lang MUST be fully-qualified) -> Salaam
    T(di.beans.valuespel.City).getSimpleName()   (calling Class<City>#getSimpleName() method) -> City
    T(di.beans.valuespel.Inventor)             -> class di.beans.valuespel.Inventor


--- Part A, Section 6: the Elvis operator (?:) ---

    nickname ?: 'Unknown'  (nickname was never set)    -> Unknown


--- Part A, Section 7: the safe navigation operator (?.) ---

    placeOfBirth?.name  (placeOfBirth is null, no NPE) -> null
    #calculator?.max(4, 2)  (calculator variable is null, no NPE) -> null
    calc?.max(8, 11)  (`calc` field is null on rootObject `tesla`, STILL no NPE) -> null
    calc?.max(8, 11)  (`calc` field is now set on rootObject `tesla`) -> 11


--- Part A, Section 8: collection selection (.?[], .^[], .$[]) ---

    #numbers.?[#this % 2 == 0]  (all evens)  -> [2, 4, 6, 8, 10]
    #numbers.^[#this % 2 == 0]  (first even) -> 2
    #numbers.$[#this % 2 == 0]  (last even)  -> 10
    {'India':'Delhi','France':'Paris','US':'DC'}.?[key.length() <= 2]  (map selection, by key) -> {US=DC}


--- Part A, Section 9: collection projection (.![]) ---

    #inventors.![placeOfBirth.name]  (list of birth cities)      -> [Smiljan, Idvor]
    {'a':1,'b':2}.![value]  (map projection -> a List, per docs) -> [1, 2]


--- Part A, Section 10: expression templating (standalone -- needs TemplateParserContext) ---

    random number is 0.9885346819720419


--- Part B: @Value, @PropertySource, and SpEL inside a Spring-managed bean ---

    [AppInfo] appName=Spring DI Demo
    [AppInfo] version=1.0
    [AppInfo] maxUsers=100
    [AppInfo] withDefault=DefaultValue  (expected: DefaultValue, key absent from app.properties)
    [AppInfo] spelArithmetic=42  (expected: 42)
    [AppInfo] spelTernary=HighCapacity  (expected: HighCapacity, since maxUsers=100 > 50)
    [AppInfo] greeting=Welcome to Spring DI Demo!
    [AppInfo] randomGreeting=Ciao
    [AppInfo] osName (predefined 'systemProperties' bean)          =Windows 11
    [AppInfo] appNameViaEnvironment (predefined 'environment' bean) =Spring DI Demo
    [AppInfo] randomIdMessage (templating works natively in @Value) =Your random ID is 0.7756513966722343


```

<br>

---

