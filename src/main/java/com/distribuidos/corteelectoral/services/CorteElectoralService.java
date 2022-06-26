package com.distribuidos.corteelectoral.services;

import com.distribuidos.corteelectoral.controller.RestClient;
import com.distribuidos.corteelectoral.domain.CypherDecrypter;
import com.distribuidos.corteelectoral.domain.ResultsDTO;
import com.distribuidos.corteelectoral.domain.Shamir;
import com.distribuidos.corteelectoral.repositories.IResultsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CorteElectoralService {

    @Autowired
    IResultsRepository resultsRepository;
    private final Shamir shamir = new Shamir(3, 3);
    private BigInteger prime = new BigInteger("303929149119144853675827470350825567007");
    public static boolean canDecrypt = false;
    private static boolean firstTime = true;
//C:\private_key.pem
    private HashMap<String, HashMap<String,Integer>> conteoVotos = new HashMap<>();

    public ResponseEntity<List<ResultsDTO>> getVotes(String pass1, String pass2, String pass3) throws Exception {
        validatePasswordTribunal(pass1,pass2,pass3);
        if(canDecrypt)
        {
            RestClient restClient = new RestClient();
            ResponseEntity<String> call = restClient.callGetVotes();
            countVotes(call.getBody());
            return new ResponseEntity<>(resultsRepository.findAll(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    private void countVotes(String json) throws JSONException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeySpecException, InvalidKeyException {
        if(firstTime) {
            JSONArray jsonArray = new JSONArray(json);
            List<ResultsDTO> resultados = new ArrayList<>();


            for (int i = 0; i < jsonArray.length(); i++) {
                Object jsonnombre = jsonArray.getJSONObject(i).get("nombre_partido");

                Object jsonlista = jsonArray.getJSONObject(i).get("lista");
                String nombrePartido = CypherDecrypter.decrypt(jsonnombre.toString());

                String nombreLista = CypherDecrypter.decrypt(jsonlista.toString());


                if (conteoVotos.get(nombrePartido) != null) {
                    if (conteoVotos.get(nombrePartido).get(nombreLista) != null) {
                        Integer cantVotos = conteoVotos.get(nombrePartido).get(nombreLista) + 1;
                        conteoVotos.get(nombrePartido).replace(nombreLista, cantVotos);
                    } else {

                        conteoVotos.get(nombrePartido).put(nombreLista, 1);
                    }
                } else {

                    HashMap<String, Integer> votosLista = new HashMap<>();
                    votosLista.put(nombreLista, 1);
                    conteoVotos.put(nombrePartido, votosLista);
                }

            }
            ResultsDTO result = null;
            for (String partido : conteoVotos.keySet()) {
                for (String lista : conteoVotos.get(partido).keySet()) {
                    result = new ResultsDTO();
                    result.setNombre_lista(lista);
                    result.setNombre_partido(partido);
                    result.setCantidadVotos(conteoVotos.get(partido).get(lista));
                    result.setPartido_Lista(partido + "_" + lista);
                    saveVotesToDB(result);
                }
            }
            this.firstTime = false;

        }

}

private void saveVotesToDB(List<ResultsDTO> results){
        resultsRepository.saveAll(results);
        }
    private void saveVotesToDB(ResultsDTO result){
        resultsRepository.save(result);
    }

        private boolean validatePasswordTribunal(String pass1, String pass2, String pass3)
        {
            BigInteger bigIntegerPass1 = new BigInteger(pass1);
            BigInteger bigIntegerPass2 = new BigInteger(pass2);
            BigInteger bigIntegerPass3 = new BigInteger(pass3);
            Shamir.SecretShare secret0 = new Shamir.SecretShare(0,bigIntegerPass1);
            Shamir.SecretShare secret1 = new Shamir.SecretShare(1,bigIntegerPass2);
            Shamir.SecretShare secret2 = new Shamir.SecretShare(2,bigIntegerPass3);


            Shamir.SecretShare[] secretParts = new Shamir.SecretShare[3];
            secretParts[0] = secret0;
            secretParts[1] = secret1;
            secretParts[2] = secret2;
            String envVariable = new String(shamir.combine(secretParts,prime).toByteArray());

            String fileName = System.getenv(envVariable);

            if(fileName != null)
            {
                canDecrypt = true;
                CypherDecrypter.setFilePath(fileName);
            }
            else
            {
                canDecrypt = false;
            }

            return canDecrypt;
        }

        }

