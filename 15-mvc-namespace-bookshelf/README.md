# Spring MVC — MVC XML Namespace Configuration

## Case 3: Root WAC + Single DispatcherServlet + Annotation-Driven Controllers

### Motivation

Case 1 and Case 2 made every piece of Spring MVC infrastructure explicit — declaring
`BeanNameUrlHandlerMapping`, `SimpleControllerHandlerAdapter`, and controller beans
manually in XML, with controllers implementing Spring's `Controller` interface directly.

Case 3 introduces the **middle ground** that most real-world legacy Spring projects use:
- XML still drives the container config (`web.xml`, `root-context.xml`, `dispatcher-servlet.xml`)
- But the MVC XML namespace tags (`<mvc:annotation-driven/>`, `<mvc:resources/>`,
  `<mvc:view-controller/>`) replace the verbose explicit bean declarations
- And Java annotations (`@Controller`, `@GetMapping`, `@Autowired`) replace
  `implements Controller`, `handleRequest()`, and XML `<property>` injection

This is the layer that makes `<mvc:annotation-driven/>` meaningful — having seen what
it replaces in Cases 1 and 2, it's no longer magic.

---

### Setup

| Item | Choice |
|---|---|
| Java | 8 (Eclipse Temurin / Adoptium) |
| Spring Framework | 5.3.39 (vanilla — no Spring Boot) |
| Build tool | None — JARs added manually to `WEB-INF/lib` |
| IDE | Eclipse IDE for Enterprise Java (Dynamic Web Project) |
| Server | Apache Tomcat 9.0.x |
| Servlet spec | **3.1** (`web-app_3_1.xsd`) — upgraded from 2.3 in Cases 1 & 2 |
| Config style | XML container config + MVC namespace tags + Java annotations on classes |

**JARs required** (all in `WEB-INF/lib`): `spring-core`, `spring-beans`, `spring-context`,
`spring-expression`, `spring-jcl`, `spring-web`, `spring-webmvc`,
`javax.annotation-api-1.3.2`, `jstl-1.2`

> Servlet API JAR — do NOT add to `WEB-INF/lib`. Provided by Tomcat runtime.

---

### Project Structure

```
15-mvc-namespace-bookshelf/
├── src/main/java/
│   └── com/bookshelf/
│       ├── service/
│       │   ├── Book.java
│       │   └── BookService.java        ← root WAC bean (explicit <bean> in XML)
│       └── controller/
│           └── BookController.java     ← child WAC bean (@Controller, component-scanned)
└── src/main/webapp/
    ├── static/
    │   └── style.css                   ← served via <mvc:resources/>
    └── WEB-INF/
        ├── lib/
        ├── views/
        │   ├── home.jsp
        │   ├── books.jsp
        │   └── book-detail.jsp
        ├── web.xml
        ├── root-context.xml
        └── dispatcher-servlet.xml
```

---

### URL Map

| URL | Handler | View |
|---|---|---|
| `/` | `<mvc:view-controller/>` — no Java code | `home.jsp` |
| `/books` | `BookController.listBooks()` | `books.jsp` |
| `/books/{id}` | `BookController.bookDetail()` | `book-detail.jsp` |
| `/static/**` | `<mvc:resources/>` — bypasses controllers | direct file |

---

### How the Config Works

#### web.xml — Servlet 3.1

Same two-context structure as Case 2: `ContextLoaderListener` builds root WAC from
`root-context.xml` before `DispatcherServlet` initializes its child WAC from
`dispatcher-servlet.xml`.

Key difference from Cases 1 & 2: `DispatcherServlet` is mapped to **`/`** instead of
`*.do`. This intercepts everything — including static file requests — which is why
`<mvc:resources/>` becomes necessary. With `*.do` mapping, static files never reached
`DispatcherServlet` at all.

Servlet 3.1 schema also fixes the `isELIgnored=true` default that caused `${message}`
to print literally in Case 1 JSPs — EL works in all JSPs without any extra directive.

#### root-context.xml

Declares `BookService` as an explicit `<bean>` — no `@Service` annotation, no component
scan here. Intentional: keeping the root WAC declaration style consistent with Cases 1
and 2, and avoiding accidental double-instantiation (if component scan were enabled here
AND in `dispatcher-servlet.xml` for the same package, two instances would be created).

#### dispatcher-servlet.xml — where the namespace tags live

**`<context:component-scan base-package="com.bookshelf.controller"/>`**
Scans only the controller package for `@Controller` classes — replacing explicit
`<bean>` declarations for controllers. Deliberately scoped to controller package only,
not the service package (BookService is already in root WAC).

**`<mvc:annotation-driven/>`**
The most important tag. Replaces what Cases 1 and 2 declared manually:

