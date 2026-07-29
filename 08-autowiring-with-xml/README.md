# Autowiring (With XML based configuration metadata)

- [Spring 5.3.39 - &sect; 1.4. Dependencies - &sect; Autowiring Collaborators](https://docs.spring.io/spring-framework/docs/5.3.39/reference/html/core.html#beans-factory-autowire)
- [Spring Latest - &sect; Autowiring Collaborators](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-autowire.html)
- [Spring Latest - &sect; Fine-tuning Annotation-based Autowiring with @Primary or @Fallback](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired-primary.html)
- [Spring Latest - &sect; Fine-tuning Annotation-based Autowiring with Qualifiers](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired-qualifiers.html)


## Rough Notes

- You can let Spring resolve collaborators (other beans) automatically for your
  bean by inspecting the contents of the ApplicationContext.
- Autowiring can significantly reduce the need to specify properties or
  constructor arguments.
- When using XML-based configuration metadata, you can specify the autowire
  mode for a bean definition with the `autowire` attribute of the `<bean/>`
  element.
- The autowiring functionality has four modes.
- You specify autowiring per bean and can thus choose which ones to autowire.
- With `byType` or `constructor` autowiring mode, you can wire arrays and typed
  collections. In such cases, all autowire candidates within the container that
  match the expected type are provided to satisfy the dependency.
- You can autowire strongly-typed `Map` instances if the expected key type is
  `String`. An autowired `Map` instance's values consist of all bean instances
  that match the expected type, and the `Map` instance's keys contain the
  corresponding bean names.
- Explicit dependencies in `property` and `constructor-arg` settings always
  override autowiring.
- You cannot autowire simple properties such as primitives, `String`'s, and
  `Class`'s (and arrays of such simple properties). This limitation is
  by-design.
- Multiple bean definitions within the container may match the type specified
  by the setter method or constructor argument to be autowired.
- For arrays, collections, or `Map` instances, this is not necessarily a
  problem.
- However, for dependencies that expect a single value, this ambiguity is not
  arbitrarily resolved. If no unique bean definition is available, an exception
  is thrown.
- Because autowiring by type may lead to multiple candidates, it is often
  necessary to have more control over the selection process. One way to
  accomplish this is below.
- Designate a single bean definition as the primary candidate by setting the
  `primary` attribute of its `<bean/>` element to `true`.
- On a per-bean basis, you can exclude a bean from autowiring. In Spring’s XML
  format, set the `autowire-candidate` attribute of the `<bean/>` element to
  `false`.
- The container makes that specific bean definition unavailable to the
  autowiring infrastructure.
- The `autowire-candidate` attribute is designed to only affect type-based
  autowiring. It does not affect explicit references by name, which get
  resolved even if the specified bean is not marked as an autowire candidate.
- You can also limit autowire candidates based on pattern-matching against
  bean names. The top-level `<beans/>` element accepts one or more patterns
  within its `default-autowire-candidates` attribute.
- Use of `<qualifier value="..">` tag nested inside `<bean/>` tag.


| Autowiring Mode | Explanation |
| --- | --- |
| `no` | (Default) No autowiring. Bean references must be defined by `ref` elements. Changing the default setting is not recommended for larger deployments, because specifying collaborators explicitly gives greater control and clarity. To some extent, it documents the structure of a system. |
| `byName` | Autowiring by property name. Spring looks for a bean with the same name as the property that needs to be autowired. For example, if a bean definition is set to autowire by name and it contains a `master` property (that is, it has a `setMaster(..)` method), Spring looks for a bean definition named `master` and uses it to set the property. |
| `byType` | Lets a property be autowired if exactly one bean of the property type exists in the container. If more than one exists, a fatal exception is thrown, which indicates that you may not use `byType` autowiring for that bean. If there are no matching beans, nothing happens (the property is not set). |
| `constructor` | Analogous to `byType` but applies to constructor arguments. If there is not exactly one bean of the constructor argument type in the container, a fatal error is raised. |


## Code Examples

- Auto-wiring mode **"byName"**
    - Package [byName.carDekho](./src/byName/carDekho/README.md)

