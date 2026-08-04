package com.example.annodemo.stereotypes;

import org.springframework.stereotype.Controller;

/**
 * @Controller is meta-annotated with @Component; in plain Spring Framework (no
 *             Spring MVC DispatcherServlet in this toy project) it behaves just
 *             like @Component. It matters once Spring MVC is on the classpath,
 *             where the DispatcherServlet specifically looks
 *             for @Controller-annotated beans to map @RequestMapping handler
 *             methods.
 */
@Controller
public class MyController {

	public MyController() {
		System.out.println("[stereotypes] MyController constructed (@Controller)");
	}

}
