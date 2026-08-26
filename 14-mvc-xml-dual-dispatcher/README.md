# Spring MVC — Pure XML Configuration

## Case 2: One Root WAC + Two Child WACs (Two DispatcherServlets)

### Motivation

Case 1 showed the bare minimum — a single `DispatcherServlet` with no root
`WebApplicationContext` (WAC), everything in one XML file. That works for trivial
apps but doesn't reflect real-world needs.

Case 2 demonstrates why the root WAC / child WAC hierarchy exists in the first place:
**sharing beans across multiple `DispatcherServlet`s**, and proving that the root WAC
is alive and accessible before any child WAC is initialized.

It also demonstrates a fundamental Spring Web concept: servlet container-managed
components (listeners, filters) live outside Spring's IoC container and cannot receive
injected dependencies — they must reach Spring beans manually via `ServletContext`.
Spring-managed beans (controllers, services) on the other hand receive dependencies
via injection and never need to fetch them from any context directly.

---

### Use Case — "DualApp": News Portal + Admin Panel

The app has two completely separate sections, each with its own `DispatcherServlet`:

| Section | URL pattern | DispatcherServlet | Child WAC config |
|---|---|---|---|
| Public news site | `/news/*` | `news-dispatcher` | `news-dispatcher-servlet.xml` |
| Admin panel | `/admin/*` | `admin-dispatcher` | `admin-dispatcher-servlet.xml` |

**Shared in root WAC:** `ArticleService` — a dummy service returning hardcoded article
data. Both `NewsController` and `AdminController` use it. Since it lives in the root WAC
as a singleton, both child WACs share the exact same instance.

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
DualApp/
├── src/main/java/
│   └── com/dualapp/
│       ├── listener/
│       │   └── StartupListener.java        ← custom ServletContextListener
│       ├── service/
│       │   └── ArticleService.java         ← shared root WAC bean
│       ├── news/
│       │   └── NewsController.java         ← news child WAC bean
│       └── admin/
│           └── AdminController.java        ← admin child WAC bean
└── src/main/webapp/
    ├── index.html
    └── WEB-INF/
        ├── lib/                            ← all Spring JARs + commons-logging
        ├── views/
        │   ├── news/
        │   │   └── articles.jsp
        │   └── admin/
        │       └── dashboard.jsp
        ├── web.xml                         ← servlet container config
        ├── root-context.xml                ← root WAC — shared beans
        ├── news-dispatcher-servlet.xml     ← news child WAC
        └── admin-dispatcher-servlet.xml    ← admin child WAC
```

---

### Architecture

```
Root WAC  (ContextLoaderListener → root-context.xml)
│   └── ArticleService  ← singleton, shared across entire app
│
├── News Child WAC  (news-dispatcher → news-dispatcher-servlet.xml)
│       ├── BeanNameUrlHandlerMapping
│       ├── SimpleControllerHandlerAdapter
│       ├── NewsController  ← has ArticleService injected from root WAC
│       └── InternalResourceViewResolver  → /WEB-INF/views/news/
│
└── Admin Child WAC  (admin-dispatcher → admin-dispatcher-servlet.xml)
        ├── BeanNameUrlHandlerMapping
        ├── SimpleControllerHandlerAdapter
        ├── AdminController  ← has ArticleService injected from root WAC
        └── InternalResourceViewResolver  → /WEB-INF/views/admin/
```

Sibling child WACs are invisible to each other. Both can see root WAC beans.

Root WAC cannot see either child WAC's beans.

---

### How the Config Works

#### web.xml

Declares components in startup order:

1. `contextConfigLocation` `<context-param>` — tells `ContextLoader` where to find
   root WAC config (`/WEB-INF/root-context.xml`)
2. `ContextLoaderListener` — fires first, builds root WAC, stores it on `ServletContext`
3. `StartupListener` (our own) — fires second, root WAC already alive, no child WAC yet
4. `news-dispatcher` servlet with `load-on-startup=1` — initializes first, builds news
   child WAC, finds root WAC on `ServletContext`, sets it as parent
5. `admin-dispatcher` servlet with `load-on-startup=2` — initializes second, same process

Declaration order of listeners in `web.xml` matters — they fire in that exact order.
`ContextLoaderListener` must be declared before any listener that needs root WAC beans.

#### root-context.xml

Declares only `ArticleService`. No MVC infrastructure here — root WAC has no concept
of controllers, view resolvers, or handler mappings. It is a plain Spring IoC container
holding shared business-layer beans.

#### "news-dispatcher-servlet.xml" and "admin-dispatcher-servlet.xml"

Each declares its own isolated MVC infrastructure:
- `BeanNameUrlHandlerMapping` — routes URLs to controllers by bean `name` attribute
- `SimpleControllerHandlerAdapter` — invokes `Controller`-interface-based controllers
- Its own controller bean — with `articleService` injected via `<property ref="articleService"/>`
- Its own `InternalResourceViewResolver` — pointing to its own views subfolder

When Spring wires `ref="articleService"` in a child WAC, it looks in the child first,
doesn't find it, then walks up to the parent root WAC and finds it there. This is
**transparent parent delegation** — no special code required.

---

### Two Ways Spring Beans Are Accessed — Side by Side

This project deliberately demonstrates both patterns:

#### Pattern 1 — Manual retrieval via WebApplicationContextUtils (StartupListener)

`StartupListener` is instantiated by **Tomcat**, not Spring. Spring cannot inject into
it. The only way it can reach Spring beans is by manually retrieving the WAC from
`ServletContext` and calling `getBean()`:

```java
WebApplicationContext rootWac = WebApplicationContextUtils
    .getWebApplicationContext(sce.getServletContext());
