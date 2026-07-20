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
