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
