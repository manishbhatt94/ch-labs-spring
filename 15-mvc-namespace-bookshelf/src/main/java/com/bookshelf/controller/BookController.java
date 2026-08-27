package com.bookshelf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookshelf.service.Book;
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

	/*
     * @formatter:off
     * @GetMapping("/books") — replaces:
     * 1. <bean name="/books"> in XML (BeanNameUrlHandlerMapping)
     * 2. implements Controller + handleRequest() method
     *
     * Model parameter — Spring injects this. We add attributes to it.
     * DispatcherServlet exposes them to the JSP as ${books}.
     * Returning a String — Spring wraps it into ModelAndView internally.
     * Compare Case 1/2: we returned ModelAndView explicitly.
     * @formatter:on
     */
	@GetMapping("/books")
	public String listBooks(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}

	/*
     * @formatter:off
     * @PathVariable — extracts the {id} segment from the URL.
     * e.g. GET /books/2 → id=2 → bookService.getBookById(2)
     * This wasn't possible in pure XML style (BeanNameUrlHandlerMapping
     * matches exact bean names, not URL patterns with variables).
     * RequestMappingHandlerMapping (registered by <mvc:annotation-driven/>)
     * handles pattern matching with path variables natively.
     * @formatter:on
     */
	@GetMapping("/books/{id}")
	public String bookDetail(@PathVariable int id, Model model) {
		Book book = bookService.getBookById(id);
		model.addAttribute("requestedBookId", id);
		model.addAttribute("book", book);
		return "book-detail";
	}

}
