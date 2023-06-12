package com.moneyTap.moneyTap.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moneyTap.moneyTap.models.Lead;
import com.moneyTap.moneyTap.models.UserCredentials;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@Log4j2
public class ServiceMoneyTap {
    private  String token;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }
    @Autowired
    private void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    //--------TokenAccess---------------------------------------------------------------------------------------
    public ObjectNode tokenAccess(UserCredentials userCredentials){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Encode username and password in Base64
        String credentials = userCredentials.getUsername() + ":" + userCredentials.getPassword();
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        String encodedCredentials = Base64.getEncoder().encodeToString(credentialsBytes);


        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        String url = "https://app.moneytap.com/oauth/token";
        ResponseEntity<ObjectNode> node= restTemplate.exchange(url, HttpMethod.POST,entity,ObjectNode.class);
        if(node.getBody().has("access_token")){
            token= node.getBody().get("access_token").toString();
            log.info(token);
        }
        return node.getBody();

    }
    //-----LEAD CREATION--------------------------------------------------------------------
    public ObjectNode leadCreation(Lead lead) throws JsonProcessingException {

        HttpHeaders headers= new HttpHeaders();


        String leadJson = objectMapper.writeValueAsString(lead);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer "+token);

        HttpEntity<Lead> entity= new HttpEntity<>(lead,headers);
        String url="https://dev.moneytap.com/v3//partner/buildprofile";
        ResponseEntity<ObjectNode> node=restTemplate.exchange(url,HttpMethod.POST,entity, ObjectNode.class);

        return node.getBody();
    }
    public ObjectNode getCustomerDetails(String customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customerId", customerId);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        String url = "https://dev.moneytap.com/v3/partner/customer/details";
        ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, ObjectNode.class);

        return response.getBody();
    }
}
