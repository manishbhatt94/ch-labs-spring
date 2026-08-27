package com.bookshelf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bookshelf.service.BookService;

@Controller
public class BookController {

	/*
     * @formatter:off
     * @Autowired — Spring injects BookService here automatically.
     * BookService is defined in root WAC (root-context.xml).
     * BookController is defined in child WAC (dispatcher-servlet.xml
     * via component scan).
     * Spring resolves the dependency by walking up to parent root WAC
     * transparently — no special code needed.
     *
     * Compare to Case 2: we used XML <property ref="articleService"/>
     * for the same effect. @Autowired is the annotation equivalent.
     * @formatter:on
     */
	@Autowired
	private BookService bookService;

}
