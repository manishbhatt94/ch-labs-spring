# Set Injection - XML Approach

### Program Sample Run Output

```txt
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
