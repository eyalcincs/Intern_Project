package com.stajprojeleri.library.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
    	
    	 String path = request.getServletPath();
    	    if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
    	        filterChain.doFilter(request, response);
    	        return;
    	    }
    	
    	String header;
    	String token;
    	String username;
    	
    	//Bearer ..... döner // Authorization header'ını al
    	header = request.getHeader("Authorization");
    	
    	// Header yoksa veya "Bearer " ile başlamıyorsa client a geri dön
    	if(header==null || !header.startsWith("Bearer ")) {
    		filterChain.doFilter(request, response);
    		return;
    	}
    	// Bearer kısmını at token kısmını al
    	 token = header.substring(7);
    	
    	try { 
    		
    		//Token içinden username çekeriz.
    		username = jwtUtil.getUsernameByToken(token);
    		
    		// Tokenı securitycontextholder a koyarız o yüzden buranın her istek geldiğinde boş olması gerekiyor.
    		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
    		
    		UserDetails	userDetails = userDetailsService.loadUserByUsername(username);  // Db usurname kontrolü yapar.
    			
    			if(userDetails != null && !jwtUtil.isTokenExpired(token)) {
    				// Kişiyi SecurityContext'e koyabiliriz artık.
    				
    				UsernamePasswordAuthenticationToken  authentication = 
    						new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    				
    				authentication.setDetails(userDetails);
    				
    				// Spring Security'ye: "bu kullanıcı giriş yapmış" deriz
    				// Aldığımız ve kontrolden geçirdiğimiz token burada securitycontextholder da set olursa bize yetki verir ve controllera geçeriz diğer türlü set etmezsek bize yetki vermez.
    				SecurityContextHolder.getContext().setAuthentication(authentication);   // Contexti doldurmamız gerekiyor
    			}
    		}
			
		} catch (ExpiredJwtException e) {
			System.out.println("Token süresi dolmuştur : " + e.getMessage());
		}
    	catch (Exception e) {
			System.out.println("Genel bir hata oluştu : " + e.getMessage());
		}

        // Request'i devam ettiririz (Controller'a gitmesine izin veririz)
        filterChain.doFilter(request, response);
    }
}

