# Notes about Spring MVC

### Table of Contents

Jump links:

- [1. Servlet Versions & Programmatic Registration (Servlet 2.3 vs 3.0+)](#servlet-versions-programmatic-servlet-reg)
- [2. AbstractDispatcherServletInitializer & "load-on-startup" for a Servlet](#abstract-dispatcher-servlet-initializer)
- [3. Servlet Listeners + ContextLoader / ContextLoaderListener / AbstractContextLoaderInitializer](#servlet-listeners-and-contextloader)
- [4. WebApplicationContext (WAC) — What It Is and Why It Exists](#webapplicationcontext-what-and-why)
- [5. Context Hierarchy, Root vs Child Contexts, Multiple DispatcherServlets & Web Bean Scopes](#webctx-hierarchy-web-bean-scopes)
- [6. ContextLoader — All Cases of Root WebApplicationContext (WAC) Creation](#ctxloader-cases-root-wac-creation)
- [7. Why Non-MVC Beans Belong in Root WebApplicationContext (WAC)](#why-nonmvc-beans-in-root-wac)


<br>

---

<br>


<a id="servlet-versions-programmatic-servlet-reg"></a>

## 1. Servlet Versions & Programmatic Registration (Servlet 2.3 vs 3.0+)

### What a "Servlet version" actually means

The Servlet API is a spec (interfaces like `Servlet`, `ServletContext`, `ServletConfig`,
`HttpServletRequest`) defined by Sun/Oracle, now Jakarta EE. Tomcat/Jetty/etc. implement
this spec. The version number = which capabilities of the spec your container supports.

- **Servlet 2.3** (~2001): purely `web.xml`-driven. No annotations, no programmatic
  registration. `web.xml` is the *only* way to declare servlets/filters/listeners.
- **Servlet 3.0** (2009): major turning point. Introduced:
  - `@WebServlet`, `@WebFilter`, `@WebListener` annotations (skip `web.xml` entirely)
  - `ServletContext.addServlet(...)` — programmatic servlet registration at startup
  - `ServletContainerInitializer` — a hook so a library (e.g. Spring) can run startup
    code automatically without touching `web.xml`
  - Async request processing

### My project is Servlet 2.3 — what that restricts me to

Since my Dynamic Web Project is set to Servlet 2.3 (chosen at project creation in Eclipse,
reflected in `web.xml`'s DTD/schema declaration):
- No `@WebServlet` annotations
- No `ServletContext.addServlet(...)`
- No `WebApplicationInitializer` (needs Servlet 3.0's `ServletContainerInitializer`
  under the hood — doesn't exist in 2.3)

Everything (DispatcherServlet declaration, URL mapping, context params) must go in
`web.xml`. This is intentional for learning: nothing happens "by magic" via annotation
scanning: it's all explicit.

To move to Java-config style later: change the project's target runtime/servlet version
in Eclipse project facets, and update/remove `web.xml` accordingly.

### WebApplicationInitializer — what it is, and why I can't use it yet

Spring interface. When implemented, Spring's own `SpringServletContainerInitializer`
(inside `spring-web.jar`) is auto-detected at container startup and calls its
`onStartup(ServletContext container)` method — with **zero `web.xml`**.

Inside `onStartup`, I get the raw `ServletContext` and can register servlets, filters,
listeners, context params — anything `web.xml` could do, but in Java.

Can be mixed with a partial `web.xml` (Spring docs: "as an alternative or in combination
with a web.xml file"), but in practice most projects pick one style fully.

**Why unusable in my current project:** `SpringServletContainerInitializer` itself relies
on the Servlet 3.0+ `ServletContainerInitializer` mechanism to get auto-detected. Servlet
2.3 has no such mechanism. So this is a "know it exists, can't use it yet" concept for now.

### "ServletContext.addServlet(...)" — registering a servlet without XML or "@WebServlet"

This is a real, standard **Servlet 3.0+ API method** — not something Spring invented.
Spring's `WebApplicationInitializer.onStartup(ServletContext container)` just hands me
the same `ServletContext` so I can call it myself.

Prior understanding: `ServletContext` = one shared object per web app, readable via
`<context-param>` in `web.xml` or programmatically (attributes, resource paths, etc.) —
but in Servlet 2.3 it's *read-only* in the sense that servlets themselves are always
declared via `web.xml`, never built by hand and registered at runtime.

Servlet 3.0 added *write* capability to `ServletContext`:
```java
ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet)
ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass)
```

Example (what Spring docs show):
```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        ServletRegistration.Dynamic registration =
                container.addServlet("example", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/example/*");
    }
}
```

This hands the container an actual `Servlet` instance (`new DispatcherServlet()`),
registers it, and the returned `ServletRegistration.Dynamic` handle lets me configure it
further:
- `.setLoadOnStartup(1)` ↔ XML equivalent: `<load-on-startup>1</load-on-startup>`
- `.addMapping("/example/*")` ↔ XML equivalent: `<servlet-mapping><url-pattern>`

**Key takeaway:** this capability simply doesn't exist pre-Servlet-3.0, which is why I'd
never encountered it despite knowing basic Servlet/JSP concepts already.

<br>

---

<br>


<a id="abstract-dispatcher-servlet-initializer"></a>

## 2. AbstractDispatcherServletInitializer & "load-on-startup" for a Servlet

### The scenario

`WebApplicationInitializer` is a bare interface — implementing it directly means I write
all the DispatcherServlet setup plumbing myself (create the servlet, register it, set
load-on-startup, set URL mapping, wire the ApplicationContext). Since nearly every Spring
MVC app repeats this exact plumbing, Spring provides ready-made abstract classes that do
it for me.

### The class hierarchy

```txt
WebApplicationInitializer                          (interface — the contract)
  └── AbstractDispatcherServletInitializer          (abstract class — handles Servlet
      │                                               registration, mapping, DispatcherServlet
      │                                               creation)
      └── AbstractAnnotationConfigDispatcherServletInitializer  (abstract class — additionally
                                                        handles turning your @Configuration
                                                        classes into ApplicationContexts)
```

### AbstractDispatcherServletInitializer — what it saves me from writing

By extending it (or its annotation-based subclass) and overriding a few slot methods, it
automatically handles:
- Creating the `DispatcherServlet` instance
- Registering it on the `ServletContext`
- Setting `load-on-startup` (defaults to `1` internally)
- Applying the URL mapping I specify
- Building the root `ApplicationContext` + the servlet's own `WebApplicationContext`, and
  wiring the parent-child relationship between them (full "why this hierarchy matters" —
  covered in a later notes section)

### Example (annotation-config subclass, from Spring docs)
```java
public class GolfingWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { GolfingAppConfig.class };   // would be root-context.xml
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { GolfingWebConfig.class };   // would be golfing-servlet.xml
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/golfing/*" };
    }
}
```

`getRootConfigClasses()` / `getServletConfigClasses()` exist only on the annotation-config
subclass — they just want a list of `@Configuration` classes.

The middle class (`AbstractDispatcherServletInitializer`) has lower-level abstract methods
instead (e.g. `createServletApplicationContext()`), since it doesn't assume XML or annotations.

### Caveat for my current project (Servlet 2.3)

Both `WebApplicationInitializer` and `AbstractDispatcherServletInitializer` live in
`spring-web.jar` and are only ever auto-detected via the Servlet 3.0+
`ServletContainerInitializer` mechanism. On Servlet 2.3, none of this is usable — I'll do
the XML equivalent of everything these classes do, split across `web.xml` +
`[servlet-name]-servlet.xml`. Treat as conceptual knowledge for now.

---

### \<load-on-startup\> — the scenario

By default, a servlet is **lazily initialized**: the container doesn't construct it or
call `init()` until the first matching HTTP request arrives.

```xml
<servlet>
    <servlet-name>golfing</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```

`<load-on-startup>` overrides this: the servlet is constructed and `init()` is called
immediately when the web app starts, not on first request.

### What the number means

It's not a boolean — it's a **priority/ordering value** used when multiple servlets all
specify `load-on-startup`. Lower number = initialized first (e.g. `1` finishes before `2`
starts). Omitting the element (or using a negative number) reverts to lazy, on-first-request
loading with no ordering guarantee.

### Why this matters specifically for DispatcherServlet

`DispatcherServlet.init()` is heavy — it builds the whole `WebApplicationContext`: reads
Spring config, instantiates all beans, wires dependencies, sets up `HandlerMapping`s and
`ViewResolver`s. Leaving it lazy means:
- The first real user request pays the cost of all that setup (slow first response)
- Config errors surface only when someone actually hits the app, not at server startup

Setting `load-on-startup` surfaces cost and errors immediately at server boot — near-universal
practice for `DispatcherServlet`.


<br>

---

<br>


<a id="servlet-listeners-and-contextloader"></a>

## 3. Servlet Listeners + ContextLoader / ContextLoaderListener / AbstractContextLoaderInitializer

### Servlet Listeners — what they are

Servlet listeners are a standard Servlet API mechanism to **react to lifecycle events**
happening inside the container — app startup/shutdown, session creation/destruction,
attribute changes on `ServletContext` or session. They are not request-handling things;
they are **lifecycle-hooking** things.

Declared in `web.xml` (no URL mapping needed — the container just instantiates them):
```xml
<listener>
    <listener-class>com.example.MyListener</listener-class>
</listener>
```

### Key listener interfaces (javax.servlet)

| Interface | What it listens to |
|---|---|
| `ServletContextListener` | Web app startup (`contextInitialized`) + shutdown (`contextDestroyed`) |
| `ServletContextAttributeListener` | Attributes added/removed/replaced on `ServletContext` |
| `HttpSessionListener` | Session creation and destruction |
| `HttpSessionAttributeListener` | Attributes added/removed/replaced on a session |
| `ServletRequestListener` | Each request initialized and destroyed |

For Spring MVC: **`ServletContextListener` is the one that matters** — specifically
`contextInitialized(ServletContextEvent)`, which fires after the app deploys and before
any servlet handles a request. It's the earliest possible hook into a running web app —
perfect for bootstrapping a Spring `ApplicationContext`.

---

### ContextLoader — what it is

A plain Spring class (in `spring-web.jar`). Its sole job:
- Read Spring configuration (from `contextConfigLocation` `<context-param>` in `web.xml`)
- Build a root `WebApplicationContext` (default impl: `XmlWebApplicationContext`)
- Store the finished context under a well-known attribute key on `ServletContext` so
  anything in the web app can find it later
- Also handles destroying the context cleanly at shutdown

It is **not** a listener itself — it's a helper that knows *how* to build the context.
Something else needs to call it at the right moment.

The `contextConfigLocation` param it reads:
```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/root-context.xml</param-value>
</context-param>
```

---

### ContextLoaderListener — the glue

`ContextLoaderListener` is what calls `ContextLoader` at the right moment. It:
- Implements `ServletContextListener` (container calls it at startup/shutdown)
- Extends `ContextLoader` (inherits the context-building logic)

At startup (`contextInitialized`): builds and stores the root `WebApplicationContext`.
At shutdown (`contextDestroyed`): closes and cleans it up.

Declared in `web.xml`:
```xml
<listener>
    <listener-class>
        org.springframework.web.context.ContextLoaderListener
    </listener-class>
</listener>
```

**Sequence in a running app:**
1. Container starts, sees `<listener>` → calls `contextInitialized`
2. `ContextLoaderListener` builds root `WebApplicationContext`, stores it on `ServletContext`
3. `DispatcherServlet` later initializes, finds the already-built root context, uses it as
   the **parent** for its own `WebApplicationContext`
   (parent-child relationship fully explained in the next notes section)

---

### AbstractContextLoaderInitializer — the Servlet 3.0+ equivalent

The "no `web.xml`" way to register `ContextLoaderListener`. Implements
`WebApplicationInitializer` (auto-detected at Servlet 3.0+ startup), and its `onStartup(...)`
programmatically registers a `ContextLoaderListener` on the `ServletContext` — exactly
what the `<listener>` block in `web.xml` does.

---

### Full class hierarchy (putting it all together)
```
WebApplicationInitializer                                    (interface)
  └── AbstractContextLoaderInitializer                       (registers ContextLoaderListener
      │                                                       — config-style agnostic)
      └── AbstractDispatcherServletInitializer               (additionally registers
          │                                                   DispatcherServlet)
          └── AbstractAnnotationConfigDispatcherServletInitializer  (for @Configuration style)
```

Key insight from this hierarchy: `AbstractDispatcherServletInitializer` *extends*
`AbstractContextLoaderInitializer` — meaning using it gives me **both** root context setup
(via `ContextLoaderListener`) **and** `DispatcherServlet` setup in one shot. This reflects
that the root context must exist **before** `DispatcherServlet` sets up its own child context.

---

### Is knowing this beneficial? (for Servlet 2.3 + XML setup)

Yes — practically. In `web.xml` I will write both:
- A `<listener>` block for `ContextLoaderListener` (bootstraps root Spring context)
- A `<servlet>` block for `DispatcherServlet` (sets up its own child context)

Without understanding `ContextLoaderListener`, that `<listener>` declaration looks like
mysterious boilerplate. It's there for a clear reason: it creates the root context before
any servlet wakes up.

`AbstractContextLoaderInitializer` is less immediately practical (unusable on Servlet 2.3)
but understanding it shows how the no-XML path mirrors the XML path exactly.


<br>

---

<br>


<a id="webapplicationcontext-what-and-why"></a>

## 4. WebApplicationContext (WAC) — What It Is and Why It Exists

### Starting anchor — plain ApplicationContext (already known)

`ApplicationContext` is Spring's IoC container. Feed it config (XML or `@Configuration`),
it instantiates beans, wires dependencies, hands them out. It has **zero awareness** of
HTTP, servlet containers, requests, or sessions. Works in desktop apps, CLI tools, batch
jobs — anything.

```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
MyBean b = ctx.getBean(MyBean.class);
// No Tomcat needed. No web.xml. No HTTP. Pure Java.
```

---

### What WebApplicationContext is

An **interface that extends `ApplicationContext`**. Still a full IoC container — does
everything a plain `ApplicationContext` does — but adds one critical thing:

**Awareness of the `ServletContext` it lives inside.**

The one meaningful method it adds over `ApplicationContext`:
```java
ServletContext getServletContext();
```

This reference to `ServletContext` enables:
- Beans inside it can be aware they're running in a web environment
- It can be stored on `ServletContext` under a well-known key so anything in the app
  can look it up
- Web-specific bean scopes (`request`, `session`, `application`) — these make no sense
  outside a servlet container and are implemented inside `WebApplicationContext`
  infrastructure

---

### Why not just use a plain ApplicationContext in a web app?

Technically possible — create a `ClassPathXmlApplicationContext` in a
`ServletContextListener` and store it manually as a `ServletContext` attribute. Beans
would work fine. But you'd lose:

- **Web-specific bean scopes** (`request`, `session`) — implemented inside
  `WebApplicationContext` infrastructure, not available in plain `ApplicationContext`
- **Standard well-known lookup key convention** — Spring's own classes (`DispatcherServlet`,
  `FrameworkServlet`, utility methods) know exactly where to find a `WebApplicationContext`
  on the `ServletContext`. A manually stored plain context has no such convention
- **Tight integration with `DispatcherServlet`** — which explicitly expects a
  `WebApplicationContext`, not just any `ApplicationContext`

Plain `ApplicationContext` isn't *broken* in a web setting — `WebApplicationContext` is
simply the contract Spring MVC's own plumbing speaks.

---

### Is WebApplicationContext required if only spring-web is used (no spring-webmvc)?

`WebApplicationContext` is defined in `spring-web.jar` — available without `spring-webmvc`.
`ContextLoaderListener` (also in `spring-web.jar`) creates one.

So yes — even without `DispatcherServlet`, I could bootstrap a `WebApplicationContext` via
`ContextLoaderListener` to hold shared beans, and use a *different* web framework
(e.g. Struts, JSF) for request handling. This was a real pattern in the early 2000s.

Mental model:
- `spring-web` + `WebApplicationContext` = IoC container that knows about the servlet
  environment
- Adding `spring-webmvc` = adds `DispatcherServlet` which *consumes* that context and
  layers the full MVC request-handling pipeline on top

---

### Concrete implementations (XML vs annotation-specific children)

Just like plain `ApplicationContext` has XML and annotation-specific implementations,
so does `WebApplicationContext`:

| Implementation | Style | Notes |
|---|---|---|
| `XmlWebApplicationContext` | XML config | Default — used automatically by `ContextLoaderListener` and `DispatcherServlet` unless told otherwise |
| `AnnotationConfigWebApplicationContext` | `@Configuration` classes | Used for annotation-based config in web setting |
| `GroovyWebApplicationContext` | Groovy bean definitions | Rarely used |

I never write `new XmlWebApplicationContext()` myself — `ContextLoader` does it
automatically when `ContextLoaderListener` fires. To switch to annotation-based config,
tell `ContextLoader` explicitly via a `<context-param>`:

```xml
<context-param>
    <param-name>contextClass</param-name>
    <param-value>
        org.springframework.web.context.support.AnnotationConfigWebApplicationContext
    </param-value>
</context-param>
```

---

### One WebApplicationContext per DispatcherServlet — the mental model (preview)

(Fully expanded in the next notes section — root context + parent-child hierarchy)

- `ContextLoaderListener` creates **one root `WebApplicationContext`** for the whole
  web app — starts before any servlet, holds shared beans (services, repositories,
  data sources)
- Each `DispatcherServlet` creates its **own separate `WebApplicationContext`** during
  `init()` — holds MVC-specific beans (controllers, view resolvers, handler mappings)
- The servlet's context is a **child** of the root context — can see and use root beans,
  but root context cannot see the servlet's beans

`WebApplicationContext` is **not** one global singleton for the whole app — it's one per
`DispatcherServlet` plus the root one, forming a parent-child tree.


<br>

---

<br>


<a id="webctx-hierarchy-web-bean-scopes"></a>

## 5. Context Hierarchy, Root vs Child Contexts, Multiple DispatcherServlets & Web Bean Scopes

### What "ApplicationContext instances can be scoped" actually means

The docs phrasing is confusing — it does NOT mean "scoped" as in bean scopes. It means
`ApplicationContext` instances can exist in a **parent-child hierarchy**, where each
context has a defined boundary of what beans it owns and what it can see.

Analogy: like variable scope in Java — an inner block sees outer variables, but the outer
block cannot see inner variables.

Applies to `BeanFactory` too (it has `setParentBeanFactory()`), but in practice it's
always `ApplicationContext` (specifically `WebApplicationContext`) that matters in
Spring MVC. Treat `BeanFactory` hierarchy as a technicality.

---

### Context Hierarchy — the general visibility rules

When a Spring `ApplicationContext` has a parent:
- **Child can see all beans defined in parent** ✓
- **Parent cannot see beans defined in any child** ✗
- **Same-named bean in both**: child's definition wins *within that child* (shadows parent's
  bean for that child only — parent's own bean is unaffected)

Setting a parent programmatically (Spring MVC does this automatically — I'd never write
this manually in a web app):
```java
ApplicationContext parent = new ClassPathXmlApplicationContext("parent.xml");
ApplicationContext child = new ClassPathXmlApplicationContext(
    new String[]{"child.xml"}, parent  // second arg = parent
);
```

In Spring MVC: `DispatcherServlet` looks up the root `WebApplicationContext` from
`ServletContext` and sets it as its own context's parent automatically during `init()`.

---

### Root WebApplicationContext — what it is and what it holds

Created by `ContextLoaderListener` **before any servlet starts** — it is the **parent**.

Holds beans that are:
- Shared across the whole application
- Not MVC-specific
- `@Service` classes, `@Repository` classes, data sources, transaction managers, security config

Configured by `root-context.xml` (conventional name — not a Spring term). If
`contextConfigLocation` is not specified, Spring looks for `/WEB-INF/applicationContext.xml`
by default.

### Servlet WebApplicationContext — what it is and what it holds

Created by `DispatcherServlet` during its own `init()` — it is the **child**.

Holds beans that are:
- Specific to that servlet's MVC machinery
- `@Controller` classes, `ViewResolver`, `HandlerMapping`, `MultipartResolver`, etc.

Configured by `/WEB-INF/[servlet-name]-servlet.xml` — Spring looks for this file
automatically based on the servlet name declared in `web.xml`.

---

### Typical web.xml layout (both contexts wired together)

```xml
<!-- 1. Bootstraps ROOT context (parent) -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/root-context.xml</param-value>
</context-param>

<listener>
    <listener-class>
        org.springframework.web.context.ContextLoaderListener
    </listener-class>
</listener>

<!-- 2. Bootstraps CHILD context (per-servlet) -->
<servlet>
    <servlet-name>golfing</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
    <!-- Spring auto-looks for /WEB-INF/golfing-servlet.xml -->
</servlet>

<servlet-mapping>
    <servlet-name>golfing</servlet-name>
    <url-pattern>/golfing/*</url-pattern>
</servlet-mapping>
```

### Full startup sequence

1. Container starts → fires `contextInitialized` on `ContextLoaderListener`
2. Root `WebApplicationContext` built from `root-context.xml`, stored on `ServletContext`
3. Container initializes `DispatcherServlet` (because `load-on-startup=1`)
4. `DispatcherServlet.init()` builds its own child `WebApplicationContext` from
   `golfing-servlet.xml`
5. It finds the root context already sitting on `ServletContext`, sets it as its parent
6. App is ready to handle requests

---

### Can there be more than one DispatcherServlet?

Yes. Each gets its own child `WebApplicationContext`, all sharing the same root parent:

```
Root WebApplicationContext  (ContextLoaderListener — shared services/repos)
  ├── Child WebApplicationContext A  (DispatcherServlet "app"  → /app/*)
  └── Child WebApplicationContext B  (DispatcherServlet "api"  → /api/*)
```

Real reasons to do this:
- **Two distinct URL namespaces with different MVC config** — e.g. browser-facing HTML UI
  at `/app/*` needs different `ViewResolver`s and interceptors than a REST API at `/api/*`.
  Two servlets = two clean focused configs instead of one increasingly complex one.
- **Legacy migration** — incrementally migrating part of an app to Spring MVC while
  another part was already there. Two servlets coexist during transition.

**In practice today:** rare. Modern Spring MVC handles "different behavior for different
URLs" through a single `DispatcherServlet` with structured controllers and interceptors.

**Why this matters to understand:** the root/child split exists *because* multiple servlets
sharing common beans is a legitimate real-world need. If there were always exactly one
`DispatcherServlet`, Spring could have used one flat context. The hierarchy is not
accidental — it's the design solution to shared-vs-specialized beans.

---

### Web-specific bean scopes (only available in WebApplicationContext)

Beyond the standard `singleton` and `prototype`:

**`request` scope**
- New bean instance per incoming HTTP request, destroyed when request completes
- No two concurrent requests share the same instance
- Use case: form backing objects, per-request state
```xml
<bean id="loginForm" class="com.example.LoginFormBean" scope="request"/>
```

**`session` scope**
- One bean instance per `HttpSession`
- Created when session starts, destroyed when session expires/is invalidated
- Use case: logged-in user preferences, shopping cart — survives multiple requests
  from same user, not shared with other users
```xml
<bean id="userPreferences" class="com.example.UserPreferences" scope="session"/>
```

**`application` scope**
- One bean instance per `ServletContext` (per web application)
- Similar to `singleton` but tied to `ServletContext` lifecycle rather than
  `ApplicationContext` lifecycle
- Rare in practice — `singleton` covers most of the same needs

### Important caveat on request/session scopes

`request` and `session` scoped beans **cannot be directly injected into `singleton` beans**
in the normal way — a singleton is created once at startup, but a request/session scoped
bean doesn't exist yet at that point.

Spring solves this with **scoped proxies** — a detail for when actually writing controllers
and form beans, but worth knowing the limitation exists now.


<br>

---

<br>


<a id="ctxloader-cases-root-wac-creation"></a>

## 6. ContextLoader — All Cases of Root WebApplicationContext (WAC) Creation

### What happens if you don't declare ContextLoaderListener?

> [!INFO]
> `spring-web` will create a "root" WebApplicationContext (WAC), if you 
> setup a ContextLoaderListener (which calls Spring's ContextLoader - that does 
> the job of creating a root WAC & setting it as an attribute on the web app's 
> ServletContext).
> Root WAC is created if the above listener is registered AND if the Beans XML
> file for the WAC is present, i.e., if you either have the Spring Context
> beans XML file for your root WAC:
> - At either the default path (i.e. "/WEB-INF/applicationContext.xml"),
> - Or you declare a custom file path for it using a `<context-param>` named
>   "contextConfigLocation" providing the path as its value (like in the
>   example below as "/WEB-INF/root-context.xml"), & ensure that this XML file
>   is present at this custom path you mentioned in "contextConfigLocation" value.
> ```xml
> <listener>
>   <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
> </listener>
> <context-param>
>   <param-name>contextConfigLocation</param-name>
>   <param-value>/WEB-INF/root-context.xml</param-value>
>   <!-- And ensure, this "root-context.xml" file is present at the above custom path;
>        Or else don't declare a custom "contextConfigLocation" value by omitting this
>        this <context-param>, and have the root WAC XML file at the default path
>        of "/WEB-INF/applicationContext.xml".
>   -->
> </context-param>
> ```


No `ContextLoaderListener` = no root `WebApplicationContext` is created. Period.

`ContextLoaderListener` is the only thing responsible for creating the root WAC.
Spring doesn't create it automatically just because `DispatcherServlet`(s) exists.

If you don't declare the listener, nothing reads `root-context.xml` (or
`applicationContext.xml`), nothing builds a root context, and nothing stores
one on ServletContext.

When DispatcherServlet then initializes and looks for a parent context on
ServletContext, it finds nothing — and that's fine, it just proceeds with no
parent. Its own child WAC becomes the only context in the app.

---

### The two independent decisions in web.xml
1. Whether to declare `ContextLoaderListener` at all
2. Whether to declare `contextConfigLocation` as a `<context-param>`

These are independent — each has its own effect.

---

### What happens across all combinations

| `ContextLoaderListener` present? | `contextConfigLocation` declared? | Result |
|---|---|---|
| No | No | No root WAC. DS's (DispatcherServlet/s) own (i.e. the child) WAC(s) is the only context. Valid for simple apps. |
| No | Yes | No root WAC. `<context-param>` is silently ignored — nothing reads it. |
| Yes | No | Spring looks for `/WEB-INF/applicationContext.xml` (hardcoded default in `ContextLoader`). Found → root WAC built. Not found → startup failure (`FileNotFoundException`). |
| Yes | Yes | Spring looks for the path(s) specified. Found → root WAC built. Not found → startup failure. |

Common beginner mistake: declaring `ContextLoaderListener` without providing either
`contextConfigLocation` or the default `/WEB-INF/applicationContext.xml` file →
app fails to deploy with a confusing error.

---

### contextConfigLocation can take multiple paths

```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
        /WEB-INF/root-context.xml
        /WEB-INF/security-context.xml
    </param-value>
</context-param>
```
All listed files are merged into one root WAC.

---

### Can there be only one WAC — no root, just one child?

Yes — completely valid for simple apps with one `DispatcherServlet`. Skip
`ContextLoaderListener` entirely. All beans (services, repos, controllers,
view resolvers) go into `[servlet-name]-servlet.xml`.

The root/child split becomes necessary only when:
- Multiple `DispatcherServlet`s need to share common beans
- Non-servlet components (custom `ServletContextListener`s, background threads)
  need to access Spring beans — they can only reach the root WAC, not a child WAC

---

### The 1 root + 2 child scenario

```
Root WAC  (ContextLoaderListener → root-context.xml)
  ├── Child WAC A  (DispatcherServlet "app" → app-servlet.xml)
  └── Child WAC B  (DispatcherServlet "api" → api-servlet.xml)
```

- Child A and Child B are completely independent — neither can see the other's beans
- Both can see root WAC beans
- Each child independently looks for `/WEB-INF/[servlet-name]-servlet.xml`


<br>

---

<br>


<a id="why-nonmvc-beans-in-root-wac"></a>

## 7. Why Non-MVC Beans Belong in Root WebApplicationContext (WAC)

### "Non-MVC beans" — what the term means

Some Spring beans are MVC machinery: `@Controller`, `ViewResolver`, `HandlerMapping`.
These belong in the child WAC (`[servlet-name]-servlet.xml`).

Other beans have nothing to do with HTTP or MVC — they're pure business logic,
example:

```java
@Service
public class UserService { ... }      // business logic

@Repository
public class UserRepository { ... }   // database access
```

These are "non-MVC beans." They belong in the root WAC (`root-context.xml`) because
they're shared infrastructure, not servlet-specific.

---

### "Non-servlet components" — what the term means

Anything that runs **outside** of `DispatcherServlet`'s request-handling cycle:
- Custom `ServletContextListener`s (my own startup/shutdown logic)
- Scheduled background tasks
- Background threads

Example — my own startup listener:

```java
public class MyStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Fires at app startup — BEFORE any DispatcherServlet initializes
        // Child WAC does not exist yet at this point
    }
}
```

Declared in web.xml:
```xml
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!-- All custom listeners like MyStartupListener (below) are declared after
  ContextLoaderListener is declared, because the Servlet Container initializes
  Listeners in declaration order in web.xml, and we want ContextLoaderListener
  to fire first, so that it finishes the job of initializing the root WAC.
-->
<listener>
    <listener-class>com.example.MyStartupListener</listener-class>
</listener>
```

### Why it can only reach the root WAC

Startup sequence:
1. `ContextLoaderListener.contextInitialized` → root WAC built, stored on `ServletContext`
2. `MyStartupListener.contextInitialized` → fires next (declared after CLL in web.xml)
3. `DispatcherServlet.init()` → child WAC built ← **only happens here**

At step 2, child WAC doesn't exist yet. Root WAC does. So `MyStartupListener` can
only safely access beans from the root WAC:

```java
@Override
public void contextInitialized(ServletContextEvent sce) {
    WebApplicationContext rootWac = WebApplicationContextUtils
        .getWebApplicationContext(sce.getServletContext());

    UserService userService = rootWac.getBean(UserService.class);
    userService.doSomeStartupTask();  // works — UserService is in root WAC
}
```

If `UserService` were in the child WAC instead → `rootWac.getBean(UserService.class)`
would throw `NoSuchBeanDefinitionException` at startup — child WAC hasn't been built yet.

### Simple summary

Any code that runs outside of an HTTP request being handled by `DispatcherServlet` can
only safely reach the root WAC. The child WAC is born and lives inside
`DispatcherServlet`'s lifecycle — nothing outside that lifecycle can reliably access it.


