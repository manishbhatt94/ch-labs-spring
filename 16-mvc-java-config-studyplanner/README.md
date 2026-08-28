# Spring MVC — Pure Java Configuration

## Case 4: Root WAC + Single DispatcherServlet + Zero XML

### Motivation

Cases 1-3 always had at least one XML file driving the container — `web.xml` told Tomcat
what to register, and Spring XML files told the WACs what beans to create. Case 4
eliminates all of it. No `web.xml`. No `root-context.xml`. No `dispatcher-servlet.xml`.
Every piece of configuration is expressed in Java classes.

The mental model, the WAC hierarchy, the root/child split, the MVC infrastructure beans —
none of that changes. Only the language used to express it changes: XML → Java.

---

### Setup

| Item | Choice |
|---|---|
| Java | 8 (Eclipse Temurin / Adoptium) |
| Spring Framework | 5.3.39 (vanilla — no Spring Boot) |
| Build tool | None — JARs added manually to `WEB-INF/lib` |
| IDE | Eclipse IDE for Enterprise Java (Dynamic Web Project) |
| Server | Apache Tomcat 9.0.x |
| Servlet spec | 3.1 — programmatic registration requires Servlet 3.0+ |
| Config style | Pure Java — zero XML config files, no `web.xml` |

**JARs required:** same as Case 3 (`spring-core`, `spring-beans`, `spring-context`,
`spring-expression`, `spring-jcl`, `spring-web`, `spring-webmvc`,
`javax.annotation-api-1.3.2`, `jstl-1.2`)

---

### Project Structure

```
16-mvc-java-config-studyplanner/
├── src/main/java/
│   └── com/studyplanner/
│       ├── config/
│       │   ├── AppInitializer.java   ← replaces web.xml
│       │   ├── RootConfig.java       ← replaces root-context.xml
│       │   └── WebConfig.java        ← replaces dispatcher-servlet.xml
│       ├── service/
│       │   ├── Course.java
│       │   └── CourseService.java    ← @Service, root WAC bean
│       └── controller/
│           └── CourseController.java ← @Controller, child WAC bean
└── src/main/webapp/
    ├── static/
    │   └── style.css
    └── WEB-INF/
        └── views/
            ├── home.jsp
            ├── courses.jsp
            ├── course-detail.jsp
            └── course-form.jsp
```

No `web.xml`. No Spring XML files anywhere.

---

### URL Map

| URL | Method | Handler | Result |
|---|---|---|---|
| `/` | GET | `addViewControllers()` in `WebConfig` | `home.jsp` |
| `/courses` | GET | `CourseController.listCourses()` | `courses.jsp` |
| `/courses/{id}` | GET | `CourseController.courseDetail()` | `course-detail.jsp` |
| `/courses/new` | GET | `CourseController.showCourseForm()` | `course-form.jsp` |
| `/courses` | POST | `CourseController.addCourse()` | redirect → GET `/courses` |
| `/static/**` | GET | `addResourceHandlers()` in `WebConfig` | direct file |

---

### How Pure Java Config Works — The Full Chain

#### How Tomcat discovers the app without "web.xml"

Servlet 3.0 introduced `ServletContainerInitializer` — a standard API interface that
Tomcat calls at startup regardless of whether `web.xml` exists. Tomcat discovers
implementations via Java's `ServiceLoader` mechanism: it scans JARs for a file at
`META-INF/services/javax.servlet.ServletContainerInitializer`. That file exists inside
`spring-web.jar` and declares `SpringServletContainerInitializer`.

Tomcat calls `SpringServletContainerInitializer.onStartup()`, which scans YOUR app's
classes for anything implementing `WebApplicationInitializer`, then calls `onStartup()`
on each one found. `AppInitializer` is discovered this way.

```
Tomcat
  → META-INF/services file in spring-web.jar
  → SpringServletContainerInitializer.onStartup()
    → scans app for WebApplicationInitializer implementations
    → finds AppInitializer
    → calls AppInitializer (via AACDSI) which registers:
        ContextLoaderListener (root WAC)
        DispatcherServlet     (child WAC)
```

#### AppInitializer — replaces web.xml

Extends `AbstractAnnotationConfigDispatcherServletInitializer` (AACDSI). Three methods
replace everything `web.xml` declared:

