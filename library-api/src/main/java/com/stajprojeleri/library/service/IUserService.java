package com.stajprojeleri.library.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.dto.DtoUserIU;
import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.security.AuthRequest;
import com.stajprojeleri.library.security.AuthResponse;

import jakarta.validation.Valid;


public interface IUserService {
	
	public DtoUser saveUser(DtoUserIU dtoUserIU);
	
	public AuthResponse login(AuthRequest request);
	
	public DtoUser findUserById(Integer id);
	

}
