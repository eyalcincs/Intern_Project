package com.stajprojeleri.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserIU {
	
	private String name;
	
	private String surname;
	
	private String username;
	
	private String email;
	
	private String password;

}
