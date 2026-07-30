# "byType" Auto-wiring Mode

- Main class: [byType.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - ( 1) [beans-bytype-basics.xml](./resources/beans-bytype-basics.xml)
    - (2a) [beans-bytype-ambiguity-primary.xml](./resources/beans-bytype-ambiguity-primary.xml)
    - (2b) [beans-bytype-ambiguity-exclusion.xml](./resources/beans-bytype-ambiguity-exclusion.xml)
    - ( 3) [beans-bytype-default-candidates.xml](./resources/beans-bytype-default-candidates.xml)
    - ( 4) [beans-bytype-ambiguous-fails.xml](./resources/beans-bytype-ambiguous-fails.xml)
    - ( 5) [beans-bytype-aggregation.xml](./resources/beans-bytype-aggregation.xml)


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


Jul 30, 2026 5:59:43 PM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'car_ambiguousEngineFails' defined in class path resource [byType/carDekho/resources/beans-bytype-ambiguous-fails.xml]: Unsatisfied dependency expressed through bean property 'engine'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'byType.carDekho.parts.Engine' available: expected single matching bean but found 2: ambiguousEngineA,ambiguousEngineB
Got the EXPECTED exception while creating 'car_ambiguousEngineFails':
  UnsatisfiedDependencyException: Error creating bean with name 'car_ambiguousEngineFails' defined in class path resource [byType/carDekho/resources/beans-bytype-ambiguous-fails.xml]: Unsatisfied dependency expressed through bean property 'engine'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'byType.carDekho.parts.Engine' available: expected single matching bean but found 2: ambiguousEngineA,ambiguousEngineB
This is exactly the documented behavior: "If no unique bean definition is available, an exception is thrown."



```

