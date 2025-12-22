package com.stajprojeleri.library.service.impl;

import java.beans.beancontext.BeanContext;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.format.MatchStrength;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public DtoUser saveUser(DtoUserIU dtoUserIU) {
		DtoUser response = new DtoUser();
		User user = new User();
		
		BeanUtils.copyProperties(dtoUserIU,user);
		user.setPassword(encoder.encode(dtoUserIU.getPassword()));
		User dbuser = userRepository.save(user);
		
		
		
		BeanUtils.copyProperties(dbuser,response);
		
		return response;
		
	}
	
	 @Override
	 public User getUser(String username, String password) {
		    User user = userRepository.findByUsername(username).orElse(null);
		    if (user == null) return null;

		    if (!encoder.matches(password, user.getPassword())) return null;

		    return user;
	}


	 @Override
	 public User register(User user) {
		 user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	 }

}
