	package com.stajprojeleri.library.security;
	
	import java.security.Provider;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.authentication.AuthenticationProvider;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
	
	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

	    
			public static final String AUTHENTICATE = "/api/auth/login" ; 
	    	public static final String REGISTER = "/api/auth/register";
	    	
	    	@Autowired
	    	private AuthenticationProvider authenticationProvider;
	    	
	    	@Autowired
	    	private JWTFilter jwtFilter;
	    	
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	
	    	
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**","/api/books/**","/swagger-ui/**", "/v3/api-docs/**") // Bu urller request olursa eğer her şeye izin ver controllera geçebilir.
	            .permitAll()
	            .anyRequest()	// Bu url dışındaki herşeye de yetkilendirme yap diyoruz.
	            .authenticated())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authenticationProvider(authenticationProvider)
	            .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);
	        
	      
	             return http.build();
	    }
	    
	    
	 
	   
	}
