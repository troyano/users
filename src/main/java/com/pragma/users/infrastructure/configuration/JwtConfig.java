package com.pragma.users.infrastructure.configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

@Configuration
public class JwtConfig {

	// 🔑 Bean que genera/pará RSA key
	@Bean
	public RSAKey rsaKey() throws NoSuchAlgorithmException {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(2048);
		KeyPair pair = gen.generateKeyPair();

		return new RSAKey.Builder((java.security.interfaces.RSAPublicKey) pair.getPublic())
				.privateKey(pair.getPrivate()).keyID(UUID.randomUUID().toString()).algorithm(JWSAlgorithm.RS256)
				.build();
	}

	@Bean
	public JWKSet jwkSet(RSAKey rsaKey) {
		return new JWKSet(rsaKey.toPublicJWK());
	}
}
