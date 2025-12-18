package com.stajprojeleri.library.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.service.IUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(" /api/auth")

public class AuthController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public User saveUser(@RequestBody User user) {
		user.setId(null);
		return userService.saveUser(user);
	}
	
	@GetMapping(path = "/login")
	public User getUser( 
	@RequestParam  String username,
    @RequestParam  String password) {
		return userService.getUser(username,password);
	}


}
