package com.stajprojeleri.library.controller;

import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.security.AuthRequest;
import com.stajprojeleri.library.security.AuthResponse;

public interface IAuthController {
	
	public AuthResponse login(AuthRequest request);
	
	public DtoUser findUserById(Integer id);

}
