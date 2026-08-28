package com.studyplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*
 * @formatter:off
 * Replaces /WEB-INF/dispatcher-servlet.xml
 *
 * @Configuration = source of bean definitions for the CHILD WAC.
 *
 * @EnableWebMvc = exact Java equivalent of <mvc:annotation-driven/>.
 * Registers RequestMappingHandlerMapping, RequestMappingHandlerAdapter,
 * default HttpMessageConverters, type conversion, validation integration.
 * Only ONE @Configuration class per child WAC should carry this annotation —
 * putting it on two classes in the same WAC causes duplicate bean conflicts.
 *
 * @ComponentScan = scans controller package for @Controller classes.
 * Scoped to controller package only — service package already scanned by RootConfig.
 *
 * implements WebMvcConfigurer — provides callback methods corresponding to
 * <mvc:...> namespace tags. Override only what you need; all methods have
 * default empty implementations.
 * @formatter:on
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.studyplanner.controller")
public class WebConfig implements WebMvcConfigurer {

	/*
	 * @formatter:off
     * Replaces: <mvc:view-controller path="/" view-name="home"/>
     * Maps "/" directly to "home" view — no controller method needed.
     * @formatter:on
     */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

	/*
	 * @formatter:off
     * Replaces: <mvc:resources mapping="/static/**" location="/static/"/>
     * Needed because DispatcherServlet is mapped to "/" — it intercepts
     * everything including static file requests. This carves out /static/**
     * and serves those files directly, bypassing controllers.
     * @formatter:on
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	/*
	 * @formatter:off
     * Replaces:
     *   <bean class="InternalResourceViewResolver">
     *     <property name="prefix" value="/WEB-INF/views/"/>
     *     <property name="suffix" value=".jsp"/>
     *   </bean>
     *
     * @Bean method = equivalent to a <bean> declaration in XML.
     * Method name ("viewResolver") becomes the bean id.
     * Return value is the bean instance registered in the child WAC.
     * @formatter:on
     */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