| AACDSI method | Replaces in `web.xml` |
|---|---|
| `getRootConfigClasses()` | `<context-param> contextConfigLocation` + `<listener> ContextLoaderListener` |
| `getServletConfigClasses()` | `DispatcherServlet` reading `[name]-servlet.xml` |
| `getServletMappings()` | `<servlet-mapping><url-pattern>` |

`getRootConfigClasses()` returns an array — multiple `@Configuration` classes can feed
one root WAC (for splitting bean definitions across files). Same for
`getServletConfigClasses()`. Return `null` from `getRootConfigClasses()` if no root WAC
is desired (single context app).

For multiple `DispatcherServlet`s: create multiple AACDSI subclasses. Only the first
should return non-null `getRootConfigClasses()` — others return `null` to avoid
duplicate root WAC creation.

#### `RootConfig` — replaces `root-context.xml`

```java
@Configuration
@ComponentScan("com.studyplanner.service")
public class RootConfig { }
```

`@Configuration` = equivalent to the `<beans>` root element in XML — marks this class
as a source of bean definitions.

`@ComponentScan("com.studyplanner.service")` = equivalent to
`<context:component-scan base-package="..."/>` — finds `@Service`/`@Component` classes
and registers them as beans in the root WAC.

Scoped to service package only — NOT controller package. Controllers belong in the
child WAC. Scanning them here would register them in root WAC where `DispatcherServlet`
cannot find them.

AACDSI creates an `AnnotationConfigWebApplicationContext` (instead of
`XmlWebApplicationContext`) and registers `RootConfig.class` into it — same root WAC,
different config source format.

#### WebConfig — replaces "dispatcher-servlet.xml"z

```java
@Configuration
@EnableWebMvc
@ComponentScan("com.studyplanner.controller")
public class WebConfig implements WebMvcConfigurer { ... }
```

| Annotation / method | Replaces in `dispatcher-servlet.xml` |
|---|---|
| `@EnableWebMvc` | `<mvc:annotation-driven/>` |
| `@ComponentScan(...)` | `<context:component-scan base-package="..."/>` |
| `addViewControllers()` | `<mvc:view-controller path="/" view-name="home"/>` |
| `addResourceHandlers()` | `<mvc:resources mapping="/static/**" location="/static/"/>` |
| `@Bean viewResolver()` | `<bean class="InternalResourceViewResolver">` |

`@EnableWebMvc` registers the exact same infrastructure as `<mvc:annotation-driven/>`:
`RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, default
`HttpMessageConverter`s, type conversion, validation. Only ONE `@Configuration` class
per child WAC should carry this annotation — duplicating it causes bean conflicts.

`WebMvcConfigurer` is an interface with default empty implementations of all hook
methods — override only what you need. Each override method corresponds to one
`<mvc:...>` namespace tag.

`@Bean` methods replace `<bean>` declarations — method name = bean id, return value =
bean instance.

---

### Key New Concepts in This Project

#### `@ModelAttribute` — form binding

When an HTML form is submitted (POST), the browser sends field values as request
parameters (e.g. `name=Spring+MVC&description=A+course`). Without Spring, you'd read
these manually:

```java
String name = request.getParameter("name");
String description = request.getParameter("description");
Course course = new Course();
course.setName(name);
course.setDescription(description);
```

`@ModelAttribute` automates this entirely:

```java
@PostMapping("/courses")
public String addCourse(@ModelAttribute Course course) { ... }
```

Spring sees the POST, creates `new Course()` using the no-arg constructor, then matches
each form field name to a setter (`name` → `setName()`, `description` →
`setDescription()`) and calls them with the submitted values. You receive a fully
populated `Course` object.

**Critical requirement:** form field `name` attributes in the JSP must exactly match
the property names in the Java class (i.e. what comes after "set" in the setter, with
first letter lowercased). Mismatch = field arrives as null silently.

```html
<!-- "name" matches Course.setName() -->
<input type="text" name="name"/>

