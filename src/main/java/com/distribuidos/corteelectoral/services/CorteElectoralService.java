package com.distribuidos.corteelectoral.services;

import com.distribuidos.corteelectoral.controller.RestClient;
import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.repositories.IResultsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public ResponseEntity<List<ResultsDTO>> getVotes() throws Exception {
        RestClient restClient = new RestClient();
        ResponseEntity<String> call = restClient.callGetVotes();
        countVotes(call.getBody());
        return new ResponseEntity<>(resultsRepository.findAll(), HttpStatus.OK);
    }

    private void countVotes(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        List<ResultsDTO> resultados = new ArrayList<>();
        boolean firstTime;

        for (int i = 0; i < jsonArray.length(); i++){
            Object jsonnombre = jsonArray.getJSONObject(i).get("nombre_partido");
            String jsonstring = jsonnombre.toString();
            firstTime = true;
            for (ResultsDTO res : resultados){
                if (jsonstring.equals(res.getNombre_partido())){
                    res.setCantidadVotos(res.getCantidadVotos()+1);
                    firstTime = false;
                }
            }
            if (firstTime) {
                ResultsDTO newResult = new ResultsDTO();
                newResult.setCantidadVotos(1);
                newResult.setNombre_partido(jsonstring);
                resultados.add(newResult);
            }

        }
        saveVotesToDB(resultados);
}

private void saveVotesToDB(List<ResultsDTO> results){
        resultsRepository.saveAll(results);
        }

        }
