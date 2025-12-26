package com.stajprojeleri.library.dto;

import com.stajprojeleri.library.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DtoUser {
	
	private String name;
	
	private String surname;
	
	private String username;
	
	private String email;

}
