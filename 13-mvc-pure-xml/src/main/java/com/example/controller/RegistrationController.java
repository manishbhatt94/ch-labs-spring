package com.example.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class RegistrationController implements Controller {

	/*
	 * Spring's Controller interface
	 * (org.springframework.web.servlet.mvc.Controller) has exactly one method:
	 * handleRequest(). This is the single entry point for ALL requests routed to
	 * this controller.
	 *
	 * No @GetMapping, no @PostMapping — HTTP method filtering (GET vs POST) is
	 * something you handle yourself inside this method if needed, by checking
	 * request.getMethod().
	 *
	 * This is what @RequestMapping/@GetMapping was hiding: underneath, there is
	 * always one method being invoked by the HandlerAdapter. Annotations just made
	 * it look like any method could be the entry point.
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("RegistrationController.handleRequest() invoked");

		ServletContext sc = request.getServletContext();
		WebApplicationContext rootWac = WebApplicationContextUtils.getWebApplicationContext(sc);
		System.out.println("[RegistrationController.handleRequest] Root WAC: " + rootWac); // Prints: null

		/*
		 * ModelAndView bundles two things together:
		 *
		 * 1. The logical view name ("register") → InternalResourceViewResolver turns
		 * this into /WEB-INF/view/register.jsp
		 *
		 * 2. Model data (key-value pairs available in the JSP as ${key})
		 *
		 * This is what returning a String + Model parameter was hiding in the
		 * annotation style. Under the hood, Spring converts that into a ModelAndView
		 * anyway.
		 */
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("message", "Welcome to the Registration Page!");
		return mav;

	}

}
