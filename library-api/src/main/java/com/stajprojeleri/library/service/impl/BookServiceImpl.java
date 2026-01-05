package com.stajprojeleri.library.service.impl;

import com.stajprojeleri.library.dto.DtoBook;
import com.stajprojeleri.library.dto.DtoBookIU;
import com.stajprojeleri.library.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebRequestDataBinder;

import com.stajprojeleri.library.repository.BookRepository;
import com.stajprojeleri.library.repository.UserRepository;
import com.stajprojeleri.library.service.IBookService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public DtoBook saveBook( DtoBookIU dtoBookIU) {
		DtoBook response = new DtoBook();
		Book book = new Book();
		BeanUtils.copyProperties(dtoBookIU,book);
		
		Book dbbook = bookRepository.save(book);	// Entity olmalı

		BeanUtils.copyProperties(dbbook,response);
		return response;
		
	}
	
	@Override
	public List<DtoBook> getAllBooks() {
		List<DtoBook> dtoList = new ArrayList<>();
		
		List<Book> bookList = bookRepository.findAll();
		
		for (Book  book: bookList ) {
			
			DtoBook dtoBook =new DtoBook();
			
			BeanUtils.copyProperties(book, dtoBook);
			
			dtoList.add(dtoBook);
			
		}
		return dtoList;
		
	}
	
	@Override
	public DtoBook getBookById(Integer id) {
	DtoBook dtoBook = new DtoBook();
	Optional<Book> optional =	bookRepository.findById(id);
		
	if(optional.isPresent()) {
		Book dbbooks = optional.get();
		
	BeanUtils.copyProperties(dbbooks, dtoBook);
		
	}
		
		return dtoBook;
	}

	@Override
	public void deleteBookById(Integer id) {
		Optional<Book> optional =	bookRepository.findById(id);
		
		if(optional.isPresent()) {
			bookRepository.delete(optional.get());
		}
		
	}

	@Override
	public void deleteBooks() {
		
		bookRepository.deleteAll();
		
	}

	@Override
	public DtoBook updateBook(Integer id, DtoBookIU dtoBookIU) {

	    Optional<Book> optional = bookRepository.findById(id);

	    if (optional.isPresent()) {
	        Book dbbook = optional.get();

	        dbbook.setBookName(dtoBookIU.getBookName());
	        dbbook.setPageCount(dtoBookIU.getPageCount());
	        dbbook.setAuthor(dtoBookIU.getAuthor());
	        dbbook.setRegisterDate(dtoBookIU.getRegisterDate());
	        dbbook.setLoanDate(dtoBookIU.getLoanDate());
	        dbbook.setCategory(dtoBookIU.getCategory());

	        Book updated = bookRepository.save(dbbook);

	        DtoBook dtoBook = new DtoBook();
	        BeanUtils.copyProperties(updated, dtoBook);
	        return dtoBook;
	    }

	    return null; 
	}

	
	
}
