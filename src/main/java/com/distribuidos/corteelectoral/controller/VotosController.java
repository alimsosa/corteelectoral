package com.distribuidos.corteelectoral.controller;

import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.services.CorteElectoralService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class VotosController {

    private CorteElectoralService corteElectoralService;
    @GetMapping("api/getvotes")
    public ResponseEntity<List<ResultsDTO>> getVotes(@RequestParam String stringPass1, @RequestParam String stringPass2, @RequestParam String stringPass3) throws Exception {


        return corteElectoralService.getVotes(stringPass1, stringPass2, stringPass3);
    }

}