package com.studyplanner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/*
 * @formatter:off
 * @Service = specialization of @Component for service layer beans.
 * Functionally identical to @Component — just more descriptive.
 * RootConfig's @ComponentScan picks this up and registers it in root WAC.
 *
 * In Cases 1 and 2 we used explicit <bean id="articleService"> in XML.
 * @Service + @ComponentScan is the annotation equivalent — same result,
 * Spring registers one singleton instance in the context.
 * @formatter:off
 */
@Service
public class CourseService {

    /*
     * In-memory list — acts as our "database" for this demo.
     * Since CourseService is a singleton in root WAC, this list
     * persists for the lifetime of the application.
     * Both GET (read) and POST (write) operations work against this list.
     */
    private List<Course> courses = new ArrayList<>();
    private int nextId = 1;

    public CourseService() {
        // Seed with some initial data
        courses.add(new Course(nextId++, "Spring Framework", "Core Spring, MVC, Data"));
        courses.add(new Course(nextId++, "Java Servlets & JSP", "Servlet lifecycle, JSP, JSTL"));
        courses.add(new Course(nextId++, "Design Patterns", "GoF patterns with Java examples"));
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course getCourseById(int id) {
        for (Course c : courses) {
            if (c.getId() == id) {
				return c;
			}
        }
        return null;
    }

    public void addCourse(Course course) {
        course.setId(nextId++);
        courses.add(course);
    }

}
