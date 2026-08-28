package com.studyplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.studyplanner.service.Course;
import com.studyplanner.service.CourseService;

@Controller
public class CourseController {

	/*
     * @formatter:off
     * @Autowired — CourseService lives in root WAC.
     * CourseController lives in child WAC.
     * Spring resolves this by walking up to parent root WAC transparently.
     * No explicit fetch from any context needed.
     * @formatter:on
     */
	@Autowired
	private CourseService courseService;

	@GetMapping("/courses")
	public String listCourses(Model model) {
		model.addAttribute("courses", courseService.getAllCourses());
		return "courses";
	}

	/*
     * @formatter:off
     * @PathVariable extracts {id} from the URL.
     * e.g. GET /courses/2 → id=2
     * Only possible because RequestMappingHandlerMapping
     * (registered by @EnableWebMvc) handles URL pattern matching.
     * BeanNameUrlHandlerMapping in Cases 1/2 could not do this.
     * @formatter:on
     */
	@GetMapping("/courses/{id}")
	public String courseDetail(@PathVariable int id, Model model) {
		model.addAttribute("course", courseService.getCourseById(id));
		return "course-detail";
	}

	/*
	 * @formatter:off
	 * Shows the empty "add course" form.
	 * We add an empty Course object to the model — the JSP form uses it
	 * as a backing object via Spring's form tag (or plain HTML + @ModelAttribute).
	 * @formatter:on
	 */
	@GetMapping("/courses/new")
	public String showCourseForm(Model model) {
		model.addAttribute("course", new Course());
		return "course-form";
	}

	/*
	 * @formatter:off
	 * @PostMapping — handles form submission (HTTP POST to /courses).
	 *
	 * @ModelAttribute Course course:
	 * Spring sees the form POST, creates a new Course() using no-arg constructor,
	 * then calls setName() and setDescription() from the form field values
	 * (matched by field name). This is called form/model binding.
	 * You get a fully populated Course object without manually reading
	 * request.getParameter("name") etc.
	 *
	 * POST → Redirect → GET pattern (PRG):
	 * After saving, we redirect to /courses instead of returning a view directly.
	 * If we returned "courses" view directly after POST, hitting browser refresh
	 * would re-submit the POST — adding a duplicate course each time.
	 * Redirecting to GET /courses means refresh just re-fetches the list — safe.
	 * "redirect:/courses" tells DispatcherServlet to send HTTP 302 to the browser.
	 * @formatter:on
	 */
	@PostMapping("/courses")
	public String addCourse(@ModelAttribute Course course) {
		courseService.addCourse(course);
		return "redirect:/courses";
	}

}