ArticleService svc = rootWac.getBean(ArticleService.class);
```

`WebApplicationContextUtils.getWebApplicationContext(servletContext)` always returns
the **root WAC** — it looks up the well-known attribute key
`WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE` on `ServletContext`.

To retrieve a specific **child WAC** by servlet name (rarely needed):
```java
String key = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "news-dispatcher";
WebApplicationContext newsChildWac =
    (WebApplicationContext) servletContext.getAttribute(key);
```

#### Pattern 2 — Dependency injection via XML `<property>` (Controllers)

Controllers are Spring beans — Spring instantiates them. So Spring can inject
dependencies into them at wiring time. `ArticleService` arrives via setter injection,
declared in XML:

```xml
<bean name="/articles" class="com.dualapp.news.NewsController">
    <property name="articleService" ref="articleServiceBean"/>
</bean>
```

The controller has a plain setter `setArticleService(ArticleService)`. Spring calls it
during child WAC initialization, resolving `articleServiceBean` transparently from the
root WAC. The controller never touches any `ApplicationContext` reference.

This is the correct pattern. Manual `getBean()` inside a Spring-managed bean is the
Service Locator anti-pattern and should be avoided in application code.

---

### Startup Console Output (Tomcat)

When the app deploys, Tomcat console shows this sequence:

```
=== StartupListener fired ===
Root WAC is alive. Child WACs do not exist yet.
ArticleService retrieved from root WAC directly.
Article count at startup: 3
Articles: [Spring MVC Explained, Understanding DispatcherServlet, Root vs Child WebApplicationContext]
=== StartupListener done. DispatcherServlets will init next. ===
[Spring MVC init logs for news-dispatcher...]
[Spring MVC init logs for admin-dispatcher...]
```

This proves the root WAC is fully alive and usable before either `DispatcherServlet`
has initialized its child WAC.

---

### Request Pipelines

#### `/news/articles`

```
Browser → Tomcat → news-dispatcher (DispatcherServlet)
    → BeanNameUrlHandlerMapping (finds bean named "/articles")
    → SimpleControllerHandlerAdapter (calls handleRequest())
    → NewsController (calls articleService.getAllArticles())
    → ModelAndView("articles") with articles list
    → InternalResourceViewResolver → /WEB-INF/views/news/articles.jsp
    → Browser: rendered article list
```

#### `/admin/dashboard`

```
Browser → Tomcat → admin-dispatcher (DispatcherServlet)
    → BeanNameUrlHandlerMapping (finds bean named "/dashboard")
    → SimpleControllerHandlerAdapter (calls handleRequest())
    → AdminController (calls articleService.getArticleCount())
    → ModelAndView("dashboard") with article count
    → InternalResourceViewResolver → /WEB-INF/views/admin/dashboard.jsp
    → Browser: rendered dashboard
```

Both pipelines use the **same `ArticleService` singleton instance** from root WAC.

---

### Key Concepts Demonstrated

**Root WAC as shared singleton scope:** `ArticleService` is instantiated once in the
root WAC. Both child WACs inject the same instance. Changes to its state would be
visible across both sections — demonstrating true application-wide sharing.

**Transparent parent delegation:** Declaring `ref="articleServiceBean"` in a child WAC XML
file works even though `articleServiceBean` is defined in `root-context.xml`. Spring's
parent-child context hierarchy resolves bean references across the boundary
automatically.

**Listener declaration order matters:** `ContextLoaderListener` must come before any
listener that needs root WAC beans. Listeners fire strictly in `web.xml` declaration
order. `load-on-startup` ordering (1 then 2) similarly controls which `DispatcherServlet`
initializes first.

**Sibling child WAC isolation:** `NewsController` cannot see `AdminController` and vice
versa. Neither child WAC can see the other's beans — only the root WAC's beans are
shared. This is the design intent: each `DispatcherServlet` has its own encapsulated
MVC configuration.

**Servlet container vs Spring container boundary:** Listeners and filters are
container-managed — Spring cannot inject into them. Controllers and services are
Spring-managed — Spring injects into them freely. The boundary between these two worlds
is `ServletContext`, which holds WAC references as attributes and acts as the bridge.
