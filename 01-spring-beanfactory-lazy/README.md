## Experiment: Compile & Run from CLI (javac & java)

**Switch directory & create binary directory:**
```cmd
cd .\01-spring-beanfactory-lazy\

mkdir .\bytecode\
```

**Compile using javac:**

```cmd
javac -Xlint:deprecation -cp ".\src\;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" -d .\bytecode\ -sourcepath .\src\ .\src\com\mainapp\Launch.java

:: Or in separate lines (CMD uses carot symbol ^ to break command in multiple lines):
javac ^
  -Xlint:deprecation ^
  -cp ".\src\;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" ^
  -d .\bytecode\ ^
  -sourcepath .\src\ ^
  .\src\com\mainapp\Launch.java
```

```powershell
# In Powershell, the line break character to split command in multiple lines is backtick (`):

javac `
  -Xlint:deprecation `
  -cp ".\src\;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" `
  -d .\bytecode\ `
  -sourcepath .\src\ `
  .\src\com\mainapp\Launch.java
```

**Copy resources (XML files):**

```powershell
Copy-Item .\src\beans.xml .\bytecode\
```

**Run the program using JVM (java):**

```cmd
java -cp ".\bytecode\;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" com.mainapp.Launch

:: Or in separate lines (CMD uses carot symbol ^ to break command in multiple lines):
java ^
  -cp ".\bytecode\;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" ^
  com.mainapp.Launch
```

### Peek the classpath Eclipse IDE generates when running your Java Project

- Go to top menu - "Run" -> "Run Configurations".
- "Run Configurations" dialog window opens up.
- On the left sidebar, under "Java Application", find your main class, e.g. "Launch"
  (if main class is called "Launch.java").
- Click that and in the main section of the dialog window, verify the **main class
  name**, and the **project name**.
- At the bottom, find and click the "Show Command Line" button.
- This opens a "Command Line" popup window which contains the entire JVM command
  used by Eclipse.
- Eclipse uses `javaw.exe` (Java Windowless) instead of `java.exe` in order to
  prevent the system default command prompt from opening whenever the project is
  run, and to enable Eclipse's own Console to capture the output.

Here is the command line JVM run sample command I copied from Eclipse:

```cmd
C:\Users\Manish\AppData\Local\javm\jdk\temurin@8.0.482\bin\javaw.exe ^
  -Dfile.encoding=UTF-8 ^
  -Dstdout.encoding=UTF-8 ^
  -Dstderr.encoding=UTF-8 ^
  -classpath "C:\Users\Manish\Dev\java\CodeHunt\ch-labs-spring\01-spring-beanfactory-lazy\bin;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-beans-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-context-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-core-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-expression-5.3.39.jar;C:\Users\Manish\Dev\mvn-libs-jar-files\spring-jcl-5.3.39.jar" ^
  com.mainapp.Launch

:: Note: Added the line separator (^) symbols myself (Not present in Eclipse)
```

