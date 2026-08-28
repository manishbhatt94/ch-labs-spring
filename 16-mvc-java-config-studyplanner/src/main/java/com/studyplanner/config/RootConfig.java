package com.studyplanner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * @formatter:off
 * Replaces /WEB-INF/root-context.xml
 *
 * @Configuration = this class is a source of Spring bean definitions.
 * Equivalent to the <beans> root element in XML.
 *
 * @ComponentScan = equivalent to <context:component-scan> in XML.
 * Scans com.studyplanner.service for @Service/@Component/@Repository classes
 * and registers them as beans in the ROOT WAC.
 *
 * Deliberately scoped to service package only — NOT controller package.
 * Controllers belong in the child WAC (WebConfig scans them separately).
 * Scanning controllers here would register them in root WAC — they'd be
 * invisible to DispatcherServlet which looks in the child WAC.
 *
 * No explicit @Bean methods needed here — @ComponentScan finds CourseService
 * via its @Service annotation automatically.
 * @formatter:off
 */
@Configuration
@ComponentScan("com.studyplanner.service")
public class RootConfig {
}
