package com.studyplanner.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * @formatter:off
 * This class REPLACES web.xml entirely.
 *
 * How Tomcat discovers it without web.xml:
 * 1. Tomcat scans JARs for META-INF/services/javax.servlet.ServletContainerInitializer
 * 2. Finds that file inside spring-web.jar — declares SpringServletContainerInitializer
 * 3. Tomcat calls SpringServletContainerInitializer.onStartup(Set<Class<?>>, ServletContext)
 * 4. Spring scans YOUR app for classes implementing WebApplicationInitializer
 * 5. Finds this class (via AbstractAnnotationConfigDispatcherServletInitializer - short AACDSI,
 *    which implements WebApplicationInitializer)
 * 6. Calls onStartup(ServletContext) — which AbstractAnnotationConfigDispatcherServletInitializer
 *    (AACDSI) implements, doing the addListener() + addServlet() plumbing internally
 *
 * You just fill in the three slots below (i.e. implement the three methods - getRootConfigClasses,
 * getServletConfigClasses, getServletMappings):
 * @formatter:on
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/*
	 * @formatter:off
	 * Replaces:
	 *   <context-param>
	 *     <param-name>contextConfigLocation</param-name>
	 *     <param-value>/WEB-INF/root-context.xml</param-value>
	 *   </context-param>
	 *   <listener>
	 *     <listener-class>ContextLoaderListener</listener-class>
	 *   </listener>
	 *
	 * AACDSI internally creates an AnnotationConfigWebApplicationContext,
	 * registers RootConfig.class into it, wraps it in ContextLoaderListener,
	 * and adds that listener to the ServletContext.
	 *
	 * Return null here if no root WAC is desired (single context app).
	 * Array allows splitting root bean definitions across multiple @Configuration
	 * classes — all registered into one root WAC.
	 * @formatter:on
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/*
	 * @formatter:off
	 * Replaces: DispatcherServlet reading /WEB-INF/dispatcher-servlet.xml
	 *
	 * AACDSI internally creates an AnnotationConfigWebApplicationContext,
	 * registers WebConfig.class into it, passes it to a new DispatcherServlet.
	 * DispatcherServlet.init() finds the root WAC on ServletContext and sets
	 * it as this child context's parent — parent-child link established there,
	 * not here explicitly.
	 *
	 * For multiple DispatcherServlets: create multiple AACDSI subclasses.
	 * Only one should return non-null getRootConfigClasses() — others return null
	 * to avoid creating duplicate root WACs.
	 * @formatter:on
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/*
	 * @formatter:off
	 * Replaces:
	 *   <servlet-mapping>
	 *     <servlet-name>dispatcher</servlet-name>
	 *     <url-pattern>/</url-pattern>
	 *   </servlet-mapping>
	 *
	 * String[] because one DispatcherServlet (DS) can handle multiple URL patterns.
	 * This is NOT for declaring multiple DispatcherServlets — it's multiple
	 * URL patterns for the single DS that this AACDSI subclass registers.
	 * @formatter:on
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
