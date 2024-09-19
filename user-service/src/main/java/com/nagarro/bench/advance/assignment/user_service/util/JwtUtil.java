package com.nagarro.bench.advance.assignment.user_service.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nagarro.bench.advance.assignment.user_service.dto.UserResponseDTO;
import com.nagarro.bench.advance.assignment.user_service.model.User;
import com.nagarro.bench.advance.assignment.user_service.service.UserService;

import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	@Autowired
	private UserService userService;
	
//	public String extractUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//	
//	public Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration) ;
//	}
	
//	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimResolver.apply(claims);
//		
//	}

//	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}
	
//	private boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
	
	public String generateToken(String email) {
		UserResponseDTO user = userService.getUserByEmail(email);
		List<String> userRoles = Arrays.asList(user.getRoles().split(","));
		Map<String, Object> claims = new HashMap<>();
		claims.put("firstname", user.getFirstName());
		claims.put("lastname", user.getLastName());
		claims.put("email", user.getEmail());
		claims.put("roles", userRoles);
		return createToken(claims, email);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	 public void validateToken(final String token) {
	        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	 }
	 
	 private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

}
