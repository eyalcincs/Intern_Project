package com.stajprojeleri.library.service.impl;

import com.stajprojeleri.library.entity.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebRequestDataBinder;

import com.stajprojeleri.library.repository.BookRepository;
import com.stajprojeleri.library.security.SecurityConfig;
import com.stajprojeleri.library.service.IBookService;

@Service
public class BookServiceImpl implements IBookService {

    private final SecurityConfig securityConfig;
	
	@Autowired
	private BookRepository bookRepository;

    BookServiceImpl(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }
	
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
		
	}
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	@Override
	public Book getBookById(Integer id) {
		
	Optional<Book> optional =	bookRepository.findById(id);
		
	if(optional.isPresent()) {
		return optional.get();
		
	}
		
		return null;
	}

	@Override
	public void deleteBookById(Integer id) {
		Book dbbook = getBookById(id);
		if(dbbook != null) {
			bookRepository.delete(dbbook);
		}
		
	}

	@Override
	public void deleteBooks() {
		
		bookRepository.deleteAll();
		
	}

	@Override
	public Book updateBook(Integer id, Book updateBook) {
		Book dbBook =	getBookById(id);
		
		if(dbBook != null) {
			dbBook.setBookName(updateBook.getBookName());
			dbBook.setPageCount(updateBook.getPageCount());
			dbBook.setAuthor(updateBook.getAuthor());
			dbBook.setRegisterDate(updateBook.getRegisterDate());
			dbBook.setLoanDate(updateBook.getLoanDate());
			dbBook.setCategoryString(updateBook.getCategoryString());
			
		   return bookRepository.save(dbBook);
			
		}
		return null;
	}
	
	
	
	
	
	

}
