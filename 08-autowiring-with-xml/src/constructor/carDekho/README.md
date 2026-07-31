# "constructor" Auto-wiring Mode

- Main class: [constructor.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - ( 1 ) [beans-constructor-basics.xml](./resources/beans-constructor-basics.xml)
    - ( 2 ) [beans-constructor-greedy-selection.xml](./resources/beans-constructor-greedy-selection.xml)
    - ( 3 ) [beans-constructor-partial-match-forces-lesser-ctor.xml](./resources/beans-constructor-partial-match-forces-lesser-ctor.xml)


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



```

