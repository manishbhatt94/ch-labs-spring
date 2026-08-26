# Spring MVC — Pure XML Configuration

## Case 1: No Root WAC, Single DispatcherServlet

### Motivation

Most Spring MVC tutorials jump straight into annotations (`@Controller`, `@GetMapping`)
and convenience tags (`<mvc:annotation-driven/>`). This hides the actual infrastructure
Spring MVC sets up underneath. A beginner ends up with a working app but no mental model
of what is actually routing requests, what is invoking controller methods, or what is
resolving view names.

This project strips all of that away. Every piece of Spring MVC infrastructure is
declared explicitly as a `<bean>` in XML. No annotations on Java classes. No component
scanning. No `<mvc:annotation-driven/>`. The goal is to see what annotations and
convenience tags are hiding before using them.

This is **Case 1** of a two-case study:
- **Case 1 (this project):** No root `WebApplicationContext`. Single `DispatcherServlet`.
  All Spring beans in one XML file.
- **Case 2 (separate project):** Root `WebApplicationContext` via `ContextLoaderListener`
  + two `DispatcherServlet`. Proper three-context parent-child hierarchy.

---

### Setup

| Item | Choice |
|---|---|
| Java | 8 (Eclipse Temurin / Adoptium) |
| Spring Framework | 5.3.39 (vanilla — no Spring Boot) |
| Build tool | None — JARs added manually to `WEB-INF/lib` |
| IDE | Eclipse IDE for Enterprise Java (Dynamic Web Project) |
| Server | Apache Tomcat 9.0.x |
| Servlet spec | 2.3 DTD in `web.xml` |
| Config style | Pure XML at every layer — no Spring annotations on Java classes |

---

### Project Structure

```
YourProjectName/
├── src/main/
│   └── java/com/example/controller/
│       └── RegistrationController.java
└── src/main/webapp/
    ├── index.html
    └── WEB-INF/
        ├── lib/                        ← all Spring JARs + commons-logging
        ├── view/
        │   └── register.jsp
        ├── web.xml                     ← servlet container config
        └── dispatcher-servlet.xml      ← the one and only Spring config file
```

---

### How Pure XML Configuration Works Here

#### "web.xml" — servlet container config

Declares the `DispatcherServlet` with `<load-on-startup>1</load-on-startup>` so Spring
boots immediately at deploy time rather than on the first request. URL pattern `*.do`
routes all `.do` requests through it.

No `ContextLoaderListener` is declared — therefore **no root WebApplicationContext is
created**. The `DispatcherServlet`'s own `WebApplicationContext` is the only Spring
context in this application.

No `contextConfigLocation` `<context-param>` (which sets init parameters on the per web
application object: `ServletContext`) is declared either.

Neither is `contextConfigLocation` `<init-param>` (which sets init parameters on the
per-servlet object: `ServletConfig`) specified in the `<servlet>` tag
declaring the `DispatcherServlet`. So, to creates the WebApplicationContext (WAC)
specific to the `DispatcherServlet`, Spring automatically looks for
`/WEB-INF/[servlet-name]-servlet.xml` by convention, in absence of the
 `contextConfigLocation` `<init-param>` in the `<servlet>` tag.

#### `dispatcher-servlet.xml` — the only Spring config file

This is where the invisible becomes visible. Three infrastructure beans are declared
explicitly that annotation-based projects never show you:

**`BeanNameUrlHandlerMapping`** — maps incoming request URLs to controller beans by
matching the URL against each bean's `name` attribute in XML. This is the bean that
`@GetMapping("/registration.do")` replaces in annotation style.

**`SimpleControllerHandlerAdapter`** — `DispatcherServlet` never calls a controller
directly. It delegates to a `HandlerAdapter` that knows the calling convention for a
particular controller type. This adapter handles controllers that implement Spring's
`Controller` interface (i.e. `org.springframework.web.servlet.mvc.Controller`) via
its abstract method `handleRequest()`. This is what `<mvc:annotation-driven/>`
registers automatically in annotation style (as `RequestMappingHandlerAdapter`).

**`InternalResourceViewResolver`** — turns a logical view name like `"register"` into
a full JSP path `/WEB-INF/view/register.jsp` using a configured prefix and suffix.
This one exists in both XML and annotation-based projects — it is not hidden by
annotations.

The controller itself is also declared as a plain `<bean>` — no component scanning,
no `@Controller` annotation. Its `name` attribute doubles as the URL it handles.

#### "RegistrationController.java" — a plain Java class

Implements Spring's `Controller` interface
(`org.springframework.web.servlet.mvc.Controller` — not the `@Controller` annotation,
which is a completely different thing). This interface has exactly one method:
`handleRequest(HttpServletRequest, HttpServletResponse)` — the single entry point for
all requests routed to this controller.

Returns a `ModelAndView` — bundling the logical view name and model data together.
In annotation style, returning a `String` view name + accepting a `Model` parameter
achieves the same result; Spring assembles a `ModelAndView` internally from those.

No Spring annotations anywhere on this class.

---

### Request Pipeline

```
Browser: GET /YourProject/registration.do
    │
    ▼
Tomcat — matches *.do → hands off to DispatcherServlet
    │
    ▼
DispatcherServlet — asks HandlerMapping: who handles "/registration.do" ?
    │
    ▼
BeanNameUrlHandlerMapping — finds bean with name="/registration.do"
    │                        → RegistrationController
    ▼
SimpleControllerHandlerAdapter — calls handleRequest(request, response)
    │
    ▼
RegistrationController.handleRequest() — returns ModelAndView("register")
    │
    ▼
DispatcherServlet — asks ViewResolver: what is "register"?
    │
    ▼
InternalResourceViewResolver — resolves to /WEB-INF/view/register.jsp
    │
    ▼
Tomcat's JspServlet — renders JSP, substitutes ${message} from model
    │
    ▼
Browser: rendered HTML
```

---

### What Annotations Were Hiding

| Explicit in this project | Hidden by annotations |
|---|---|
| `BeanNameUrlHandlerMapping` bean | `@GetMapping("/registration.do")` |
| `SimpleControllerHandlerAdapter` bean | `<mvc:annotation-driven/>` |
| `implements Controller` + `handleRequest()` | `@Controller` + any method name |
| `ModelAndView` return type | Returning `String` + `Model` parameter |
| `<bean name="/registration.do">` in XML | `@GetMapping("/registration.do")` |
| No component scan — all beans explicit | `<context:component-scan>` |

