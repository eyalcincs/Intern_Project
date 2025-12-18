package com.stajprojeleri.library.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.stajprojeleri.library.entity.User;


public interface IUserService {
	
	public User saveUser(User user);
	
	public User getUser(String username , String password);

}
