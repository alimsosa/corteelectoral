package com.distribuidos.corteelectoral.services;

import com.distribuidos.corteelectoral.controller.RestClient;
import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.repositories.IResultsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorteElectoralService {

    @Autowired
    IResultsRepository resultsRepository;

    public ResponseEntity<List<ResultsDTO>> getVotes(String key) throws Exception {
        RestClient restClient = new RestClient();
        ResponseEntity<String> call = restClient.callGetVotes(key);
        return countVotes(call.getBody());
    }

    private ResponseEntity<List<ResultsDTO>> countVotes(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        List<ResultsDTO> resultados = new ArrayList<>();
        boolean flagVotoSumando = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            for (ResultsDTO r : resultados) {
                if (r.getNombre_partido().equals(jsonArray.getJSONObject(i).get("nombre_partido"))) {
                    r.setCantidadVotos(r.getCantidadVotos()+1);
                    flagVotoSumando = true;
                }
            }
            if (flagVotoSumando == false){
                ResultsDTO res = new ResultsDTO();
                res.setNombre_partido(jsonArray.getJSONObject(i).get("nombre_partido").toString());
                res.setCantidadVotos(1);
                resultados.add(res);
            }
        }
        saveVotesToDB(resultados);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    private void saveVotesToDB(List<ResultsDTO> results){
        resultsRepository.saveAll(results);
    }

}
