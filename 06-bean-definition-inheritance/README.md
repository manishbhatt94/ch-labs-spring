# Bean Definition Inheritance

- [Spring 5.3.39 &sect; 1.7. Bean Definition Inheritance](https://docs.spring.io/spring-framework/docs/5.3.39/reference/html/core.html#beans-child-bean-definitions)
- [Spring Latest &sect; Bean Definition Inheritance](https://docs.spring.io/spring-framework/reference/core/beans/child-bean-definitions.html)


## Summary: Bean Definition Inheritance

- **What it is:** A bean definition can hold a lot of configuration
  (constructor arguments, property values, container-specific settings like
  init-method, a static factory method name, etc.). A **child** bean definition
  inherits configuration data from a **parent** bean definition, and can
  override some values or add new ones. This saves repetitive typing —
  effectively a form of templating.
- **XML-only concept (in practice):** This is expressed through the `parent`
  attribute when using XML-based configuration metadata (e.g. with
  `ClassPathXmlApplicationContext`). Programmatically, child bean definitions
  are represented by the `ChildBeanDefinition` class, but most users configure
  this declaratively in XML rather than working with it at that level.
- **Not Java/OOP inheritance:** It inherits **configuration data** (values),
  not Java instance variables, methods, or types.
- **`parent` attribute:** Set on the child `<bean>`, with its value being the
  `id` of the parent bean definition to inherit from.
- **Bean class in the child:** A child bean definition uses the parent's bean
  class if the child doesn't specify one — but it can also override it with its
  own class. If it does, that class must be compatible with the parent (i.e.,
  it must accept the parent's property values).
- **`abstract="true"` attribute (on the parent bean):**
  - Marks the bean definition as a pure *template* — usable only as a parent
     for child definitions, not instantiable on its own.
  - Trying to use an `abstract` bean on its own — either via an explicit
     `getBean()` call using its `id`, or by referencing it as another bean's
     `ref` property — results in an error. The container's internal
     pre-instantiation logic also skips beans marked `abstract`.
- **What if the parent doesn't specify a `class`?** Then marking it
  `abstract="true"` is *required* — a class-less parent bean definition is
  incomplete and cannot be instantiated as-is.
- **What if `abstract` is not set on a parent that *does* specify a class?**
  - `ApplicationContext` pre-instantiates all singleton beans by default. So if
     a parent bean definition specifies a class and is intended only as a
     template, you must explicitly set `abstract="true"` — otherwise, the
     container will actually attempt to pre-instantiate that "parent" bean as a
     real, concrete, usable bean too.
- **What gets inherited from parent to child** (child can override any of these):
  - `scope`
  - constructor argument values
  - property values
  - method overrides
  - Additionally, initialization method, destroy method, and static factory
     method settings are inherited from the parent if the child doesn't specify
     its own — if the child does specify one, it overrides the parent's
     corresponding setting.
- **What is *never* inherited** — these are always taken directly from the
  child bean definition itself, regardless of the parent:
  - `depends-on`
  - `autowire` mode
  - `dependency-check`
  - `singleton` (legacy attribute, superseded by `scope` in modern Spring, but
     still documented as not inherited)
  - `lazy-init`
- **Use case:** When multiple bean definitions share a chunk of identical
  configuration (property values, constructor args, etc.) and only some values
  differ per bean — the shared values are declared once in the parent, and each
  child only specifies what's different.
- **Pros:** Reduces duplicate XML; centralizes shared configuration so updating
  the parent updates all children.
- **Cons:** Adds indirection — you need to check the parent bean definition to
  know a child's full effective configuration; it's a mechanism tied
  specifically to XML-based configuration.


---

## Sample Example Scenario: Alert Notification Services

**Alert Notification Services:** Multiple alert-sending services (Critical,
Info, etc.) share the same email server configuration, but each has its own
unique subject prefix and priority level. Bean Definition Inheritance lets the
shared configuration be declared once instead of being duplicated in every
bean.

_**Scenario: Alert Notification Services**_

**The Problem:**

Imagine a company sends alerts via email — critical system alerts, and
routine info alerts. Both alert types connect to the *same* SMTP server, use
the *same* "from" address, but differ in their subject prefix and priority
level.

Without Bean Definition Inheritance, you'd have to repeat the SMTP host, port,
and from-address in every single `<bean>` definition — and if the mail server
ever changes, you'd have to hunt down and update it in multiple places.

**The Solution:**

An `abstract="true"` parent bean definition holds the shared SMTP
configuration. Each child bean definition uses `parent="..."` to inherit
those common values automatically, and only needs to specify the properties
that are actually different for it (subject prefix, priority level).

**Sample Demo:**

Check out:
- Package: [com.alertsDemo](./src/com/alertsDemo/)
- Main class: [com.alertsDemo.MainApp](./src/com/alertsDemo/MainApp.java)
- Bean class: [com.alertsDemo.AlertService](./src/com/alertsDemo/AlertService.java)
- Bean config: [com.alertsDemo/beans.xml](./src/com/alertsDemo/beans.xml)


### Sample Output

```txt
===== Demo of AlertService bean named [debugAlertService] =======
Connecting to smtp.company.com:587
From: alerts@company.com
Subject: [DEBUG] Database connection pool ready in 240 milliseconds
Priority: 100
-----

===== Demo of AlertService bean named [infoAlertService] =======
Connecting to smtp.company.com:587
From: alerts@company.com
Subject: [INFO] Nightly backup completed
Priority: 300
-----

===== Demo of AlertService bean named [criticalAlertService] =======
Connecting to smtp.company.com:587
From: critical-incident-alerts@company.com
Subject: [CRITICAL] Server disk usage above 95%
Priority: 900
-----

```


---

## Sample Example Scenario: Notification Channel Exporters

**Notification Channel Exporters:** Three completely unrelated classes —
Email, SMS, and Push notifiers — share common sender configuration (company
name, environment, timestamp format), while each has its own channel-specific
setting. Demonstrates that Bean Definition Inheritance works across different
Java classes, not just within a single class hierarchy.

_**Scenario: Notification Channel Exporters**_

**The Problem:**

Imagine a company sends notifications through three different channels —
Email, SMS, and Push. All three need the same sender identity, environment
name, and timestamp format. But each channel is backed by a *completely
different, unrelated Java class* — there's no shared superclass between them
in Java.

Since the classes don't share a Java type hierarchy, normal Java inheritance
can't help here — yet the XML configuration for all three still has a lot of
duplicated values.

**The Solution:**

A `parent` bean definition with **no `class` attribute at all** acts as a
pure configuration template — it holds only the shared property values, not
an actual object type. Each child bean definition supplies its own unrelated
`class` and inherits the shared configuration values, adding only what's
unique to it.

**Sample Demo:**

Check out:
- Package: [com.notifyChannels](./src/com/notifyChannels/)
- Main class: [com.notifyChannels.MainApp](./src/com/notifyChannels/MainApp.java)
- Bean classes: `EmailNotifier`, `SmsNotifier`, `PushNotifier`
- Bean config: [com.notifyChannels/beans.xml](./src/com/notifyChannels/beans.xml)


### Sample Output

```txt

===== Demo of EmailNotifier type bean: =======
[Email via smtp.company.com] Password reset requested (retries=2, timeout=5000ms)

===== Demo of SmsNotifier type bean: =======
[SMS via +1-800-555-0100] Your OTP is 4821 (retries=3, timeout=5000ms)

===== Demo of PushNotifier type bean: =======
[Push to app com.company.app] New message received (retries=3, timeout=5000ms)

```

<br>

---
