package com.distribuidos.corteelectoral.services;

import com.distribuidos.corteelectoral.controller.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorteElectoralService {

    public ResponseEntity getVotos(){
        RestClient restClient = new RestClient();
        return restClient.callGetVotes();
    }

}
