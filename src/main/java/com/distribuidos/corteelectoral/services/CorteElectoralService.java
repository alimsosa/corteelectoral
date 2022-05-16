package com.distribuidos.corteelectoral.services;

import com.distribuidos.corteelectoral.controller.RestClient;
import com.distribuidos.corteelectoral.domain.ResultsDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorteElectoralService {

    public ResponseEntity<List<ResultsDTO>> getVotes() {
        RestClient restClient = new RestClient();
        ResponseEntity<String> call = restClient.callGetVotes();
        return countVotes(call.getBody());
    }


    private ResponseEntity<List<ResultsDTO>> countVotes(String json) {

        JSONArray jsonArray = new JSONArray(json);
        List<ResultsDTO> resultados = new ArrayList<>();
        ResultsDTO r1 = new ResultsDTO();

        for (int i = 0; i < jsonArray.length(); i++) {
            for (ResultsDTO r : resultados) {
                if (r.getNombre_partido() == jsonArray.getJSONObject(i).get("nombre_partido").toString()) {
                    r.setCantidadVotos(r.getCantidadVotos() + 1);
                    break;
                }
            }
            ResultsDTO res = new ResultsDTO();
            res.setNombre_partido(jsonArray.getJSONObject(i).get("nombre_partido").toString());
            res.setCantidadVotos(1);
            resultados.add(res);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);


    }

}
