package com.bookshelf.service;

import java.util.Arrays;
import java.util.List;

public class BookService {

	/*
	 * Hardcoded dummy data — no database. This bean lives in root WAC (declared in
	 * root-context.xml). No @Service annotation — Spring finds it via explicit
	 * <bean> tag, not component scanning.
	 */
	// @formatter:off
	private static final List<Book> BOOKS = Arrays.asList(
			new Book(1, "Clean Code", "Robert C. Martin"),
			new Book(2, "The Pragmatic Programmer", "Andrew Hunt"),
			new Book(3, "Effective Java", "Joshua Bloch"));
	// @formatter:on

	public List<Book> getAllBooks() {
		return BOOKS;
	}

	public Book getBookById(int id) {
		for (Book book : BOOKS) {
			if (book.getId() == id) {
				return book;
			}
		}
		return null;
	}

}
