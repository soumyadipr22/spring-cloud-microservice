package org.spring.microservice.auth.util;

import java.io.Serializable;
import java.util.Date;

import org.spring.microservice.auth.exception.JwtTokenMalformedException;
import org.spring.microservice.auth.exception.JwtTokenMissingException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtil implements Serializable{

	private static final long serialVersionUID = -3707690970562762668L;
    
    public String generateToken(String userName) {
		Claims claims = Jwts.claims().setSubject(userName);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET_KEY).compact();
	}  
    
    public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}
}
