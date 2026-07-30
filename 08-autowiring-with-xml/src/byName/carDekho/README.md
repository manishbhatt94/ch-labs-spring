# "byName" Auto-wiring Mode

- Main class: [byName.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - 1) [beans-byname-main.xml](./resources/beans-byname-main.xml)
    - 2) [beans-byname-default-candidates.xml](./resources/beans-byname-default-candidates.xml)
    - 3) [beans-byname-simple-and-collections.xml](./resources/beans-byname-simple-and-collections.xml)
    - 4) [beans-byname-default-autowire.xml](./resources/beans-byname-default-autowire.xml)


---

## Sample run output

```txt
####### Auto-wiring mode "byName" Demo (XML config) ###########


=== 1) beans-byname-main.xml ===
================================


---- car1 - plain byName ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=null]
  color           : Red
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car2 - explicit <property> overrides autowiring ----
  engine          : Turbo Engine (bean id = 'turboEngine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=null]
  color           : Blue
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car3 - autowire-candidate=false excludes gps ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : Green
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car4 - explicit ref bypasses autowire-candidate=false ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=HARDWIRED]
  color           : Black
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 2) beans-byname-default-candidates.xml ===
==============================================


---- car5 - gps excluded via default-autowire-candidates ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Yellow
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car6 - explicit ref still bypasses the pattern exclusion ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : Purple
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 3) beans-byname-simple-and-collections.xml ===
==================================================


color: Silver (Stand-alone Bean 'color' of Type String)
dealershipPrice: 24999.99
serviceHistory: [2023-01-10: Oil change, 2023-06-22: Tire rotation]
spareEngines: [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]

---- car_simple1 - color/dealershipPrice/serviceHistory left null (simple types and arrays-of-simple-types are never autowire candidates); engine/transmission/spareEngines/accessories/vehicleId still wired by name ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 75cadfd8-f4eb-4d2d-9b61-1d2f151d4049

---- car_simple2 - color/dealershipPrice/serviceHistory set explicitly, including a literal nested <array> for the String[] property ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Red
  dealershipPrice : 27999.50
  serviceHistory  : [2019-12-31: In warranty Service #1 - Handle-bars alignment, 2020-02-25: In warranty Service #2 - Throttle cable choking, 2020-05-12: In warranty Service #3 - Engine knocking problem]
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : e24461ae-f1e6-446b-99a8-461db79f123b

---- car_simple3 - spareEngines/accessories/vehicleId auto-wired fields overridden using <property>, and field 'gps' also manually wired ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Air Conditioning], Accessory[Touchscreen Instrument Cluster], Accessory[Infotainment System Dashboard]]
  vehicleId       : dee4c9f5-2350-44a3-9eec-f131bdcf3703

---- car_arrays1 - declared before the 'spareEngines' bean definition; still gets it wired (declaration order doesn't matter for byName) ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 979cccc0-967d-40ad-bb2f-15b79f97d8d1

---- car_arrays2 - declared after the 'spareEngines' bean definition; wired identically to car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : d492e5ad-a842-4de1-8c2d-74b705b130b2

---- car_collections1 - declared before the 'accessories' bean definition; still gets it wired, same reason as car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : b6dce868-0f30-4186-b3ec-7c02791c6d3c

---- car_collections2 - declared after the 'accessories' bean definition; wired identically to car_collections1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 340ce6a4-0560-4844-852e-afc421488168

---- car_uuid1 - vehicleId from the prototype UUID.randomUUID() bean ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : a4d80f48-5e6a-45fb-81d1-47bf59b549a1

---- car_uuid2 - a DIFFERENT UUID: the only property in this file where the two cars genuinely differ, thanks to prototype scope ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : e1cd15af-dd86-4552-a3b7-3506559718e0



=== 4) beans-byname-default-autowire.xml ===
============================================


---- carInheritedDefault - no autowire attribute, inherits <beans default-autowire="byName"> ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Teal
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- carExplicitDefault - autowire="default", same effect as omitting it ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Maroon
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- carOptedOut - autowire="no" overrides the container-wide default ----
  engine          : null (NOT wired)
  transmission    : null (NOT wired)
  gps             : null (NOT wired)
  color           : White
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : null (NOT wired)
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



```

