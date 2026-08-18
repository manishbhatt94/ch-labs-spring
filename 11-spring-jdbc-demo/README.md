# Spring-JDBC Demo


---

## 0. Notes

- Example project code - sample runs - output\
  [sample-runs-outputs.md](./sample-runs-outputs.md)


---


## 1. Eclipse project setup


### JARs Required

Listed as GAV (GroupId, ArtifactId, Version) coordinates `Group:Artifact:Version`:

- **For Spring Core (5 JARs):** \
  (Placed under custom User Library titled: "spring-basics-libs-5.3.39")
  1. `org.springframework:spring-beans:5.3.39`
  1. `org.springframework:spring-context:5.3.39`
  1. `org.springframework:spring-core:5.3.39`
  1. `org.springframework:spring-expression:5.3.39`
  1. `org.springframework:spring-jcl:5.3.39`
- **For JSR-250 Annotations @PostConstruct, @PreDestroy:** \
  (Placed under custom User Library titled: "spring-jsr250-annotations-5.3.39")
  1. `javax.annotation:javax.annotation-api:1.3.2`
  1. `org.springframework:spring-aop:5.3.39`
- **For Spring JDBC:** \
  ("spring-tx.jar" holds the DataAccessException class)
  1. Spring JDBC » 5.3.39 (`org.springframework:spring-jdbc:5.3.39`)\
     > Spring JDBC provides an abstraction layer that simplifies code to use
     > JDBC and the parsing of database-vendor specific error codes.
     >
     > https://mvnrepository.com/artifact/org.springframework/spring-jdbc/5.3.39
  1. Spring Transaction » 5.3.39 (`org.springframework:spring-tx:5.3.39`)
     > Support for programmatic and declarative transaction management for
     > classes that implement special interfaces or any POJO.
     >
     > https://mvnrepository.com/artifact/org.springframework/spring-tx/5.3.39


No separate CGLIB/ASM jar is needed — Spring repackages both inside `spring-core`.

---