| Registered explicitly in Cases 1 & 2 | Registered automatically by `<mvc:annotation-driven/>` |
|---|---|
| `BeanNameUrlHandlerMapping` | `RequestMappingHandlerMapping` |
| `SimpleControllerHandlerAdapter` | `RequestMappingHandlerAdapter` |
| _(nothing)_ | Default `HttpMessageConverter`s |
| _(nothing)_ | Type conversion + formatting support |
| _(nothing)_ | JSR-303 validation integration |

Without this tag, `@GetMapping` / `@RequestMapping` annotations are silently ignored
and every request returns 404.

**`<mvc:view-controller path="/" view-name="home"/>`**
Maps `/` directly to `home.jsp` with zero Java code. Equivalent to writing a controller
whose entire body is `return "home";`. Useful for static content pages with no model data.

**`<mvc:resources mapping="/static/**" location="/static/"/>`**
Tells Spring to serve requests matching `/static/**` directly from the `webapp/static/`
folder, bypassing `RequestMappingHandlerMapping` entirely. Without this, a request for
`/static/style.css` would find no matching controller and return 404.

**`InternalResourceViewResolver`** — unchanged from Cases 1 and 2.

#### BookController.java — what annotations replaced

`@Controller` — replaces `implements Controller`. Marks the class for component scanning
and tells `RequestMappingHandlerMapping` to inspect it for URL mappings.

`@Autowired` on `BookService` field — replaces XML `<property ref="bookService"/>`.
Spring injects `BookService` transparently from the parent root WAC during child WAC
initialization. The controller never fetches it from any context manually.

`@GetMapping("/books")` returning `String` + `Model` parameter — replaces
`handleRequest()` returning `ModelAndView`. Spring assembles a `ModelAndView` internally
from the returned string and model attributes — `ModelAndView` was always there underneath.

`@GetMapping("/books/{id}")` with `@PathVariable` — new capability not possible in
pure XML style. `BeanNameUrlHandlerMapping` matched exact bean name strings only.
`RequestMappingHandlerMapping` (registered by `<mvc:annotation-driven/>`) handles URL
patterns with variable segments natively.

---

### Request Pipeline

```
Browser: GET /15-mvc-namespace-bookshelf/books/2
    │
    ▼
Tomcat → DispatcherServlet (mapped to "/")
    │
    ▼
RequestMappingHandlerMapping
    │  scans @Controller beans for @GetMapping("/books/{id}") match
    │  extracts id=2 from URL
    ▼
RequestMappingHandlerAdapter
    │  invokes BookController.bookDetail(id=2, Model)
    ▼
BookController.bookDetail()
    │  calls bookService.getBookById(2)  ← BookService from root WAC
    │  adds "book" to Model
    │  returns "book-detail"
    ▼
InternalResourceViewResolver
    │  resolves → /WEB-INF/views/book-detail.jsp
    ▼
Browser: rendered book detail page
```

---

### What's New vs Cases 1 & 2

| Concept | Cases 1 & 2 | Case 3 |
|---|---|---|
| Controller declaration | Explicit `<bean>` in XML | `@Controller` + component scan |
| URL mapping | `BeanNameUrlHandlerMapping` + bean `name` attr | `@GetMapping` / `@RequestMapping` |
| Controller invocation | `SimpleControllerHandlerAdapter` + `handleRequest()` | `RequestMappingHandlerAdapter` + any method |
| Dependency injection | XML `<property ref="..."/>` | `@Autowired` |
| Return type | `ModelAndView` (explicit) | `String` + `Model` (Spring wraps internally) |
| Path variables | Not possible | `@PathVariable` |
| Static files | Not needed (`*.do` pattern) | `<mvc:resources/>` required (`/` pattern) |
| Simple page mapping | Needed a controller class | `<mvc:view-controller/>` |
| Servlet spec | 2.3 DTD | 3.1 XSD — EL works by default in JSPs |
| JSTL | Scriptlets used in JSPs | `<c:forEach>`, `<c:choose>` etc. |

---

### Documentation Reference

All from Spring 4.3.x reference (more detailed than 5.x):
`https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/mvc.html`

| Section | Topic | Read when |
|---|---|---|
| § 22.2.1 | Special Bean Types in WebApplicationContext | Now — formal vocab for HandlerMapping, HandlerAdapter, ViewResolver |
| § 22.2.2 | Default DispatcherServlet Configuration | Now — explains `DispatcherServlet.properties` fallback defaults |
| § 22.3.1 | Defining a controller with `@Controller` | Now — formal reference for what you just used |
| § 22.3.2 | Mapping Requests with `@RequestMapping` | Now — `@GetMapping`, `@PathVariable`, method params |
| § 22.16.1 | Enabling MVC Java Config or MVC XML Namespace | Now — formal reference for `<mvc:annotation-driven/>` |
| § 22.4 | Handler Mappings | Later — all three HandlerMapping types side by side |
| § 22.5 | Resolving Views | Later — ViewResolver implementations in detail |
| § 22.16.3 | `mvc:interceptors` | When adding interceptors |
| § 22.16.9 | `mvc:resources` | Cross-check what you just used |
