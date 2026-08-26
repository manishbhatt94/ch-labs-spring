package com.dualapp.news;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dualapp.service.ArticleService;

public class NewsController implements Controller {

	/*
     * @formatter:off
     * ArticleService is declared here as a plain field.
     * Spring injects it via the <property> tag in news-dispatcher-servlet.xml.
     * Spring resolves "articleService" by looking in the news child WAC first,
     * not finding it there, then walking up to the root WAC and finding it.
     * NewsController never fetches it from any context manually.
     * This is dependency injection — the correct pattern for Spring beans.
     * @formatter:on
     */
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("NewsController.handleRequest() — serving /news/articles");

		List<String> articles = articleService.getAllArticles();

		ModelAndView mav = new ModelAndView("articles");
		mav.addObject("articles", articles);

		return mav;

	}

}
