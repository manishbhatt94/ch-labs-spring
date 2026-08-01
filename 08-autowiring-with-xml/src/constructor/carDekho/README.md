# "constructor" Auto-wiring Mode

- Main class: [constructor.carDekho.MainDemo](./MainDemo.java)
- Bean definition XML files:
    - ( 1 &nbsp;) [beans-constructor-basics.xml](./resources/beans-constructor-basics.xml)
    - ( 2 &nbsp;) [beans-constructor-greedy-selection.xml](./resources/beans-constructor-greedy-selection.xml)
    - ( 3 &nbsp;) [beans-constructor-partial-match-forces-lesser-ctor.xml](./resources/beans-constructor-partial-match-forces-lesser-ctor.xml)
    - ( 4 &nbsp;) [beans-constructor-zero-match-single-ctor.xml](./resources/beans-constructor-zero-match-single-ctor.xml)
    - ( 5a ) [beans-constructor-ambiguity-primary.xml](./resources/beans-constructor-ambiguity-primary.xml)
    - ( 5b ) [beans-constructor-ambiguity-exclusion.xml](./resources/beans-constructor-ambiguity-exclusion.xml)
    - ( 6 &nbsp;) [beans-constructor-explicit-arg-overrides.xml](./resources/beans-constructor-explicit-arg-overrides.xml)
    - ( 7 &nbsp;) [beans-constructor-simple-type-excluded.xml](./resources/beans-constructor-simple-type-excluded.xml)
    - ( 8 &nbsp;) [beans-constructor-aggregation.xml](./resources/beans-constructor-aggregation.xml)

<br>

---

<br>


# Autowiring Mode: "constructor"

XML-based `autowire="constructor"` autowiring, demonstrated across 8 isolated
bean files. Spring 5.3.39, Java 8, plain XML — no Java annotations anywhere
in this chapter.

## Core mechanism

`constructor` mode is the constructor-injection counterpart to `byType`:
Spring inspects each bean's constructor(s), and for every parameter, scans
the container for a bean whose type matches. There is no property-name
matching involved at any point — matching is purely by declared parameter
type, same principle as `byType`.

## Constructor selection with multiple constructors

When a class declares more than one constructor, Spring does not simply use
the first one found or the no-arg one. It selects the **greediest
constructor whose parameters can all be fully satisfied** by beans in the
container — i.e. among all constructors where every parameter has exactly
one matching candidate bean, the one with the most parameters wins.

This "greediest satisfiable constructor" rule is documented for
`@Autowired`-driven resolution, but this chapter confirms it also governs
plain XML `autowire="constructor"` with zero annotations present — both
paths share the same underlying constructor-resolution machinery.

Confirmed behaviorally (not just by final result, but by instrumenting the
constructors themselves): only **one** constructor is ever actually invoked.
Spring evaluates satisfiability of each candidate constructor against the
container's beans up front, then commits to and invokes the winner — it
does not attempt the greediest constructor, catch a failure, and retry a
lesser one at the instantiation level.

Consequently, when the greediest constructor has at least one parameter
with **no** matching bean, Spring is able to cleanly fall back to a lesser
constructor that *is* fully satisfiable, without any error — again, invoking
only that one constructor, not attempting the greedier one first.

## Zero matching beans is fatal (a real divergence from "byType")

For setter-based `byType` autowiring, a property with zero matching
candidate beans is simply left `null` — no error. Constructor mode behaves
differently: if **any** constructor parameter across *every* candidate
constructor ends up with zero matching beans, context refresh fails with an
`UnsatisfiedDependencyException` wrapping a `NoSuchBeanDefinitionException`,
naming the exact unsatisfied parameter type. This is a fatal, whole-context
failure — not a per-property silent skip. This makes intuitive sense given
constructor injection's nature: an object cannot be partially constructed
the way it can be partially populated via setters.

## Ambiguity resolution

When 2+ beans of the same type are candidates for a single constructor
parameter, the same disambiguation mechanisms confirmed for `byType` also
work for `constructor` mode, with no XML differences:

- `primary="true"` on one candidate bean resolves the ambiguity in its
  favor.
- `autowire-candidate="false"` on all-but-one candidate removes it from the
  pool entirely, also resolving to a single remaining candidate.
- Without either mechanism, ambiguity is fatal:
  `NoUniqueBeanDefinitionException` wrapped in
  `UnsatisfiedDependencyException` — same exception shape as `byType`'s
  ambiguity failure.

As with `byType`, ambiguity as a concept only applies to single-valued
constructor parameters. It does not apply to array/`List`/`Set`/`Map`
constructor parameters at all — see the aggregation section below.

## Explicit "\<constructor-arg\>" always overrides autowiring

Explicit `<constructor-arg>` — whether targeted by `index`, `type`, or
`name` — always takes precedence over autowired resolution for that
specific parameter, even when autowiring would otherwise resolve
unambiguously via `primary`, and even when the bean referenced is itself
marked `autowire-candidate="false"` elsewhere. Only the explicitly-targeted
parameter is affected; every other parameter on the same bean definition
can still be left to `autowire="constructor"` and resolve normally,
autowired and explicit parameters coexist cleanly on one bean definition.
This holds for scalar parameters (`ref`/`value`) as well as
collection-shaped parameters (a nested `<array>`/`<list>`/`<map>` for a
collection-typed constructor argument).

