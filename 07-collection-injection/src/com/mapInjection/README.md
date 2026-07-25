# Map Injection - XML Approach

Good scenario for Map: a student's **subject → marks** record (simple key-value
pairs), a **day-of-week → scheduled course** weekly timetable (value as bean
reference, plus a holiday with a `null` value), and a standalone
**course → coordinator name** lookup that deliberately demonstrates why
`equals()`/`hashCode()` matter for *Map keys* — not just Set elements.

**Design choices, explained up front:**

- Spring's plain `<map>` (nested in `constructor-arg`/`property`) defaults to
  `LinkedHashMap` — insertion order preserved, keys unique.
- `<util:map map-class="...">` lets you pick `HashMap` (fast, unordered) or
  `TreeMap` (always sorted by key).
- **Corner case #1 (Map contract, not implementation-specific):** duplicate
  keys in XML don't throw an error — the **last value silently wins**. We
  demonstrate this in `subjectMarks`.
- **Corner case #2 (keys need `equals()`/`hashCode()`):** in
  `courseCoordinators`, `Course` is used as a **map key**. Two
  *value-equal-but-distinct* `Course` beans used as keys cause the second
  insertion to **overwrite** the first's value — same root cause as the Set
  duplicate-drop, but manifests differently for Map keys.
- **Corner case #3:** `<null/>` as a value — Sunday has no scheduled course.
- Note: `TreeMap` keys must be mutually `Comparable` (or you supply a
  `Comparator`) — safe here since keys are `String`s; using `Course` objects as
  `TreeMap` keys would need `Course implements Comparable<Course>`, which we
  call out in a comment rather than demo, to keep things brief.

### Map Interface Implementation Classes Cheat-Sheet

| Implementation | Order | When to use |
| --- | --- | --- |
| `HashMap` | none guaranteed | Pure lookup table; fastest `get`/`put` |
| `LinkedHashMap` (Spring's `<map>` default) | insertion order | Lookup + predictable, stable iteration/display order |
| `TreeMap` | sorted by key | Entries must always come out sorted; keys must be `Comparable` (or supply a `Comparator`) |


Two things to always remember with Maps:
**duplicate keys never throw — the last value silently wins**, and for custom
objects used as keys, override `equals()`/`hashCode()`, or else, "the same key"
quietly becomes "two different keys" (or vice versa, causing silent overwrites).


## Program Sample Run Output

```txt
######### Map Injection (XML) Demo ################


pincodeToCity.getClass() => class java.util.HashMap
pincodeToCity: {560001=Bengaluru, 600001=Chennai, 500001=Hyderabad, 226001=Lucknow}   (iteration order not guaranteed)

subjectAverages.getClass() => class java.util.TreeMap
subjectAverages: {English=79.0, Math=92.0, Science=88.5}   (always sorted by key, regardless of XML order)

courseCoordinators.getClass() => class java.util.HashMap
courseCoordinators.size() => 3   (4 <entry> lines in XML, but only 3 survive -- see comment in beans.xml)
courseCoordinators: {Course [courseName=Electronic Design and Circuits, courseCredits=4]=Prof. Rashmi Balakrishnan, Course [courseName=Database Management Systems, courseCredits=7]=Prof. Spoorthy Udayakumar Kulkarni, Course [courseName=Compiler Design, courseCredits=5]=Prof. Suresh Iyer}
Coordinator for 'Database Management Systems' course (created freshly outside IOC Container) =>
     Prof. Spoorthy Udayakumar Kulkarni

Student #1:
Student [studentName=Kamal Chandra, subjectMarks={Math=85, Science=90, English=75}, weeklyTimetable={Monday=Course [courseName=Database Management Systems, courseCredits=7], Wednesday=Course [courseName=Compiler Design, courseCredits=5], Friday=Course [courseName=Electronic Design and Circuits, courseCredits=4], Sunday=null}]

Student #2:
Student [studentName=R.K. Saurabh, subjectMarks={Science=95, Math=80}, weeklyTimetable={Tuesday=Course [courseName=Compiler Design, courseCredits=5], Thursday=Course [courseName=Electronic Design and Circuits, courseCredits=4]}]

s1.getSubjectMarks().getClass() => class java.util.LinkedHashMap   (plain <map/> nested in <property> -> defaults to LinkedHashMap)
s1.getSubjectMarks().size() => 3   (4 <entry> lines for 'English' x2 declared, only 3 unique keys survive)
s1.getSubjectMarks().get("English") => 75   (last value for the repeated key wins, not the first)

s1.getWeeklyTimetable().get("Sunday") => null   (Map values, unlike Set elements, are allowed to be null)


```

