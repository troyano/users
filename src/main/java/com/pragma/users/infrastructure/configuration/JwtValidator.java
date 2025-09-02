package com.pragma.users.infrastructure.configuration;

import com.nimbusds.jose.JWSObject;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public String extractClaim(String token, String claim) throws java.text.ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        Object claimValue = jwsObject.getPayload().toJSONObject().get(claim);
        return claimValue != null ? claimValue.toString() : null;
    }
}

