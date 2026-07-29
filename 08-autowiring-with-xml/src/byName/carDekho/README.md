# "byName" Auto-wiring Mode

- Main class: [byName.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    1. [beans-byname-main.xml](./resources/beans-byname-main.xml)
    1. [beans-byname-default-candidates.xml](./resources/beans-byname-default-candidates.xml)
    1. [beans-byname-simple-and-collections.xml](./resources/beans-byname-simple-and-collections.xml)
    1. [beans-byname-default-autowire.xml](./resources/beans-byname-default-autowire.xml)


---

## Sample run output

```txt

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
  vehicleId       : a9209d3e-7793-4a3c-b11a-4a7484febb6f

---- car_simple2 - color/dealershipPrice/serviceHistory set explicitly, including a literal nested <array> for the String[] property ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Red
  dealershipPrice : 27999.50
  serviceHistory  : [2019-12-31: In warranty Service #1 - Handle-bars alignment, 2020-02-25: In warranty Service #2 - Throttle cable choking, 2020-05-12: In warranty Service #3 - Engine knocking problem]
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 4dd104e0-da33-449c-a0b1-d89ce34b0e47

---- car_simple3 - spareEngines/accessories/vehicleId auto-wired fields overridden using <property>, and field 'gps' also manually wired ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Air Conditioning], Accessory[Touchscreen Instrument Cluster], Accessory[Infotainment System Dashboard]]
  vehicleId       : d3d4b029-0f90-4b72-b0e8-0bcd7139737f

---- car_arrays1 - declared before the 'spareEngines' bean definition; still gets it wired (declaration order doesn't matter for byName) ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : f6acf221-50f8-4d9c-b1ae-a34cff0ec8ba

---- car_arrays2 - declared after the 'spareEngines' bean definition; wired identically to car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : b1676df9-eacc-4570-8dca-222a8511f2a4

---- car_collections1 - declared before the 'accessories' bean definition; still gets it wired, same reason as car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 639a8b7c-49d3-4637-a7a6-5c03903dbd23

---- car_collections2 - declared after the 'accessories' bean definition; wired identically to car_collections1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 43e9aa18-89a4-41a8-82cb-397ff089fbde

---- car_uuid1 - vehicleId from the prototype UUID.randomUUID() bean ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : ff79ceb4-0ca4-4741-bdc5-71fa40366aa6

---- car_uuid2 - a DIFFERENT UUID: the only property in this file where the two cars genuinely differ, thanks to prototype scope ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 81ceb8c4-5df1-4deb-95ea-1ca8b79f7d32



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

