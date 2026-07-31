# "byType" Auto-wiring Mode

- Main class: [byType.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - (  1 ) [beans-bytype-basics.xml](./resources/beans-bytype-basics.xml)
    - ( 2a ) [beans-bytype-ambiguity-primary.xml](./resources/beans-bytype-ambiguity-primary.xml)
    - ( 2b ) [beans-bytype-ambiguity-exclusion.xml](./resources/beans-bytype-ambiguity-exclusion.xml)
    - (  3 ) [beans-bytype-default-candidates.xml](./resources/beans-bytype-default-candidates.xml)
    - (  4 ) [beans-bytype-ambiguous-fails.xml](./resources/beans-bytype-ambiguous-fails.xml)
    - (  5 ) [beans-bytype-aggregation.xml](./resources/beans-bytype-aggregation.xml)


---

<br>

**XML-based autowiring using `autowire="byType"`.**

## Concepts covered

- Exactly one bean of a required type → wired automatically, no `<property>`
  needed.
- Zero matching beans → property silently left `null`, no exception.
- Multiple matching beans for a single-valued property, no resolution mechanism
  → `NoUniqueBeanDefinitionException` (fatal, fails context refresh).
- Ambiguity resolution via `primary="true"` on one candidate bean — works with
  zero Java annotations.
- Ambiguity resolution via `autowire-candidate="false"` — removes a bean from
  type-based autowiring candidacy entirely (not a tie-break; reduces the
  candidate pool).
- `default-autowire-candidates` on `<beans/>` — same exclusion, applied
  file-wide via bean-name pattern matching, instead of per-bean.
- Explicit `<property>`/`<constructor-arg>` always overrides autowiring, even
  when autowiring would otherwise resolve cleanly (via `primary` or otherwise).
- Simple types (`String`, `BigDecimal`, etc.) and arrays of simple types are
  excluded from the autowiring candidate scan entirely — same rule as `byName`,
  shared underlying code, not mode-specific.
- Arrays and typed collections (`List<T>`, `Set<T>`) — byType aggregates
  **every** eligible bean of the matching type, not just one. A bean already
  used to satisfy a single-valued property elsewhere can also appear in an
  aggregated collection.
- `Map<String, T>` autowiring — populated with all beans of type `T`, keyed by
  bean name; requires the key type to be `String`.
- Zero matching beans for an array/`List`/`Set`/`Map` property → resolves to
  `null`, **not** an empty collection (the classic `autowire="byType"`
  dependency descriptor is never marked "required").
- `<qualifier>`-based ambiguity resolution does **not** work with plain XML
  `autowire="byType"` — it requires a `@Qualifier` annotation on the actual
  injection point (field/setter/constructor param), which is outside the scope
  of pure-XML autowiring. Not demonstrated here for that reason.
- Injection happens exclusively through setter methods (same as `byName`) — no
  field or constructor injection under this mode.


### The "required" flag, explained briefly

Internally, Spring resolves a dependency through a `DependencyDescriptor`
object, which carries a `required` flag. That flag controls what happens when
**zero** matching beans are found:

- **`required = true` →** Spring throws (`NoSuchBeanDefinitionException`). This
  is the default for things like `@Autowired` fields/constructors (unless you
  write 	`@Autowired(required = false)`).
- **`required = false` →** Spring just leaves the property unset and moves on,
  no exception.

The classic XML `autowire="byType"`/`byName` machinery always builds its
internal descriptor with `required = false`, because plain `autowire=`
property-population is inherently *best-effort* — a property with no matching
bean simply stays unset, it doesn't fail the whole bean.

That's *why* **zero candidates** give you `null` rather than an exception.

And for array/`List`/`Set`/`Map` properties specifically, that same
`required = false` setting is also *why* **zero candidates** give you `null`
instead of an empty collection — Spring only bothers constructing an (empty or
populated) collection object once it's committed to satisfying the dependency
at all, and with `required = false` and nothing found, it exits before ever
building one.

<br>

---

## Sample run output

```txt
####### Auto-wiring mode "byType" Demo (XML config) ###########


=== 1) beans-bytype-basics.xml ===
==================================


---- car_basic - single unambiguous candidate per type wired automatically; spareEngines picks up the same 'engine' instance as a 1-element array; color/dealershipPrice/serviceHistory stay null despite matching-typed beans existing ----
  engine          : Standard Engine (bean id = 'myEngine')
  transmission    : Automatic Transmission (bean id = 'transmissionAuto')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'myEngine')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car_simpleTypesExplicit - color/dealershipPrice/serviceHistory set explicitly, the only way to populate a simple type or an array of one ----
  engine          : Standard Engine (bean id = 'myEngine')
  transmission    : Automatic Transmission (bean id = 'transmissionAuto')
  gps             : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]
  color           : Pearl White
  dealershipPrice : 33499.75
  serviceHistory  : [2025-01-20: Pre-delivery inspection]
  spareEngines    : [Standard Engine (bean id = 'myEngine')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 2a) beans-bytype-ambiguity-primary.xml ===
==============================================


---- car_resolvedByPrimary - ambiguous by type (2 Engine beans), resolved via primary="true"; note spareEngines aggregates BOTH Engine beans regardless of which is primary ----
  engine          : Standard Engine (bean id = 'enginePrimaryA')
  transmission    : Automatic Transmission (bean id = 'autoTransmission')
  gps             : GpsSystem [gpsModuleType=BATTERY_POWERED]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'enginePrimaryA'), Turbo Engine (bean id = 'enginePrimaryB')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car_explicitOverridesPrimary - explicit <property ref="enginePrimaryB"/> overrides even primary-based autowiring ----
  engine          : Turbo Engine (bean id = 'enginePrimaryB')
  transmission    : Automatic Transmission (bean id = 'autoTransmission')
  gps             : GpsSystem [gpsModuleType=BATTERY_POWERED]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'enginePrimaryA'), Turbo Engine (bean id = 'enginePrimaryB')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 2b) beans-bytype-ambiguity-exclusion.xml ===
================================================


---- car_resolvedByExclusion - ambiguous by type (2 Engine beans), resolved because autowire-candidate="false" removes engineExcludeA from candidacy entirely ----
  engine          : Turbo Engine (bean id = 'engineExcludeB')
  transmission    : Automatic Transmission (bean id = 'noManualGearShifting')
  gps             : GpsSystem [gpsModuleType=CAN_BUS_TELEMATICS]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Turbo Engine (bean id = 'engineExcludeB')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car_explicitRefToExcludedEngine - explicit ref still resolves the autowire-candidate="false" bean, same as byName ----
  engine          : Standard Engine (bean id = 'engineExcludeA')
  transmission    : Automatic Transmission (bean id = 'noManualGearShifting')
  gps             : GpsSystem [gpsModuleType=CAN_BUS_TELEMATICS]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Turbo Engine (bean id = 'engineExcludeB')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 3) beans-bytype-default-candidates.xml ===
==============================================


---- car_patternResolves - 'engine' resolved because 'engineExcludedByPattern' doesn't match default-autowire-candidates="engine,transmission"; 'gps' stays null because 'gps' doesn't match the pattern either, leaving zero eligible candidates ----
  engine          : Standard Engine (bean id = 'engine')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : null (NOT wired)
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'engine')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)

---- car_explicitRefBypassesPattern - explicit refs still reach both pattern-excluded beans ('engineExcludedByPattern' and 'gps') ----
  engine          : Turbo Engine (bean id = 'engineExcludedByPattern')
  transmission    : Automatic Transmission (bean id = 'transmission')
  gps             : GpsSystem [gpsModuleType=HARDWIRED]
  color           : null (NOT wired)
  dealershipPrice : null (NOT wired)
  serviceHistory  : null (NOT wired)
  spareEngines    : [Standard Engine (bean id = 'engine')]
  accessories     : null (NOT wired)
  vehicleId       : null (NOT wired)



=== 4) beans-bytype-ambiguous-fails.xml (expected to fail) ===
==============================================================


Jul 31, 2026 12:30:05 AM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'car_ambiguousEngineFails' defined in class path resource [byType/carDekho/resources/beans-bytype-ambiguous-fails.xml]: Unsatisfied dependency expressed through bean property 'engine'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'byType.carDekho.parts.Engine' available: expected single matching bean but found 2: ambiguousEngineA,ambiguousEngineB
Got the EXPECTED exception while creating 'car_ambiguousEngineFails':
  UnsatisfiedDependencyException: Error creating bean with name 'car_ambiguousEngineFails' defined in class path resource [byType/carDekho/resources/beans-bytype-ambiguous-fails.xml]: Unsatisfied dependency expressed through bean property 'engine'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'byType.carDekho.parts.Engine' available: expected single matching bean but found 2: ambiguousEngineA,ambiguousEngineB
This is exactly the documented behavior: "If no unique bean definition is available, an exception is thrown."



=== 5) beans-bytype-aggregation.xml ===
=======================================


---- engineFleet - engineList/engineSet/engineMap all auto-aggregate every Engine bean in the file; accessoryFleet stays null (not an empty Set) since zero Accessory beans exist anywhere here ----
  engineList     : [Standard Engine (bean id = 'fleetEngineAlpha'), Turbo Engine (bean id = 'fleetEngineBravo'), Standard Engine (bean id = 'fleetEngineCharlie')]
  engineSet      : [Standard Engine (bean id = 'fleetEngineAlpha'), Turbo Engine (bean id = 'fleetEngineBravo'), Standard Engine (bean id = 'fleetEngineCharlie')]
  engineMap      : {fleetEngineAlpha=Standard Engine (bean id = 'fleetEngineAlpha'), fleetEngineBravo=Turbo Engine (bean id = 'fleetEngineBravo'), fleetEngineCharlie=Standard Engine (bean id = 'fleetEngineCharlie')}
  accessoryFleet : null (NOT wired)

---- engineFleetExplicitOverride - engineList pinned explicitly to just 2 of the 3 engines, while engineSet/engineMap are still auto-wired with all 3 via byType ----
  engineList     : [Standard Engine (bean id = 'fleetEngineAlpha'), Turbo Engine (bean id = 'fleetEngineBravo')]
  engineSet      : [Standard Engine (bean id = 'fleetEngineAlpha'), Turbo Engine (bean id = 'fleetEngineBravo'), Standard Engine (bean id = 'fleetEngineCharlie')]
  engineMap      : {fleetEngineAlpha=Standard Engine (bean id = 'fleetEngineAlpha'), fleetEngineBravo=Turbo Engine (bean id = 'fleetEngineBravo'), fleetEngineCharlie=Standard Engine (bean id = 'fleetEngineCharlie')}
  accessoryFleet : null (NOT wired)



```

