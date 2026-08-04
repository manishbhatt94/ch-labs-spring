package com.example.annodemo.stereotypes;

import org.springframework.stereotype.Repository;

/**
 * @Repository is meta-annotated with @Component AND additionally enables
 *             persistence-exception translation via
 *             PersistenceExceptionTranslationPostProcessor (converts native DB
 *             exceptions into Spring's DataAccessException hierarchy) - a
 *             behavior @Component/@Service do not add.
 */
@Repository
public class MyRepository {

	public MyRepository() {
		System.out.println("[stereotypes] MyRepository constructed (@Repository)");
	}

}
