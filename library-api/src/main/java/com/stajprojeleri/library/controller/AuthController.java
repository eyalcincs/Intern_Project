package com.stajprojeleri.library.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stajprojeleri.library.dto.DtoUser;
import com.stajprojeleri.library.dto.DtoUserIU;
import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.security.AuthRequest;
import com.stajprojeleri.library.security.AuthResponse;

import com.stajprojeleri.library.service.IUserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")

public class AuthController implements IAuthController {
	
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public DtoUser saveUser(@RequestBody DtoUserIU dtoUserIU) {

		return userService.saveUser(dtoUserIU);
	}
	
	
	
	@PostMapping(path = "/Login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		
		return userService.login(request);
		
	}


	@GetMapping(path="/{id}")
	@Override
	public DtoUser findUserById(@PathVariable(value="id")Integer id) {
		return userService.findUserById(id);
	}
}
