package com.dualapp.service;

import java.util.Arrays;
import java.util.List;

public class ArticleService {

	/*
	 * Dummy data — no database. Just returns hardcoded article titles. This bean
	 * lives in root WAC. Both NewsController and AdminController get the SAME
	 * instance of this class injected into them — demonstrating that root WAC beans
	 * are truly shared singletons across child WACs.
	 */
	public List<String> getAllArticles() {
		return Arrays.asList("Spring MVC Explained", "Understanding DispatcherServlet",
				"Root vs Child WebApplicationContext");
	}

	public int getArticleCount() {
		return getAllArticles().size();
	}

}
