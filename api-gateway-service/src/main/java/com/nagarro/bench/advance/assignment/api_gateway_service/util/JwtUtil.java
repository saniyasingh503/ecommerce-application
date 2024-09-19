package com.nagarro.bench.advance.assignment.api_gateway_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	 public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	 	public List<String> extractRoles(String token) {
	        Claims claims = Jwts.parserBuilder()
	            .setSigningKey(getSignKey()).build()
	            .parseClaimsJws(token)
	            .getBody();
	        return claims.get("roles", List.class); 
	    }

	    public void validateToken(final String token) {
	        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	    }

	    private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

}
