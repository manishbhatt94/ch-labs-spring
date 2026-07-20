# Bean Post Processor

- [Spring 5.3.39 &sect; 1.8.1. Customizing Beans by Using a BeanPostProcessor](https://docs.spring.io/spring-framework/docs/5.3.39/reference/html/core.html#beans-factory-extension-bpp)
- [Spring Latest &sect; Customizing Beans by Using a BeanPostProcessor](https://docs.spring.io/spring-framework/reference/core/beans/factory-extension.html#beans-factory-extension-bpp)


Brief notes:

1. Create a BeanPostProcessor by implementing the interface
   `org.springframework.beans.factory.config.BeanPostProcessor`
1. Override it's two methods:
    - `public Object postProcessBeforeInitialization(Object bean, String beanName);`
    - `public Object postProcessAfterInitialization(Object bean, String beanName);`


---

## Sample Example Scenario: Config Trim / String Cleaner

**Config Trim / String Cleaner:** Scans all beans for String fields and
automatically trims trailing/leading spaces. This prevents hard-to-find bugs
caused by accidental whitespaces in configuration properties or file paths.

_**Scenario: Config Trim / String Cleaner**_

**The Problem:**

When you configure data via XML or properties files, it is
easy to accidentally type an extra space (e.g.,
`<property name="url" value="  http://localhost:8080     " />`).

This hidden space will break database connections or API calls later, throwing
hard-to-debug errors.

**The Solution:**

A `BeanPostProcessor` can intercept the bean right after its properties are
set, scan for any `String` variables, and automatically run `.trim()` on them
before the rest of your application ever uses the bean.

**Sample Demo:**

Check out:
- Package: [com.stringCleanDemo](./src/com/stringCleanDemo/)
- Main class: [com.stringCleanDemo.MainApp](./src/com/stringCleanDemo/MainApp.java)
- `BeanPostProcessor` sample implementation: [com.stringCleanDemo.processor.StringTrimmingPostProcessor](./src/com/stringCleanDemo/processor/StringTrimmingPostProcessor.java)


### Sample Output

```txt
--- Starting Spring Application Context ---

[BPP - Before Init] Checking raw URL string: 'jdbc:mysql://localhost:3306/prod_db    '
[BPP - Before Init] Sanitized URL to: 'jdbc:mysql://localhost:3306/prod_db'
   [Bean Init] Initialising pool using URL length: 35
   [Bean Init] Connection pool successfully ready.
[BPP - After Init] Post-validation check for bean: myDatabaseConfig
[BPP - After Init] Final clean username configuration: 'admin_user'


--- Context Fully Initialized ---

Main App Verified URL: 'jdbc:mysql://localhost:3306/prod_db'

```


---

## Sample Example Scenario: Timezone Normalisation via Custom Field Annotations

**Timezone Normalisation via Custom Field Annotations:**

Scans all initialized beans across the container for specific `LocalDateTime`
variables marked with a custom runtime tag and automatically converts their
values from Indian Standard Time (IST) to Coordinated Universal Time (UTC).

This highlights how a single `BeanPostProcessor` can intercept multiple
structurally unique bean types simultaneously to enforce a global architectural
constraint without code duplication.

_**Scenario: Timezone Normalisation via Custom Field Annotations**_

**The Problem:**

When building backend services, user-facing layers often receive payloads
containing timestamp inputs tied to the end user's regional timezone (such as
Indian Standard Time).

However, best practices dictate that core application logic, mathematical
time-span computations, and database persistence layers must operate strictly
inside a standardized time format (UTC).

Forcing every business service class to write its own repetitive parsing and
timezone shift calculations clutters the domain logic and introduces high
conversion bug risks if a developer misses a field.

**The Solution:**

A global `BeanPostProcessor` can intercept every bean instance right after
property injection but before the bean's custom `init-method` fires.

By utilizing clean Java Reflection, it dynamically loops through all internal
instance fields, detects an explicit custom marker annotation
(`@ConvertToUTC`), bypasses standard private access restrictions, and
normalizes the target timezone data directly in memory across the entire
container application landscape automatically.

**Sample Demo:**

Check out:
- Package: [com.convertIstUtc](./src/com/convertIstUtc/)
- Main class: [com.convertIstUtc.MainApp](./src/com/convertIstUtc/MainApp.java)
- `BeanPostProcessor` sample implementation: [com.convertIstUtc.processor.TimezoneNormalizationProcessor](./src/com/convertIstUtc/processor/TimezoneNormalizationProcessor.java)


### Sample Output

```txt
====== Bootstrap Spring Engine ======


[BPP-Before] Intercepted 'orderService' | Found IST Time: 2026-07-20T18:30
[BPP-Before] Normalised field 'bookingTime' to UTC: 2026-07-20T13:00
   [Init-Method] OrderService verified bookingTime: 2026-07-20T13:00
[BPP-After] Compliance Audit Passed for 'orderService'. Certified state: 2026-07-20T13:00


[BPP-Before] Intercepted 'userService' | Found IST Time: 2026-07-20T23:45
[BPP-Before] Normalised field 'registrationTime' to UTC: 2026-07-20T18:15
   [Init-Method] UserService verified registrationTime: 2026-07-20T18:15
[BPP-After] Compliance Audit Passed for 'userService'. Certified state: 2026-07-20T18:15


[BPP-Before] Intercepted 'deliveryService' | Found IST Time: 2026-07-20T09:00
[BPP-Before] Normalised field 'dispatchTime' to UTC: 2026-07-20T03:30
   [Init-Method] DeliveryService verified dispatchTime: 2026-07-20T03:30
[BPP-After] Compliance Audit Passed for 'deliveryService'. Certified state: 2026-07-20T03:30


====== Engine Bootstrapped Successfully ======

   [Destroy-Method] DeliveryService verified dispatchTime: 2026-07-20T03:30
   [Destroy-Method] UserService verified registrationTime: 2026-07-20T18:15
   [Destroy-Method] OrderService verified bookingTime: 2026-07-20T13:00
```

<br>

---

<br>

## 💡 Introduction to Custom Annotations in Java

Annotations are **passive markers** or "sticky notes" that you stick onto your
code elements (such as classes, methods, or fields). They do not contain any
executable logic.

Instead, external engines (like standard Java Reflection or the Spring
Framework container) scan and process these markers at runtime to alter
application behaviors dynamically.

### The Basic Syntax

To create a custom annotation, you use the `@interface` keyword instead of
`class` or `interface`. You then explicitly define its usage rules and
architectural lifespan using Java's built-in meta-annotations:

```java
package com.convertIstUtc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 1. Where can developers paste this annotation?
@Target(ElementType.FIELD) 

// 2. How long does this annotation survive?
@Retention(RetentionPolicy.RUNTIME) 

public @interface ConvertToUTC {
    // This empty body serves as a clean binary flag (On/Off switch).
}
```

---

### Understanding &nbsp;`@Target`&nbsp; (The Application Scope)

The `@Target` meta-annotation defines a strict compiler guardrail, restricting
the specific structural locations where a developer is physically allowed to
type your custom annotation.

While Java supports a wide variety of advanced target types—including
`ElementType.PARAMETER` for method inputs, `ElementType.CONSTRUCTOR` for object
creation blocks, and `ElementType.LOCAL_VARIABLE` for method-level variables,
the three most common configurations you will encounter in everyday Spring
framework development are:

- `ElementType.FIELD`: Restricts the annotation exclusively to instance
  properties or class-level variables (perfect for isolating our target time metrics).
- `ElementType.METHOD`: Restricts placement to functional method signatures
  (such as standard lifecycle markers like `@PostConstruct`).
- `ElementType.TYPE`: Restricts placement to the top-level declaration headers
  of classes, interfaces, or enums (such as Spring's core stereotype annotation
  `@Component`).

---

### Deep Dive: Understanding &nbsp;`@Retention`&nbsp; (The Metadata Lifetime)

The `@Retention` policy controls the compiled permanence of your annotation. It
signals to the Java compiler and the JVM exactly when your custom metadata flag
must be completely stripped away and destroyed. 

To understand this fully as a beginner, keep in mind that Java code moves
sequentially through three distinct lifecycle stages:

```txt
[1. Source Stage] (.java text files) ➡️ [2. Compilation Stage] (.class bytecodes) ➡️ [3. Runtime Stage] (RAM/JVM Execution)
```

The three available retention options dictate exactly how far along this
timeline your annotation is allowed to survive:

#### 1. &nbsp;`RetentionPolicy.SOURCE`&nbsp; (Discarded during Compilation)

- **What it does:** This policy limits the annotation's life exclusively to the
  raw text inside your `.java` files. The very instant you build your project
  or compile your code, the Java compiler (`javac`) reads it for validation
  purposes and then completely erases it. As a result, the annotation is 100%
  absent from the compiled `.class` binary bytecode files generated on your
  disk.
- **Real-world Example:** The standard `@Override` or `@SuppressWarnings`
  annotations. They exist solely to help the Eclipse compiler perform safety
  cross-checks while you write code. Once compilation passes, the running
  program has no structural use for them.
- **Why it fails for Spring BPPs:** A `BeanPostProcessor` executes its logic
  long after compilation has ended. If your custom tag uses `SOURCE` retention,
  the tag is deleted during the compilation phase, leaving no metadata markers
  behind for Spring to find at runtime.

#### 2. &nbsp;`RetentionPolicy.CLASS`&nbsp; (Discarded during JVM Loading)

- **What it does:** This is the absolute fallback default setting in Java if
  you omit the `@Retention` line from your custom annotation definition. The
  compiler handles this by successfully recording the annotation metadata
  directly inside the compiled `.class` binary files on your hard drive.
  However, when you boot your application, the class-loader mechanism of the
  Java Virtual Machine (JVM) intentionally ignores this metadata and refuses to
  load it into active system memory.
- **When it is used:** It is primarily utilized by heavy, offline bytecode
  manipulation toolsets or security scanners that read compiled project `.jar`
  files straight off the disk without actually launching or spinning up the
  live application environment.
- **Why it fails for Spring BPPs:** Because the JVM actively discards this
  metadata when pulling your compiled class files into runtime RAM, your
  application beans will materialize as plain, un-flagged instances. Spring's
  reflection engine will see absolutely nothing on your properties.

#### 3. &nbsp;`RetentionPolicy.RUNTIME`&nbsp; (Survives Permanently in JVM Memory)

- **What it does:** This policy guarantees the maximum lifespan for your custom
  metadata. The annotation is safely baked into the compiled `.class` bytecode
  file on your file system, **and** the JVM explicitly preserves and loads that
  metadata directly into active memory allocations (RAM) when your application
  starts running. It remains completely alive, attached to the class structure,
  for the entire duration of the application execution lifecycle.
- **Why it is Mandatory for Spring BPPs:** Spring's `BeanPostProcessor` works
  directly with live runtime heap instances. It relies on standard Java
  Reflection (`java.lang.reflect`) to inspect active memory structures on the
  fly, asking queries like:
  *"Does this active variable instance hold the `@ConvertToUTC` flag right now?"*
  This direct in-memory inspection is only technically achievable if the
  annotation's lifetime is explicitly declared as `RUNTIME`.

