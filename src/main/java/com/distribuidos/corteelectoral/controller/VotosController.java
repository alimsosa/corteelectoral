package com.distribuidos.corteelectoral.controller;

import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.services.CorteElectoralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotosController {

    private final CorteElectoralService corteElectoralService;

    @GetMapping("/getvotos")
    public ResponseEntity<List<ResultsDTO>> getVotos() {
        return corteElectoralService.getVotos();
    }

}
