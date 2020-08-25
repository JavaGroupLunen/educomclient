package com.educom.restclient.client;

import com.educom.restclient.model.Vertrag;
import com.educom.restclient.ui.controller.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class VertragClient implements HttpService<Vertrag> {
    static final String URL_UPDATE_Vertrag = "http://localhost:8082/api/vertrag/updatevertrag/{id}";
    static final String URL_FINDBYLASTNAME = "http://localhost:8082/api/vertrag/findByLastName/{lastname}";
    static final String URL_FINDBYFIRSNAME = "http://localhost:8082/api/vertrag/findByName/{firstname}";
    static final String URL_FINDBYEMAIL = "http://localhost:8082/api/vertrag/findByEmail/{email}";
    static final String URL_DELETEBYID = "http://localhost:8082/api/vertrag/deletebyId/{id}";
    static final String URL_ADDVertrag = "http://localhost:8082/api/vertrag/vertrag";
    static final String URL_VertragLIST = "http://localhost:8082/api/vertrag/vertraglist";
    static final String URL_GETBYID = "http://localhost:8082/api/vertrag/{id}";

    @Autowired
    RestTemplate restTemplate;


    public VertragClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }


    public String delete(Long id) {
        final String uri = URL_DELETEBYID;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        System.out.println(id);
        restTemplate.delete(uri, params);
        return "removed";
    }

    @Override
    public List<Vertrag> findByName(String name) {
        return null;
    }


    public List<Vertrag> findByDatum(Date vertragDatum) {
        final String uri = URL_FINDBYFIRSNAME;
        Map<String, Date> urlParameters = new HashMap<>();
        urlParameters.put("vertragDatum", vertragDatum);
        ResponseEntity<Vertrag[]> entity = restTemplate.getForEntity(uri,
                Vertrag[].class,
                urlParameters);
        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }


    public String update(Long id, Vertrag vertrag) {
        final String uri = URL_UPDATE_Vertrag;
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, vertrag, params);
        return "updated";
    }


    public String add(Vertrag vertrag) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            final String baseUrl = URL_ADDVertrag;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(vertrag);
  //      ResponseEntity<Vertrag> result = restTemplate.postForEntity(uri, vertrag, String.class);
        //return result;
        return null;
    }



    public List<Vertrag> getAllVertrag() {
        final String uri = URL_VertragLIST;

        ResponseEntity<List<Vertrag>> Vertraglist = restTemplate.getForObject(uri,
                ResponseEntity.class
        );
        return  Vertraglist.getBody();
    }

    @Override
    public HttpHeaders getHeader() {

        HttpHeaders headers = new HttpHeaders();
        String authHeader = "Bearer " + LoginController.authenticationText;
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        return headers;
    }

}
