package com.stajprojeleri.library.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="book")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Book {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //Otomatik olarak id artırmak
	private Integer id;
	
	@Column(name = "book_name" , nullable = false)
	private String bookName;
	
	@Column(name = "page_count",nullable = true)
	private int pageCount;
	
	@Column(name = "author",nullable = true)
	private String 	author;
	
	@Column(name = "category",nullable = true)
	private String category;
	
	@Column(name = "register_date", nullable = false)
	private LocalDate registerDate; 
	
	@Column(name = "loan_date", nullable = true)
	private LocalDate loanDate;

}
