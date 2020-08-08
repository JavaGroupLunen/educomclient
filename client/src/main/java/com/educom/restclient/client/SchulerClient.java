package com.educom.restclient.client;


import com.educom.restclient.model.Schuler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Log4j2
public class SchulerClient implements HttpService<Schuler> {
    static final String URL_UPDATE_schuler = "http://localhost:8082/api/schuler/updateschuler/{id}";
    static final String URL_FINDBYLASTNAME = "http://localhost:8082/api/schuler/findByLastName/{lastname}";
    static final String URL_FINDBYFIRSNAME = "http://localhost:8082/api/schuler/findByName/{firstname}";
    static final String URL_FINDBYEMAIL = "http://localhost:8082/api/schuler/findByEmail/{email}";
    static final String URL_DELETEBYID = "http://localhost:8082/api/schuler/deletebyId/{id}";
    static final String URL_ADDSCHULER = "http://localhost:8082/api/schuler/add";
    static final String URL_SCHULERLIST = "http://localhost:8082/api/schuler/schulerlist";
    static final String URL_GETBYID = "http://localhost:8082/api/schuler/{id}";
//    @Autowired
//    private final WebClient webClient;
    @Autowired
    RestTemplate restTemplate;


    public SchulerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    @Override
    public String delete(Long id) {
        final String uri = URL_DELETEBYID;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        System.out.println(id);
        restTemplate.delete(uri, params);
        return "removed";
    }

    public List<Schuler> findByEmail(String email) {
        final String uri = URL_FINDBYEMAIL;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("email", email);
        ResponseEntity<Schuler[]> entity = restTemplate.getForEntity(uri,
                Schuler[].class,
                urlParameters);
        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }

    @Override
    public List<Schuler> findByName(String firstname) {
        final String uri = URL_FINDBYFIRSNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("firstname", firstname);
        ResponseEntity<Schuler[]> entity = restTemplate.getForEntity(uri,
                Schuler[].class,
                urlParameters);
        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }

    public List<Schuler> findByLastName(String lastname) {

        final String uri = URL_FINDBYLASTNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("lastname", lastname);
        ResponseEntity<Schuler[]> entity = restTemplate.getForEntity(uri,
                Schuler[].class,
                urlParameters);
        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }


    public String updateschuler(Long id, Schuler schuler) {
        final String uri = URL_UPDATE_schuler;
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, schuler, params);
        return "updated";

    }

    @Override
    public String update(Long id, Schuler schuler) {
        final String uri = URL_UPDATE_schuler;
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, schuler, params);
        return "updated";

    }

    @Override
    public ResponseEntity<String> add(Schuler schuler) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            final String baseUrl = URL_ADDSCHULER;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(schuler);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, schuler, String.class);


        return result;
    }


}
