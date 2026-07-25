# Set Injection - XML Approach

Few points to note:

- Spring's plain `<set>` element (nested in a `<bean>`'s `<constructor-arg>` or
  a `<property>`) always builds a **`LinkedHashSet`** under the hood —
  uniqueness + insertion order preserved. Same as your `<list>` defaulting to
  `ArrayList`.
- To get a non-default implementation (`HashSet`, `TreeSet`, etc.), you use
  `<util:set set-class="...">`, exactly like we did with
  `<util:list list-class="...">`.
- **The corner case:** `Course` beans placed in a `Set<Course>` are
  deduplicated using `equals()`/`hashCode()`. I've deliberately declared two
  separate bean ids (`courseDatabase` and `courseDatabaseDuplicate`) with
  identical field values, plus one literal duplicate `<ref>`, to prove that
  only value-based `equals()`/`hashCode()` — not just reference identity —
  makes a `Set` behave correctly with custom objects.


### Set Interface Implementation Classes Cheat-Sheet

| Implementation	 | Order | When to use |
| --- | --- | --- |
| `HashSet` | none guaranteed | Only uniqueness matters; fastest add/contains/remove |
| `LinkedHashSet` (Spring's `<set>` default) | insertion order | Uniqueness + you want predictable, stable display order |
| `TreeSet` | sorted (natural/`Comparator`) | Elements must always come out sorted; requires elements to be `Comparable` (or supply a `Comparator`) |

## Program Sample Run Output

```txt
######### Set Injection (XML) Demo ################


skillTags.getClass() => class java.util.HashSet
skillTags: [Java, C++, JavaScript, Python]   (iteration order not guaranteed)

examCities.getClass() => class java.util.LinkedHashSet
examCities: [Bengaluru, Chennai, Hyderabad, Pune]   (duplicate 'Bengaluru' dropped, insertion order kept)

courseCodes.getClass() => class java.util.TreeSet
courseCodes: [CS105, CS220, CS301, CS410]   (duplicate 'CS105' dropped, always sorted)

commonHobbies.getClass() => class java.util.LinkedHashSet
commonHobbies: [Reading, Cricket, Chess]

Student #1:
Student [studentName=Kamal Chandra, hobbies=[Reading, Cricket, Chess], enrolledCourses=[Course [courseName=Database Management Systems, courseCredits=7], Course [courseName=Compiler Design, courseCredits=5], Course [courseName=Electronic Design and Circuits, courseCredits=4]], certifications=[Analytics for Dummies, Java Database Connectivity, Java EE]]

Student #2:
Student [studentName=R.K. Saurabh, hobbies=[Reading, Cricket, Chess], enrolledCourses=[Course [courseName=Compiler Design, courseCredits=5], Course [courseName=Electronic Design and Circuits, courseCredits=4], Course [courseName=Computer Networking, courseCredits=6]], certifications=[CUDA Programming, Machine Learning, Matrix Computations on GPU, OpenGL Introduction]]

s1.getEnrolledCourses().getClass() => class java.util.LinkedHashSet   (plain <set/> nested in a <property> -> defaults to LinkedHashSet)
s1.getEnrolledCourses().size() => 3   (5 <ref> entries declared in XML, only 3 unique courses survive because of Course#equals()/#hashCode(); without that override it would be 4)
s1.getCertifications().getClass() => class java.util.TreeSet

→ s1.getHobbies() == s2.getHobbies() ==> true   (both reference the same singleton 'commonHobbies' bean)

→ s1.getEnrolledCourses() == s2.getEnrolledCourses() ==> false   (each student got its own inline <set>, even though some Course beans are shared)


```
