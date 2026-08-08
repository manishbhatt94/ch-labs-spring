# Outputs for Sample Code Runs


## 1. Main01_AutowiredInjectionStyles.java

- Main class: [di.main.Main01_AutowiredInjectionStyles](./src/di/main/Main01_AutowiredInjectionStyles.java)
- Components package:
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