<!-- "description" matches Course.setDescription() -->
<textarea name="description"></textarea>
```

#### POST → Redirect → GET (PRG Pattern)

After a successful form POST, returning a view name directly causes a problem: if the
user hits browser Refresh, the browser re-submits the POST — adding a duplicate entry.

```java
// WRONG — refresh re-submits POST, adds duplicate course every time
@PostMapping("/courses")
public String addCourse(@ModelAttribute Course course) {
    courseService.addCourse(course);
    return "courses"; // renders view directly after POST
}
```

The fix is to redirect after POST:

```java
// CORRECT — redirect sends HTTP 302, browser issues new GET /courses
@PostMapping("/courses")
public String addCourse(@ModelAttribute Course course) {
    courseService.addCourse(course);
    return "redirect:/courses"; // DispatcherServlet sends 302 to browser
}
```

`"redirect:/courses"` is a special return value prefix that `DispatcherServlet`
recognises — it sends an HTTP 302 response to the browser pointing to `/courses`.
The browser then issues a fresh GET request to `/courses`. Refreshing that GET page
is safe — it just re-fetches the list without re-submitting anything.

This is called the **Post/Redirect/Get (PRG) pattern** and is standard practice for
any form submission that modifies state.

#### @Service vs explicit \<bean\> declaration

In Cases 1 and 2, `ArticleService` was declared explicitly:
```xml
<bean id="articleService" class="com.example.ArticleService"/>
```

In this project, `CourseService` uses:
```java
@Service
public class CourseService { ... }
```

Combined with `@ComponentScan("com.studyplanner.service")` in `RootConfig`, the result
is identical — one singleton `CourseService` instance registered in the root WAC.
`@Service` is functionally the same as `@Component` but signals service-layer intent.

---

### Complete XML → Java Mapping Reference

| XML (Cases 1–3) | Java (Case 4) |
|---|---|
| `web.xml` | `AppInitializer extends AACDSI` |
| `root-context.xml` | `RootConfig.java` (`@Configuration`) |
| `dispatcher-servlet.xml` | `WebConfig.java` (`@Configuration`) |
| `XmlWebApplicationContext` | `AnnotationConfigWebApplicationContext` |
| `<context-param> contextConfigLocation` | `getRootConfigClasses()` |
| `<listener> ContextLoaderListener` | handled internally by AACDSI |
| `<servlet> DispatcherServlet` | handled internally by AACDSI |
| `<servlet-mapping> <url-pattern>` | `getServletMappings()` |
| `<beans>` root element | `@Configuration` |
| `<bean id="x" class="Y"/>` | `@Bean` method returning `Y` |
| `<context:component-scan>` | `@ComponentScan` |
| `<mvc:annotation-driven/>` | `@EnableWebMvc` |
| `<mvc:view-controller/>` | `addViewControllers()` in `WebMvcConfigurer` |
| `<mvc:resources/>` | `addResourceHandlers()` in `WebMvcConfigurer` |
| `<property ref="..."/>` (XML injection) | `@Autowired` |
| `@Service` + scan (Case 3 style) | `@Service` + `@ComponentScan` in `RootConfig` |

---

### Documentation Reference

All from Spring 4.3.x:
`https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/mvc.html`

| Section | Topic |
|---|---|
| § 22.15 | Code-based Servlet container initialization — `WebApplicationInitializer`, `AACDSI` |
| § 22.16.1 | Enabling MVC Java Config — `@EnableWebMvc`, `WebMvcConfigurer` |
| § 22.16.2 | Customizing provided configuration — overriding `WebMvcConfigurer` methods |
| § 22.3.3 | Defining `@RequestMapping` handler methods — `@ModelAttribute`, `@PathVariable`, `Model` |
| § 22.3.3.4 | Supported method return values — `"redirect:"` prefix, PRG pattern |
| § 22.16.9 | `mvc:resources` / `addResourceHandlers()` |

For `@Configuration`, `@ComponentScan`, `@Bean` — these are core Spring IoC concepts,
not MVC-specific. Reference:
`https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/beans.html`

| Section | Topic |
|---|---|
| § 7.10.2 | `@Bean` annotation |
| § 7.10.3 | `@Configuration` annotation |
| § 7.10.4 | `@ComponentScan` |

---

### Side Notes

#### Why does ServletContainerInitializer interface method onStartup(..) takes a parameter of type Set of Class?

`ServletContainerInitializer.onStartup(Set<Class<?>> c, ServletContext ctx);`

Why a Set of Class is required?

The `Set<Class<?>> c` parameter is tied to a companion annotation `@HandlesTypes` that 
can be placed on a `ServletContainerInitializer` implementation.

It tells Tomcat:
> "when you call my onStartup(), pass me all classes in the app that
> implement/extend/annotate this type."

Spring's SpringServletContainerInitializer declares:

```java
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext ctx) {
        // webAppInitializerClasses = all classes in YOUR app implementing WAI
    }
}
```

So Tomcat scans your app's classpath, collects every class implementing `WebApplicationInitializer`,
and passes them all as that `Set`. Spring then instantiates each one and calls
`onStartup(ServletContext)` on each.


#### Can we have more than one WebApplicationInitializer implementation?

Yes — absolutely. The Set exists precisely for this reason. You could have:

```java
public class SecurityInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext ctx) {
        // register a security filter
        ctx.addFilter("springSecurityFilter", new DelegatingFilterProxy())
           .addMappingForUrlPatterns(null, false, "/*");
    }
}

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    // registers ContextLoaderListener + DispatcherServlet
}
```

Both get discovered and both get called.

The pattern is: *"one initializer per concern"*, NOT "one initializer for everything".

`AbstractAnnotationConfigDispatcherServletInitializer` handles the Spring MVC wiring;
a separate `WebApplicationInitializer` handles security filter registration;
another could handle something else. Clean separation.


#### How do you register multiple DispatcherServlets in pure Java config?

This is where `AbstractAnnotationConfigDispatcherServletInitializer` (AACDSI) hits its limit —
it only wires one DispatcherServlet (DS). For multiple DS, you either:

**Option A** — multiple `WebApplicationInitializer` implementations, one per DS:

```java
public class NewsInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class }; // only one should return root config
    }
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { NewsWebConfig.class };
    }
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/news/*" };
    }
}

public class AdminInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; // root WAC already registered by NewsInitializer — return null here
    }
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { AdminWebConfig.class };
    }
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/admin/*" };
    }
}
```

Note the `null` for `getRootConfigClasses()` in `AdminInitializer` — exactly matching the
Javadoc for this `getRootConfigClasses` method:
> "@return null if creation and registration of a root context is not desired."

Only one initializer should create the root WAC; the others return null to avoid creating
duplicate root contexts.


**Option B** — implement WebApplicationInitializer directly (one class, full control):

```java
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext ctx) {
        // Root WAC
        AnnotationConfigWebApplicationContext rootContext = 
            new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);
        ctx.addListener(new ContextLoaderListener(rootContext));

        // News DS + child WAC
        AnnotationConfigWebApplicationContext newsContext = 
            new AnnotationConfigWebApplicationContext();
        newsContext.register(NewsWebConfig.class);
        ServletRegistration.Dynamic newsDs = 
            ctx.addServlet("news", new DispatcherServlet(newsContext));
        newsDs.setLoadOnStartup(1);
        newsDs.addMapping("/news/*");

        // Admin DS + child WAC
        AnnotationConfigWebApplicationContext adminContext = 
            new AnnotationConfigWebApplicationContext();
        adminContext.register(AdminWebConfig.class);
        ServletRegistration.Dynamic adminDs = 
            ctx.addServlet("admin", new DispatcherServlet(adminContext));
        adminDs.setLoadOnStartup(2);
        adminDs.addMapping("/admin/*");
    }
}
```

This is the most explicit — equivalent to what `web.xml` declared in Case 2, now in
Java. Full visibility, no magic.


#### Replicating all four WebApplicationContext (WAC) cases in pure Java

| Case | `getRootConfigClasses()` | `getServletConfigClasses()` | Notes |
| --- | --- | --- | --- |
| No root WAC, one DS | `return null` | `return new Class[]{WebConfig.class}` | Simple single-context app |
| No root WAC, two DS | Two `AACDSI` subclasses, both return `null` for root | Each returns its own `WebConfig` | Unusual — sibling contexts with no shared parent |
| One root WAC, one DS | `return new Class[]{RootConfig.class}` | `return new Class[]{WebConfig.class}` | Most common setup |
| One root WAC, two DS | First initializer returns `RootConfig.class`, second returns `null` | Each returns own `WebConfig` | Case 2 equivalent in Java |

