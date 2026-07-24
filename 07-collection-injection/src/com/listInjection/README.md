# List Injection - XML Approach

### Program Sample Run Output

```txt
planets.getClass() => class java.util.LinkedList
planets: [Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto]
planets == planets2 ---> true. (i.e. singleton)

benefits.getClass() => class java.util.ArrayList
benefits: [Health Allowance, Medical Insurance, Meal Coupons]

Student #1:
Student [studentName=Kamal Chandra, benefits=[Health Allowance, Medical Insurance, Meal Coupons], courses=[Course [courseName=Database Management Systems, courseCredits=7], Course [courseName=Compiler Design, courseCredits=5], Course [courseName=Electronic Design and Circuits, courseCredits=4]], qualifications=[B.Tech., B.A. L.L.B.]]

Student #2:
Student [studentName=R.K. Saurabh, benefits=[Health Allowance, Medical Insurance, Meal Coupons], courses=[Course [courseName=Compiler Design, courseCredits=5], Course [courseName=Electronic Design and Circuits, courseCredits=4], Course [courseName=Computer Networking, courseCredits=6]], qualifications=[M.Sc., Ph.D.]]


→ s1.getBenefits() == s2.getBenefits() ==> true

→ s1.getCourses() == s2.getCourses() ==> false

→ s1.getQualifications() == s2.getQualifications() ==> false

```
