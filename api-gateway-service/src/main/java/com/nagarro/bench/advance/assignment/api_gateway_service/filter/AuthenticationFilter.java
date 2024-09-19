package com.nagarro.bench.advance.assignment.api_gateway_service.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.nagarro.bench.advance.assignment.api_gateway_service.exception.ForbiddenException;
import com.nagarro.bench.advance.assignment.api_gateway_service.exception.UnauthorizedException;
import com.nagarro.bench.advance.assignment.api_gateway_service.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

		@Autowired
	    private RouteValidator validator;

	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    public AuthenticationFilter() {
	        super(Config.class);
	    }


	    @Override
	    public GatewayFilter apply(Config config) {
	        return ((exchange, chain) -> {
	            if (validator.isSecured.test(exchange.getRequest())) {
	                // Check if the header contains the token
	                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
	                    throw new UnauthorizedException("Authorization header is missing");
	                }

	                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
	                if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                    authHeader = authHeader.substring(7);
	                }
	                

	                try {
	                    jwtUtil.validateToken(authHeader);

	                   
	                } catch (ExpiredJwtException e) {
	                    throw new UnauthorizedException("Token is expired");
	                } catch (Exception e) {
	                    throw new UnauthorizedException("Invalid token");
	                }
	                
	                List<String> roles = jwtUtil.extractRoles(authHeader);

                    // Check if roles include the required role
                    if (!requiresRole(exchange.getRequest(), roles))
                        throw new ForbiddenException("Invalid authorization role");
                    
	            }
	            return chain.filter(exchange);
	        });
	    }

	    
	    private boolean requiresRole(ServerHttpRequest request, List<String> roles) {
	        // Example logic for role-based access
	    	if(!(roles.contains("ADMIN") || roles.contains("USER")))
	    		return false;
	        if ((request.getURI().getPath().startsWith("/api/v1/products") ||
	        		request.getURI().getPath().startsWith("/api/v1/prices")) &&
	        		(request.getMethod() == HttpMethod.POST ||
	        		request.getMethod() == HttpMethod.PUT ||
	        		request.getMethod() == HttpMethod.DELETE)) {
	            return roles.contains("ADMIN");
	        }
	        return true;
	    }
	    
		public static class Config {

	    }


}
