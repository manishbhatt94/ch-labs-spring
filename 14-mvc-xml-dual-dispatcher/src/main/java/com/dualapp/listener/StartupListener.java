package com.dualapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dualapp.service.ArticleService;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		/*
		 * @formatter:off
         * At this point in the startup sequence:
         * - ContextLoaderListener has already fired (declared before us in web.xml)
         * - Root WAC is alive and stored on ServletContext
         * - Neither news-dispatcher nor admin-dispatcher has initialized yet
         * - Therefore: no child WAC exists yet
         *
         * We retrieve the root WAC explicitly via WebApplicationContextUtils —
         * the only bridge available to servlet container-managed components
         * (listeners, filters) that live outside Spring's IoC container.
         * @formatter:on
         */
		System.out.println("=== StartupListener fired ===");
		System.out.println("Root WAC is alive. Child WACs do not exist yet.");

		WebApplicationContext rootWac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

		/*
         * @formatter:off
         * Directly fetching bean from root WAC via getBean() —
         * this is acceptable here because StartupListener is NOT a Spring
         * bean. It's instantiated by Tomcat. Spring cannot inject into it.
         * Manual getBean() is the only option available.
         * (Compare: controllers never do this — they get beans injected.)
         * @formatter:on
         */
		ArticleService articleService = rootWac.getBean(ArticleService.class);

		System.out.println("ArticleService retrieved from root WAC directly.");
		System.out.println("Article count at startup: " + articleService.getArticleCount());
		System.out.println("Articles: " + articleService.getAllArticles());
		System.out.println("=== StartupListener done. DispatcherServlets will init next. ===");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("=== App shutting down. StartupListener.contextDestroyed fired. ===");
	}

}
