package com.distribuidos.corteelectoral.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Arrays;

public class RestClient {

    private static final String GET_VOTES = "http://localhost:8080/getvotes";

    static RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<String> callGetVotes(){
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
        ResponseEntity<String> result = restTemplate.exchange(GET_VOTES, HttpMethod.GET,entity,String.class);
        return result;
    }
}
