package com.stajprojeleri.library.service;

import java.util.List;

import com.stajprojeleri.library.entity.*;

public interface IBookService {
	
	public Book saveBook(Book book);
	
	public List<Book> getAllBooks();
	
	public Book getBookById(Integer id);
	
	public void deleteBookById(Integer id);	
	
	public void deleteBooks();	
	
	public Book updateBook(Integer id, Book updateBook);
}
