package com.stajprojeleri.library.service.impl;

import java.beans.beancontext.BeanContext;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.dto.DtoUserIU;
import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.repository.UserRepository;
import com.stajprojeleri.library.service.IUserService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public DtoUser saveUser(DtoUserIU dtoUserIU) {
		DtoUser response = new DtoUser();
		User user = new User();
		
		BeanUtils.copyProperties(dtoUserIU,user);
		
		User dbuser = userRepository.save(user);
		
		BeanUtils.copyProperties(dbuser,response);
		
		return response;
		
	}
	
	 @Override
	    public User getUser(String username, String password) {
	        return userRepository
	                .findByUsernameAndPassword(username, password)
	                .orElse(null); 
	    }

}
