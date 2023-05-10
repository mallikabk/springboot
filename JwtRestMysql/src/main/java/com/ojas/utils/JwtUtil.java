package com.ojas.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtUtil {
	public static final int EXPIRATION_IN_SECONDS = 120;

	private static final String JWT_SECRET = "Some$ecretKey";

	private Clock clock = DefaultClock.INSTANCE;

	@Value("${app.secrate}")
	private String secrate;

	public String generateJwtToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuer("Nagaraju")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
				.signWith(SignatureAlgorithm.HS512, secrate.getBytes()).compact();
	}
//	public String generateJwtToken(AuthenticationManager auth) {
//		return Jwts.builder().setSubject("Nagaraju").setIssuer("Nagaraju")
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
//				.signWith(SignatureAlgorithm.HS512, secrate.getBytes()).compact();
//	}


	public Claims getClaims(String token) {
		System.out.println("=======================jjjjjjjjjjjjjjjjj===============");
		// return
		// Jwts.parser().setSigningKey(secrate.getBytes()).parseClaimsJwt(token).getBody();
//		return Jwts.parser()
//				.setSigningKey(Base64.getEncoder().encode(secrate.getBytes()))
//				.parseClaimsJwt(token).getBody();
	//	return Jwts.parser().setSigningKey(JWT_SECRET.getBytes()).parseClaimsJws(token).getBody();
		

		return Jwts.parser()
				.setSigningKey(secrate.getBytes())
				.parseClaimsJws(token)
				.getBody();	
	}

	

	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();

	}

	public String getUsername(String token) {
		System.out.println("----------------->getUsername Called");
		 String subject = getClaims(token).getSubject();
		 System.out.println("oooooooooooosub----------->"+subject);
		return subject;

	}

	public boolean isValidToken(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));

	}

	public boolean validateToken(String token, String username) {
		String tokenUserName = getUsername(token);
		return (username.equals(tokenUserName) && !isValidToken(token));

	}
}
