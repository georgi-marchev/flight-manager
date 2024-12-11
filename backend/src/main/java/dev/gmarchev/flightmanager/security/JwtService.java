package dev.gmarchev.flightmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Service
public class JwtService {

	private final SecretKey jwtSecretKey;

	private final long jwtAccessDurationInMillis;

	private final long jwtRefreshDurationInMillis;

	@Autowired
	public JwtService(
			@Value("${jwt.secret}") String jwtSecret,
			@Value("${jwt.access.duration-seconds}") long jwtAccessDurationInSeconds,
			@Value("${jwt.refresh.duration-seconds}") long jwtRefreshDurationInSeconds) {

		this.jwtSecretKey = getSigningKey(jwtSecret);
		this.jwtAccessDurationInMillis = jwtAccessDurationInSeconds * 1000;
		this.jwtRefreshDurationInMillis = jwtRefreshDurationInSeconds * 1000;
	}

	private static SecretKey getSigningKey(String base64Secret) {

		byte[] keyBytes = Decoders.BASE64.decode(base64Secret);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(String username) {

		return generateToken(username, jwtAccessDurationInMillis);
	}

	private String generateToken(String username, long validityInMillis) {

		long currentTimeMillis = System.currentTimeMillis();

		return Jwts.builder()
				.claims(new HashMap<>())
				.subject(username)
				.issuedAt(new Date(currentTimeMillis))
				.expiration(new Date(currentTimeMillis + validityInMillis)) // 10 hours expiration
				.signWith(jwtSecretKey)
				.compact();
	}

	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		Claims claims = Jwts.parser()
				.verifyWith(jwtSecretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

		return claimsResolver.apply(claims);
	}

	public boolean isTokenExpired(String token) {

		return extractClaim(token, Claims::getExpiration)
				.before(new Date());
	}

	public String generateRefreshToken(String username) {

		return generateToken(username, jwtRefreshDurationInMillis);
	}
}
