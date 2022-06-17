package com.cornello.prototype.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cornello.prototype.entity.FileEntity;
import com.cornello.prototype.model.RequestBodyTest;

public class Utility {
    
    public static Map<String, String> memoizeTable = new HashMap<>(); // O(1)

    private Utility() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDownloadUri(FileEntity fileEntity) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/files/")
                .path(fileEntity.getId())
                .toUriString();
    }

    public static String generateJWTToken(String subject, RequestBodyTest claim) {
        List< Map<String, String>> claims = new ArrayList<>();
        Map<String, String> claimMap = new HashMap<>();
        claimMap.put("numId", String.valueOf(claim.getNumeric()));
        claimMap.put("text", claim.getText());
        claims.add(claimMap);

        claimMap = new HashMap<>();
        claimMap.put("numId", String.valueOf(claim.getNumeric()+1));
        claimMap.put("text", claim.getText()+"2");
        claims.add(claimMap);
        
    	return JWT.create()
                .withSubject(subject)
                .withClaim("claim", claimMap)
                .withClaim("claims",claims)
                .sign(Algorithm.HMAC256(Base64.decodeBase64("MySecret".getBytes())));
    }

    public static String decodeJWT(String token) {
        return JWT.require(Algorithm.HMAC256(Base64.decodeBase64("MySecret".getBytes())))
                .build()
                .verify(token)
                .getClaim("claim").asString();
    }
}
