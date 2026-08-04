package com.example.annodemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

/**
 * XML equivalent:
 * <context:component-scan base-package="com.example.annodemo.filters">
 * <context:include-filter type="regex" expression=".*Stub.*"/>
 * <context:exclude-filter type="annotation" expression=
 * "org.springframework.stereotype.Repository"/> </context:component-scan>
 *
 * Corner case demonstrated: includeFilters and excludeFilters can combine. -
 * includeFilters widens the net beyond plain @Component-family classes. -
 * excludeFilters narrows it back, even overriding an otherwise-valid stereotype
 * annotation (@Repository is excluded here on purpose).
 */
// @formatter:off
@Configuration
@ComponentScan(
		basePackages = "com.example.annodemo.filters",
		includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Stub.*"),
		excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Repository.class)
)
// @formatter:on
public class FilterConfig {

}
