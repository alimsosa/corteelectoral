package com.distribuidos.corteelectoral.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotosController {


    @GetMapping("/getvotos")
    public ResponseEntity<String> getVotos() {
        RestClient restClient = new RestClient();
        ResponseEntity<String> result = restClient.callGetVotes();
        return result;
    }

}
