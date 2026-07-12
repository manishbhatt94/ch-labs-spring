# Spring Framework Setup

We will be using Spring Framework Version 5.3.39 (which was the last version on
the v5.x of the Spring Framework release history).

Reference documentation for Spring Framework 5.3.39 by accessing the archives
at [docs.spring.io/spring-framework/docs/5.3.39/](https://docs.spring.io/spring-framework/docs/5.3.39/)

Here is the link that we'll use for the reference documentation:
[docs.spring.io/spring-framework/docs/5.3.39/reference/html/](https://docs.spring.io/spring-framework/docs/5.3.39/reference/html/)

## JARs required for the basic setup for the minimal Spring Framework

We will be using the below 5 *Spring Modules* whose JARs and download link are
mentioned below. It seems, these spring modules' version numbers are kept in
sync with the Spring Framework version number.

So, we will use the **5.3.39** version JARs for these 5 Spring Modules, which
as we stated above, is the version number of Spring Framework that we'll be
using.

Note the below 5 Spring Modules, we will be needing for the absolute basic
minimals project using Spring Framework:

1. **spring-beans** (version **5.3.39**)
   - **mvnrepository.com Link:** [org.springframework/spring-beans/5.3.39](https://mvnrepository.com/artifact/org.springframework/spring-beans/5.3.39)
   - **Artifact description on mvnrepository.com:** Spring Beans provides the
   configuration framework and basic functionality to instantiate, configure,
   and assemble java objects.
1. **spring-context** (version **5.3.39**)
   - **mvnrepository.com Link:** [org.springframework/spring-context/5.3.39](https://mvnrepository.com/artifact/org.springframework/spring-context/5.3.39)
   - **Artifact description on mvnrepository.com:** Spring Context provides
   access to configured objects like a registry (a context). It inherits its
   features from Spring Beans and adds support for internationalization, event
   propagation, resource loading, and the transparent creation of contexts.
1. **spring-core** (version **5.3.39**)
   - **mvnrepository.com Link:** [org.springframework/spring-core/5.3.39](https://mvnrepository.com/artifact/org.springframework/spring-core/5.3.39)
   - **Artifact description on mvnrepository.com:** Basic building block for
   Spring that in conjunction with Spring Beans provides dependency injection
   and IoC features.
1. **spring-expression** (version **5.3.39**)
   - **mvnrepository.com Link:** [org.springframework/spring-expression/5.3.39](https://mvnrepository.com/artifact/org.springframework/spring-expression/5.3.39)
   - **Artifact description on mvnrepository.com:** Powerful Expression
   Language for querying and manipulating an object graph at runtime. It is an
   extension of the unified expression language (unified EL) as specified in
   the JSP 2.1 specification.
1. **spring-jcl** (version **5.3.39**)
   - **mvnrepository.com Link:** [org.springframework/spring-jcl/5.3.39](https://mvnrepository.com/artifact/org.springframework/spring-jcl/5.3.39)
   - **Artifact description on mvnrepository.com:** Spring Commons Logging
   Bridge.

**Note:** For all these five (5) modules, make sure to download the 3 types of
JAR files, viz.:
- The binary JAR, e.g. `spring-beans-5.3.39.jar`.
- The javadoc JAR, e.g. `spring-beans-5.3.39-javadoc.jar`.
- The sources JAR, e.g. `spring-beans-5.3.39-sources.jar`.

After downloading all, link the binary JARs to your Eclipse Project's Build
Path, by creating a new User Library with a name of your choice, e.g.
`spring-basic-libs-5.3.39`, then adding all the five (5) binary JARs to this
new User Library.

Then, as discussed elsewhere, for each of these five (5) binary JARs, link
their respective javadoc and sources JAR files to them.
