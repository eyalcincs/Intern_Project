package com.stajprojeleri.library.security;

import java.lang.classfile.ClassFile.Option;
import java.security.PublicKey;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



import com.stajprojeleri.library.entity.User;
import com.stajprojeleri.library.repository.UserRepository;

@Configuration
public class AppConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			// JWTFilter classında kullandığımız loadUserByUsername metodunu burada düzenleriz.
			@Override 
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				
			Optional<User> optional =	userRepository.findByUsername(username);
				
			if(optional.isPresent()) {
				return optional.get();
			}
				return null;
			}
		};
		
	}
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {

	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}


	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	}

}
