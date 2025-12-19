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


	public class DtoBook {

	private String bookName;
	
	private String categoryString;

	private LocalDate registerDate; 

}
