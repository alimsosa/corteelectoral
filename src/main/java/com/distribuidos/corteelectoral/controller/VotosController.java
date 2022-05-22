package com.distribuidos.corteelectoral.controller;

import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.services.CorteElectoralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotosController {

    private final CorteElectoralService corteElectoralService = new CorteElectoralService();

    @GetMapping("api/getvotes/{key}")
    public ResponseEntity<List<ResultsDTO>> getVotes(@PathVariable(value = "key", required = true) String key) throws Exception {
        return corteElectoralService.getVotes(key);
    }
}