package com.dualapp.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dualapp.service.ArticleService;

public class AdminController implements Controller {

	/*
	 * Same ArticleService injected here too — but this is the SAME singleton
	 * instance that NewsController received. Root WAC beans are singletons shared
	 * across the entire application, including all child WACs.
	 */
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("AdminController.handleRequest() — serving /admin/dashboard");

		int count = articleService.getArticleCount();

		ModelAndView mav = new ModelAndView("dashboard");
		mav.addObject("articleCount", count);

		return mav;

	}

}
