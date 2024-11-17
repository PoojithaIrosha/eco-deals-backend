package com.poojithairosha.ecodeals.util;

import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.rsa.RSASigner;
import io.fusionauth.jwt.rsa.RSAVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private static final String ISSUER = "www.ecodeals.com";

    @Value("${jwt.private-key-location}")
    private Resource privateKeyLocation;

    @Value("${jwt.public-key-location}")
    private Resource publicKeyLocation;

    @Value("${jwt.expiration-in-days}")
    private Long expirationInDays;

    // Access Token Signer
    private Signer getTokenSigner() {
        try {
            return RSASigner.newSHA256Signer(new String(Files.readAllBytes(Paths.get(privateKeyLocation.getURI()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Access Token Verifier
    private Verifier getTokenVerifier() throws IOException {
        return RSAVerifier.newVerifier(Paths.get(publicKeyLocation.getURI()));
    }

    // Retrieve subject from the token
    public String getSubject(String token) throws JWTExpiredException, IOException {
        return JWT.getDecoder().decode(token, getTokenVerifier()).subject;
    }

    // Generate Access Token
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) throws IOException {
        JWT jwt = new JWT()
                .setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(userDetails.getUsername())
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusDays(expirationInDays))
                .addClaim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase())
                        .collect(Collectors.toList()));

        extraClaims.forEach(jwt::addClaim);
        return JWT.getEncoder().encode(jwt, getTokenSigner());
    }

    public String generateToken(UserDetails userDetails) throws IOException {
        return generateToken(userDetails, Map.of());
    }

    // Get Token Expiration
    public ZonedDateTime getExpiration(String token) throws IOException {
        return JWT.getDecoder().decode(token, getTokenVerifier()).expiration;
    }

    // Check for token validity
    public boolean isTokenValid(String token, UserDetails userDetails) throws IOException {
        JWT decode = JWT.getDecoder().decode(token, getTokenVerifier());
        boolean isNotExpired = !decode.isExpired();
        boolean usernameMatches = userDetails.getUsername().equals(getSubject(token));

        return usernameMatches && isNotExpired;
    }
}
