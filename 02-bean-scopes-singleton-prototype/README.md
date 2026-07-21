# Bean Scopes: Singleton, Prototype

## Short notes

Spring provides many bean scopes like singleton, prototype, request, session, etc.
But here, we will focus on the two most commonly used scopes:

### Singleton Scope (Default)

- Meaning: Only one object (bean) is created per Spring container.
- Shared instance is returned every time it is injected or requested.
- Default scope if you don’t specify any scope.

### Prototype Scope

- Meaning: A new object is created every time the bean is requested.
- Spring does not manage the full lifecycle beyond creation (destroy callbacks
are not called by Spring IOC Container).


## Sample run output

```
===== Loading Spring IOC container from beans.xml =====

[Employee #static_block] Employee class is loaded
[Employee #constructor] Employee object (Spring bean) is created
[Salary #static_block] Salary class is loaded
[Salary #constructor] Salary object (Spring bean) is created

===== Container loaded. All singleton-scoped beans are already created above. =====

----- Testing 'emp' bean (default scope = singleton) -----
[Employee #test] Employee bean Employee@1e397ed7{employeeName="Alice"} is working
[Employee #test] Employee bean Employee@1e397ed7{employeeName="Alice"} is working
emp1 = Employee [employeeName=Alice]
emp2 = Employee [employeeName=Alice]
emp1 == emp2 ? true  --> Same object returned (singleton behavior)

----- Testing 'project' bean (scope = prototype) -----
[Project #static_block] Project class is loaded
[Project #constructor] Project object (Spring bean) is created
[Project #test] Project bean Project@56ac3a89{projectName="Spring Migration"} is working
[Project #constructor] Project object (Spring bean) is created
[Project #test] Project bean Project@27c20538{projectName="null"} is working
project1 = Project [projectName=Spring Migration]
project2 = Project [projectName=null]
project1 == project2 ? false  --> Different objects returned (prototype behavior)

----- Testing 'salary' bean (scope = singleton, explicitly set) -----
[Salary #test] Salary bean Salary@72d818d1{basicPay=50000.0} is working
[Salary #test] Salary bean Salary@72d818d1{basicPay=50000.0} is working
salary1 = Salary [basicPay=50000.0]
salary2 = Salary [basicPay=50000.0]
salary1 == salary2 ? true  --> Same object returned (singleton behavior)

```
