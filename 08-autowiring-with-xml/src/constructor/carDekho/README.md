# "constructor" Auto-wiring Mode

- Main class: [constructor.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - ( 1  ) [beans-constructor-basics.xml](./resources/beans-constructor-basics.xml)
    - ( 2  ) [beans-constructor-greedy-selection.xml](./resources/beans-constructor-greedy-selection.xml)
    - ( 3  ) [beans-constructor-partial-match-forces-lesser-ctor.xml](./resources/beans-constructor-partial-match-forces-lesser-ctor.xml)
    - ( 4  ) [beans-constructor-zero-match-single-ctor.xml](./resources/beans-constructor-zero-match-single-ctor.xml)
    - ( 5a ) [beans-constructor-ambiguity-primary.xml](./resources/beans-constructor-ambiguity-primary.xml)


---

<br>

## Sample run output

```txt
####### Auto-wiring mode "constructor" Demo (XML config) ###########


=== 1) beans-constructor-basics.xml ===
=======================================


[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

--- Basic Constructor Autowiring Demo ---
Engine: Standard Engine (bean id = 'myEngine')
Transmission: Automatic Transmission (bean id = 'myTransmission')



=== 2) beans-constructor-greedy-selection.xml ===
=================================================


[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

--- Greedy Constructor Selection Demo ---
Engine: Standard Engine (bean id = 'v8Engine')
Transmission: Automatic Transmission (bean id = 'autoTrans')
GPS: GpsSystem [gpsModuleType=BATTERY_POWERED]



=== 3) beans-constructor-partial-match-forces-lesser-ctor.xml ===
=================================================================


[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission). ~~]

--- Partial Match Fallback Demo ---
Engine: Standard Engine (bean id = 'baseEngine')
Transmission: Automatic Transmission (bean id = 'baseTrans')
GPS: null (NOT wired)



=== 4) beans-constructor-zero-match-single-ctor.xml ===
=======================================================


Jul 31, 2026 5:23:35 PM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'myCar' defined in class path resource [constructor/carDekho/resources/beans-constructor-zero-match-single-ctor.xml]: Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'constructor.carDekho.parts.AutomaticTransmission' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}

Context refresh failed as expected -- exception below:
  UnsatisfiedDependencyException: Error creating bean with name 'myCar' defined in class path resource [constructor/carDekho/resources/beans-constructor-zero-match-single-ctor.xml]: Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'constructor.carDekho.parts.AutomaticTransmission' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}


```

