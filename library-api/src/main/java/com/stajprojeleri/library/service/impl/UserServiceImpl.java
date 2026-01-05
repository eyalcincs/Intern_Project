package com.stajprojeleri.library.service.impl;

import java.beans.beancontext.BeanContext;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.format.MatchStrength;
import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.dto.DtoUserIU;
import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.repository.UserRepository;
import com.stajprojeleri.library.security.AuthRequest;
import com.stajprojeleri.library.security.AuthResponse;
import com.stajprojeleri.library.security.JWTUtil;
import com.stajprojeleri.library.service.IUserService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class UserServiceImpl implements IUserService {

    private final JWTUtil JWTUtil;

    private final AuthenticationProvider authenticationProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


    UserServiceImpl(AuthenticationProvider authenticationProvider, JWTUtil JWTUtil) {
        this.authenticationProvider = authenticationProvider;
        this.JWTUtil = JWTUtil;
    }
	
	
	
	@Override
	public DtoUser saveUser(DtoUserIU dtoUserIU) {
		DtoUser response = new DtoUser();
		User user = new User();
		
		BeanUtils.copyProperties(dtoUserIU,user);
		user.setPassword(passwordEncoder.encode(dtoUserIU.getPassword()));
		User dbuser = userRepository.save(user);
		
		BeanUtils.copyProperties(dbuser,response);
		
		return response;
		
	}
 

	@Override
	public AuthResponse login(AuthRequest request) {
		try {
			

			UsernamePasswordAuthenticationToken auth = 
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			
			authenticationProvider.authenticate(auth);
			
			Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
			String token = JWTUtil.generateToken(optionalUser.get());
			
			return new AuthResponse(token);
			
		} catch (Exception e) {
			 e.printStackTrace(); 
			    System.out.println("Kullanıcı adı veya şifre hatalı");
			    
			    
		}return null;
		
		
	}

	@Override
	public DtoUser findUserById(Integer id) {

	    Optional<User> optionalUser = userRepository.findById(id);

	    if (optionalUser.isEmpty()) {
	        return null; 
	    }

	    User user = optionalUser.get();

	    DtoUser dtoUser = new DtoUser();
	    BeanUtils.copyProperties(user, dtoUser);

	    return dtoUser;
	}

}
