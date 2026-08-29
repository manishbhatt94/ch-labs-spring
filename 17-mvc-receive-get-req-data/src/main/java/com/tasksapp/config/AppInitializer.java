package com.tasksapp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// This is effectively an implementation class of WebApplicationInitializer (WAI) interface,
// for programmatic setup of our MVC app. Actually this class extends an abstract class
// AbstractAnnotationConfigDispatcherServletInitializer (AACDSI) which is a descendant of
// the WebApplicationInitializer (WAI) interface.

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Here, we return null, since we don't need a separate Root
		// WebApplicationContext (WAC) nor the Root-Child WAC hierarchy, in this
		// project.
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/tasks-app/*" };
	}

}
