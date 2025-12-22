package com.stajprojeleri.library.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBookIU {
	
	private String bookName;
	
	private int pageCount;
	
	private String 	author;
	
	private String category;

	private LocalDate registerDate; 
	
	private LocalDate loanDate;
	
}
