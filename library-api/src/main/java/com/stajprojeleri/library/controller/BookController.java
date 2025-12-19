package com.stajprojeleri.library.controller;

import java.nio.file.Path;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stajprojeleri.library.entity.Book;
import com.stajprojeleri.library.service.IBookService;
import com.stajprojeleri.library.service.impl.BookServiceImpl;
import com.stajprojeleri.library.dto.DtoBook;
import com.stajprojeleri.library.dto.DtoBookIU;
import com.stajprojeleri.library.entity.*;

@RestController
@RequestMapping("/api/books")


public class BookController {

	@Autowired 
	private IBookService bookService;
	
	@PostMapping
	public DtoBook saveBook(@RequestBody DtoBookIU dtoBookIU) {
		return bookService.saveBook(dtoBookIU);
	}
	 @GetMapping
	    public List<DtoBook> getAllBooks() {
	        return bookService.getAllBooks();
	    }
	 @GetMapping(path="/{id}")
	 public DtoBook getBook(@PathVariable(name="id") Integer id){
		 return bookService.getBookById(id);
		 
	 }
	 
	 @DeleteMapping(path= "/{id}")
		 public void deleteBookById(@PathVariable(name="id") Integer id){
			 bookService.deleteBookById(id);
		 }
	 @DeleteMapping
	 public void deleteBooks(){
		 bookService.deleteBooks();
	 }
	 
	 @PutMapping(path = "/{id}")
	 
	 public DtoBook updateBook(@PathVariable(name ="id") Integer id , @RequestBody DtoBookIU dtoBookIU) {
		 	
		 return bookService.updateBook(id, dtoBookIU);
	 }
	 
	 
}