By-name targeting (`<constructor-arg name="...">`) relies on parameter name
information being present in the compiled class (debug symbols) — this
worked as expected under Eclipse's default compiler settings in this
project, but is worth keeping in mind as an environment-dependent detail
if constructor-arg-by-name ever silently fails to bind on a different build
setup.

## Simple types are NOT excluded from constructor autowiring

This is the most significant behavioral divergence uncovered in this
chapter. For `byName`/`byType` setter injection, simple-typed properties
(`String`, `Number` subtypes, `Date`/`Temporal` types, enums, `URI`, `URL`,
`Locale`, `Class`, and arrays of these — per
`BeanUtils.isSimpleValueType`) are unconditionally excluded from the
autowiring candidate scan, regardless of whether a matching bean exists.

Constructor mode has **no equivalent exclusion**. A simple-typed
constructor parameter (e.g. `String`, `BigDecimal`) is treated as an
ordinary type-matched dependency:

- If exactly one bean of that simple type exists in the container, it gets
  autowired in normally — a `String` bean's value can genuinely flow into a
  `String` constructor parameter with zero explicit wiring.
- If zero beans of that simple type exist, it's fatal, exactly like the
  zero-match behavior for any other constructor parameter (see above) — not
  a silent `null`.

**Note on `UUID` specifically**: some newer Spring versions add `UUID` to
the simple-type list, but the 5.3.x `BeanUtils.isSimpleValueType` javadoc
used in this project does **not** include `UUID`. On 5.3.39, a `UUID`
constructor parameter is treated as an ordinary (non-simple) type for
autowiring purposes.

## Arrays / collections of simple types

Array-typed simple parameters (e.g. `String[]`, `BigDecimal[]`,
`LocalDate[]`) follow the same aggregation-by-element-type principle as
bean-typed collections (below), extended to simple types:

- Spring aggregates every bean whose type matches the **element** type
  (e.g. every standalone `String` bean in the container) into the array —
  not beans of the array/collection type itself. A bean declared as
  `String[]` or as a `List<String>` is not itself a candidate for a
  `String[]` constructor parameter; only individual `String`-typed beans
  are.
- Zero matching elements produces an **empty array**, not `null`, and not
  a fatal error — this differs from the *scalar* simple-type zero-match
  case above, which is fatal. Within `constructor` mode itself: scalar
  zero-match is fatal, but collection/array zero-match of the same element
  type is a silent empty collection.

## Arrays / collections / Map\<String,T\> of bean (complex) types

The same aggregation behavior confirmed for `byType`'s collection
properties carries over cleanly to constructor-mode collection parameters:

- Array, `List<T>`, and `Set<T>` constructor parameters aggregate **every**
  eligible bean of type `T` in the container.
- `Map<String, T>` constructor parameters aggregate every eligible bean of
  type `T`, keyed by bean name — same `String`-key requirement as `byType`.
- Zero matching beans of type `T` produces an empty collection (or empty
  `Map`), not `null`, not fatal — same as the simple-type collection case
  above.
- A bean that already satisfies a single-valued constructor parameter
  (e.g. via `primary` resolving a scalar `Engine` parameter) can
  simultaneously also appear in an aggregated collection parameter on the
  *same* constructor — there is no exclusivity between single-valued and
  collection-valued consumption of the same bean.
- Explicit `<constructor-arg>` with a nested collection element
  (`<array>`/`<list>`/`<map>`) overrides autowiring for that specific
  parameter only, while other collection-typed and scalar-typed parameters
  on the same bean can still resolve via autowiring normally.
- Ambiguity resolution mechanisms (`primary`, `autowire-candidate="false"`)
  are not applicable to collection-typed parameters at all — every eligible
  bean is aggregated regardless, so there is no single-candidate ambiguity
  to resolve in the first place.

## Summary: constructor mode vs. byType, at a glance

| Behavior | `byType` (setter) | `constructor` |
|---|---|---|
| Matching basis | Type | Type |
| Zero-match, scalar | `null` | **Fatal** |
| Zero-match, collection | `null` (not empty collection) | **Empty collection** (not null) |
| Simple types excluded from scan | Yes | **No** |
| Arrays of simple types excluded | Yes | **No** |
| 2+ match, scalar, unresolved | Fatal (`NoUniqueBeanDefinitionException`) | Fatal (same exception) |
| `primary` / `autowire-candidate="false"` | Resolve ambiguity | Resolve ambiguity |
| Explicit wiring overrides autowiring | Yes | Yes |
| Injection style | Setters only | Constructor only |
| Multiple injection points per class | N/A (all setters wired independently) | Constructor **selection** logic (greediest satisfiable) |


<br>

---

<br>

# Sample run output

