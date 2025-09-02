package com.pragma.users.infrastructure.configuration;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private final RSAKey rsaKey;

    public JwtValidator(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public boolean validateToken(String token) {
        try {
            // Parse the token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verify the signature
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
            return signedJWT.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractClaim(String token, String claim) throws java.text.ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        Object claimValue = jwsObject.getPayload().toJSONObject().get(claim);
        return claimValue != null ? claimValue.toString() : null;
    }
}

