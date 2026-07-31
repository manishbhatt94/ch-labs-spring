# "byName" Auto-wiring Mode

- Main class: [byName.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - ( 1 ) [beans-byname-main.xml](./resources/beans-byname-main.xml)
    - ( 2 ) [beans-byname-default-candidates.xml](./resources/beans-byname-default-candidates.xml)
    - ( 3 ) [beans-byname-simple-and-collections.xml](./resources/beans-byname-simple-and-collections.xml)
    - ( 4 ) [beans-byname-default-autowire.xml](./resources/beans-byname-default-autowire.xml)


---

**XML-based auto-wiring using `autowire="byName"`.**

## Concepts covered

- Spring matches a property to a bean whose `id`/`name` is an **exact** match
  to the property name — pure name lookup, no type-aware filtering beyond
  excluding simple types.
- No matching bean by name → property silently left `null`, no exception, no
  ambiguity possible (`byName` never faces the "multiple candidates" problem
  `byType` does).
- Explicit `<property>`/`<constructor-arg>` always overrides auto-wiring.
- Simple types (`String`, `BigDecimal`, etc.) and arrays of simple types are
  excluded from the auto-wiring candidate scan entirely, regardless of whether
  a matching-named bean of the right type exists.
- Collections/arrays of a **complex** type are ordinary candidates for
  `byName` — if a bean's id matches the property name, it gets wired like any
  other single object reference, even though its runtime type happens to be a
  `Set`/`List`/array.
- byName never aggregates multiple same-typed beans into a collection
  automatically — that's exclusively a `byType`/`constructor` behavior. A
  collection-typed property only gets wired if a bean *literally named* the
  same as the property exists.
- `autowire-candidate="false"` and `default-autowire-candidates` do **not**
  affect `byName` — both are documented as affecting only *type-based*
  auto-wiring (`byType`/`constructor`). A bean excluded this way still gets
  wired by `byName` if its name matches.
- Bean **declaration order in the XML has no effect** on `byName` matching —
  all `<bean/>` definitions are registered before any singleton is
  instantiated, so a bean declared later in the file can still satisfy a
  property on a bean declared earlier.
- A prototype-scoped bean (e.g. built via a static factory method) wired by
  name gives each consuming bean its own fresh instance, not a shared
  singleton.
- `<beans default-autowire="byName">` sets a container-wide default; a `<bean>`
  with no `autowire` attribute (or `autowire="default"`) inherits it, while an
  explicit per-bean `autowire` attribute still overrides the default mode on
  `<beans />`.
- `<qualifier>` and ambiguity-resolution mechanisms (`primary`, etc.) are not
  applicable to `byName` — it never has ambiguity to resolve in the first
  place.
- Injection happens exclusively through setter methods — no field or
  constructor injection under this mode.


<br>

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
  vehicleId       : ec036573-4536-49f5-b8e4-f83c1f5ca072

---- car_simple2 - color/dealershipPrice/serviceHistory set explicitly, including a literal nested <array> for the String[] property ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : Red
  dealershipPrice : 27999.50
  serviceHistory  : [2019-12-31: In warranty Service #1 - Handle-bars alignment, 2020-02-25: In warranty Service #2 - Throttle cable choking, 2020-05-12: In warranty Service #3 - Engine knocking problem]
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 1d646236-9e8e-4f65-83c3-8fe887530e2b

---- car_simple3 - spareEngines/accessories/vehicleId auto-wired fields overridden using <property>, and field 'gps' also manually wired ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Air Conditioning], Accessory[Touchscreen Instrument Cluster], Accessory[Infotainment System Dashboard]]
  vehicleId       : 4eaa50e9-bb1d-4e6a-9d36-d29e792d3136

---- car_arrays1 - declared before the 'spareEngines' bean definition; still gets it wired (declaration order doesn't matter for byName) ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 1e50fb43-b3f3-49d2-ba47-f94f49d5de27

---- car_arrays2 - declared after the 'spareEngines' bean definition; wired identically to car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : f8a69bc6-b76f-45c0-a547-3bc1be6e3d2f

---- car_collections1 - declared before the 'accessories' bean definition; still gets it wired, same reason as car_arrays1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : f4b7c3d3-dbb7-41ac-9573-9364aa1e0789

---- car_collections2 - declared after the 'accessories' bean definition; wired identically to car_collections1 ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 0c2baf81-4495-4312-909a-85e87e91e5a5

---- car_uuid1 - vehicleId from the prototype UUID.randomUUID() bean ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : 226b3049-069f-48ac-9aa1-fd9adaaca357

---- car_uuid2 - a DIFFERENT UUID: the only property in this file where the two cars genuinely differ, thanks to prototype scope ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'spareStandardEngine'), Turbo Engine (bean id = 'spareTurboEngine')]
  accessories     : [Accessory[Sunroof], Accessory[Leather Seats]]
  vehicleId       : a416ea02-3bba-47e0-8fec-9134767959e1



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



=== 5) beans-byname-name-matched-type-mismatch.xml ===
============================================


Jul 31, 2026 1:53:32 PM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'carDependencyTypeMismatch' defined in class path resource [byName/carDekho/resources/beans-byname-name-matched-type-mismatch.xml]: Initialization of bean failed; nested exception is org.springframework.beans.TypeMismatchException: Failed to convert property value of type 'java.time.LocalDate' to required type 'java.util.UUID' for property 'vehicleId'; nested exception is java.lang.IllegalArgumentException: Cannot convert value of type 'java.time.LocalDate' to required type 'java.util.UUID' for property 'vehicleId': PropertyEditor [org.springframework.beans.propertyeditors.UUIDEditor] returned inappropriate value of type 'java.time.LocalDate'

Got the EXPECTED exception while creating 'carDependencyTypeMismatch':
  BeanCreationException: Error creating bean with name 'carDependencyTypeMismatch' defined in class path resource [byName/carDekho/resources/beans-byname-name-matched-type-mismatch.xml]: Initialization of bean failed; nested exception is org.springframework.beans.TypeMismatchException: Failed to convert property value of type 'java.time.LocalDate' to required type 'java.util.UUID' for property 'vehicleId'; nested exception is java.lang.IllegalArgumentException: Cannot convert value of type 'java.time.LocalDate' to required type 'java.util.UUID' for property 'vehicleId': PropertyEditor [org.springframework.beans.propertyeditors.UUIDEditor] returned inappropriate value of type 'java.time.LocalDate'

Problem with by name matched bean, having type that mismatches with the type of the field.



```

