package com.stajprojeleri.library.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.dto.DtoUserIU;
import com.stajprojeleri.library.entity.User;


public interface IUserService {
	
	public DtoUser saveUser(DtoUserIU dtoUserIU);
	
	public User getUser(String username , String password);
	
	public User register(User user);

}
