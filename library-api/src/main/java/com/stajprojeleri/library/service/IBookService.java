package com.stajprojeleri.library.service;

import java.util.List;

import com.stajprojeleri.library.dto.DtoBook;
import com.stajprojeleri.library.dto.DtoBookIU;
import com.stajprojeleri.library.entity.*;

public interface IBookService {
	
	public DtoBook saveBook(DtoBookIU dtoBookIU);
	
	public List<DtoBook> getAllBooks();
	
	public DtoBook getBookById(Integer id);
	
	public void deleteBookById(Integer id);	
	
	public void deleteBooks();	
	
	public DtoBook updateBook(Integer id, DtoBookIU dtoBookIU);
}
