package com.pragma.users.infrastructure.configuration;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import com.nimbusds.jose.*;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pragma.users.domain.model.UserAuth;

@Service
public class JwtService {

	private final RSAKey rsaKey;
	private final JWSHeader header;

	public JwtService(RSAKey rsaKey) {
		this.rsaKey = rsaKey;
		this.header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).type(JOSEObjectType.JWT)
				.build();
	}

	public String generateToken(UserAuth user, Duration ttl) {
		Instant now = Instant.now();
		var uid = user.getId().toString();

		JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(uid)
                .claim("email", user.getEmail())
                .claim("roles", user.getRole())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(ttl)))
                .build();

		try {
			SignedJWT jwt = new SignedJWT(header, claims);
			JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());
			jwt.sign(signer);
			return jwt.serialize();
		} catch (Exception e) {
			throw new RuntimeException("Error firmando el JWT", e);
		}
	}
	// Endpoint para JWKS:
	public JWKSet jwkSetPublic() {
		return new JWKSet(rsaKey.toPublicJWK());
	}
}
