package com.stajprojeleri.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.repository.UserRepository;
import com.stajprojeleri.library.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
		
	}
	
	 @Override
	    public User getUser(String username, String password) {
	        return userRepository
	                .findByUsernameAndPassword(username, password)
	                .orElse(null); 
	    }

}
