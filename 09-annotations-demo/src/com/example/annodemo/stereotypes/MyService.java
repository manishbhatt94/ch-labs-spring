package com.example.annodemo.stereotypes;

import org.springframework.stereotype.Service;

/**
 * @Service is meta-annotated with @Component -> functionally identical, it's a
 *          semantic marker for "this bean holds business logic".
 */
@Service
public class MyService {
	public MyService() {
		System.out.println("[stereotypes] MyService constructed (@Service)");
	}
}
