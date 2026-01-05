package com.stajprojeleri.library.security;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.secret}") // secret = jwt.secret
	private String secret;
	
	@Value("${jwt.expiration-ms}") // expirationMs = jwt.expiration-ms
	private long expirationMs;
	
	
	// Token olışturukenToken kullancağımız Key i oluşturuyoruz.
	private Key getSignKey() {
	    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));  // secreti key e çeviriyoruz
	} 
	
	
	//TOKEN OLUŞTURMA
	public String generateToken(UserDetails userDetails) {
		
		Date now = new Date();											// Token üretilme ve ne zaman biteceğine dair hesaplama
		Date expiry = new Date(now.getTime() + expirationMs);
		
		return Jwts.builder()
		
				.setSubject(userDetails.getUsername()) 					// token'ın sahibi. Tokenı kimden üreteceğimizi seçeriz
                .setIssuedAt(now)     									// token ne zaman üretildiğini söyler
                .setExpiration(expiry)									// token ne zaman bitecek onu söyler
                .signWith(getSignKey(), SignatureAlgorithm.HS256) 		// Burada verdiğim key ve hs256 algoritmasından token oluşturmak istediğimi söylüyorum
                .compact(); 											// string token üretiriz
		

	}

    // Tokenı çözmek istersek bu metotu kullanırız						//Token içindeki bütün bilgileri okuruz
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) 							// imzayı bununla doğrularız
                .build()
                .parseClaimsJws(token) 									// token'ı aç/parse et
                .getBody();            									// içindeki claims'leri al
    }
    
    
    
    public String getUsernameByToken(String token) {					// Yukarıdaki metotda tokenı çözdük daha sonra çözdüğümüz bu token da usurname i almak istiyorsak bu metotu kullanabiliriz.
	    return extractAllClaims(token).getSubject();
	}
    
    public boolean isTokenExpired(String token) {
    	Date expiredDate = extractAllClaims(token).getExpiration();		// Çözülen metotdan Expirationu öğreniriz
    																	// Şuanki zaman bitiş süresinden küçük olduğu sürece burası true döner aksi halde tokenın süresi bitmiş demektir 
    	return new Date().before(expiredDate);
    }

}