```txt
####### Auto-wiring mode "constructor" Demo (XML config) ###########


=== 1) beans-constructor-basics.xml ===
=======================================


[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

--- Basic Constructor Autowiring Demo ---
  engine       : Standard Engine (bean id = 'myEngine')
  transmission : Automatic Transmission (bean id = 'myTransmission')



=== 2) beans-constructor-greedy-selection.xml ===
=================================================


[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

--- Greedy Constructor Selection Demo ---
  engine       : Standard Engine (bean id = 'v8Engine')
  transmission : Automatic Transmission (bean id = 'autoTrans')
  gps          : GpsSystem [gpsModuleType=BATTERY_POWERED]



=== 3) beans-constructor-partial-match-forces-lesser-ctor.xml ===
=================================================================


[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission). ~~]

--- Partial Match Fallback Demo ---
  engine       : Standard Engine (bean id = 'baseEngine')
  transmission : Automatic Transmission (bean id = 'baseTrans')
  gps          : null (NOT wired)



=== 4) beans-constructor-zero-match-single-ctor.xml ===
=======================================================


Aug 01, 2026 4:07:16 PM org.springframework.context.support.AbstractApplicationContext refresh
WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'myCar' defined in class path resource [constructor/carDekho/resources/beans-constructor-zero-match-single-ctor.xml]: Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'constructor.carDekho.parts.AutomaticTransmission' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}

Context refresh failed as expected -- exception below:
  UnsatisfiedDependencyException: Error creating bean with name 'myCar' defined in class path resource [constructor/carDekho/resources/beans-constructor-zero-match-single-ctor.xml]: Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'constructor.carDekho.parts.AutomaticTransmission' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}


=== 5a) beans-constructor-ambiguity-primary.xml ===
===================================================


[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

--- car_resolvedByPrimary - Ambiguity Resolved via primary Demo ---
  engine       : Turbo Engine (bean id = 'sportEngine')
  transmission : Automatic Transmission (bean id = 'myTransmission')

--- car_explicitOverridesPrimary - explicit <constructor-arg ref="economyEngine"/> overrides even primary-based autowiring ---
  engine       : Standard Engine (bean id = 'economyEngine')
  transmission : Automatic Transmission (bean id = 'myTransmission')



=== 5b) beans-constructor-ambiguity-exclusion.xml ===
======================================================


[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]

--- car_resolvedByExclusion - ambiguous by type (2 Engine beans), resolved because autowire-candidate="false" removes 'economyEngine' from candidacy entirely ---
  engine       : Turbo Engine (bean id = 'sportEngine')
  transmission : Automatic Transmission (bean id = 'myTransmission')

--- car_explicitRefToExcludedEngine - explicit ref still resolves to the excluded autowire-candidate="false" ('economyEngine') bean ---
  engine       : Standard Engine (bean id = 'economyEngine')
  transmission : Automatic Transmission (bean id = 'myTransmission')



=== 6) beans-constructor-explicit-arg-overrides.xml ===
========================================================


[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)

--- car_byIndex - explicit constructor-arg by index ---
  engine       : Turbo Engine (bean id = 'raceEngine')
  transmission : Automatic Transmission (bean id = 'sportTrans')
  gps          : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]

--- car_byType - explicit constructor-arg by type ---
  engine       : Turbo Engine (bean id = 'raceEngine')
  transmission : Automatic Transmission (bean id = 'sportTrans')
  gps          : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]

--- car_byName - explicit constructor-arg by name ---
  engine       : Turbo Engine (bean id = 'raceEngine')
  transmission : Automatic Transmission (bean id = 'sportTrans')
  gps          : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]

--- car_mixedExplicitAndAutowired - engine explicit, transmission/gps autowired ---
  engine       : Standard Engine (bean id = 'econoEngine')
  transmission : Automatic Transmission (bean id = 'sportTrans')
  gps          : GpsSystem [gpsModuleType=OBD_PLUG_PLAY]



=== 7) beans-constructor-simple-type-excluded.xml ===
======================================================


[~~ CTOR called: BudgetCar(Engine engine, String color, String[] features,
                         BigDecimal[] tripDistances, LocalDate[] serviceDates). ~~]

'carColor' bean from context: Sinister Bronze
'featuresArray' bean from context: [Power windows, Advanced Driver Assistance System (ADAS), Sports Mode] (type: [Ljava.lang.String;)
'tripDistancesList' bean from context: [89.46, 97.20, 81.52] (type: java.util.ArrayList)
    Distance: 89.46 (type: java.math.BigDecimal)
    Distance: 97.20 (type: java.math.BigDecimal)
    Distance: 81.52 (type: java.math.BigDecimal)
'tripDistancesArray' bean from context: [89.46, 97.20, 81.52] (type: [Ljava.math.BigDecimal;)

--- Simple-Type Constructor Param Exclusion Demo ---
  engine        : Standard Engine (bean id = 'hatchEngine')
  color         : Sinister Bronze
  features      : [Sinister Bronze]
  tripDistances : [33, 55]
  serviceDates  : []



```

