package com.cornello.prototype.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class APIClient {

    private final RestTemplate restTemplate;
    
    public ResponseEntity<String> randomUser() {
        log.info("Starting APIClient.randomUser()");
        try {
            Map<String, String> pathVariables = new HashMap<>();
            pathVariables.put("oktaUserId","123456");
            UriComponentsBuilder builder =  UriComponentsBuilder.fromUriString("https://randomuser.me/api/");
            HttpHeaders headers = new HttpHeaders();
            // headers.set("Authorization", tokenType +" "+ token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            return restTemplate.exchange(builder.buildAndExpand(pathVariables).toUri(), HttpMethod.GET, requestEntity, String.class); 
        } catch (Exception e) {
            // log.error("resposnse:{}",e.g)
            e.printStackTrace();
        }
        return null;
    }
}
