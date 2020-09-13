package com.educom.restclient.client;


import com.educom.restclient.model.Kurs;
import com.educom.restclient.ui.controller.LoginController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;

@Log4j2
public class KursClient implements HttpService<Kurs> {
    static final String URL_UPDATE_Kurs = "http://localhost:8082/api/kurs/updateKurs/{id}";
    static final String URL_FINDBYLASTNAME = "http://localhost:8082/api/kurs/findByLastName/{lastname}";
    static final String URL_FINDBYFIRSNAME = "http://localhost:8082/api/kurs/findByName/{firstname}";
    static final String URL_FINDBYEMAIL = "http://localhost:8082/api/kurs/findByEmail/{email}";
    static final String URL_DELETEBYID = "http://localhost:8082/api/kurs/deletebyId/{id}";
    static final String URL_ADDKurs = "http://localhost:8082/api/kurs/kurs";
    static final String URL_KursLIST = "http://localhost:8082/api/kurs/Kurslist";
    static final String URL_GETBYID = "http://localhost:8082/api/kurs/{id}";

    @Autowired
    RestTemplate restTemplate;
    private final WebClient webClient = WebClient.builder().build();
    @Override
    public String delete(Long id) {
        RestTemplate restTemplate=new RestTemplate();
        final String uri = URL_DELETEBYID;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(getHeader());
        System.out.println(id);
        restTemplate.exchange(uri, HttpMethod.DELETE,entity,String.class, params);

        return "removed";
    }


    @Override
    public Flux<Kurs> findByName(String name) {
        return webClient.get().uri(URL_FINDBYLASTNAME,name )
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Kurs.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + name + ". gefunden " + e.getMessage())).log();

    }


    public List<Kurs> findByLastName(String lastname) {
        final String uri = URL_FINDBYLASTNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("lastname", lastname);
        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(getHeader());
        ResponseEntity<Kurs[]> response = restTemplate.getForEntity(uri,
                Kurs[].class,
                urlParameters);

        return response.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }


    @Override
    public String update(Long id, Kurs kurs) {
        final String uri = URL_UPDATE_Kurs;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Kurs> entity = new HttpEntity<>(kurs, getHeader());
        try {
            restTemplate.exchange(uri, HttpMethod.PUT,entity, String.class,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

@Override
    public String add(Kurs kurs) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            final String baseUrl = URL_ADDKurs;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(kurs, getHeader());
        String response = restTemplate.postForObject(uri, entity, String.class);

        return response;
    }




    public List<Kurs> findByLehre(String lehre) {
        final String uri = URL_FINDBYLASTNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("lehre", lehre);
        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(getHeader());
        ResponseEntity<Kurs[]> response = restTemplate.getForEntity(uri,
                Kurs[].class,
                urlParameters);

        return response.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }
    public List<Kurs> getAllKurs() {
        final String uri = URL_KursLIST;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(getHeader());
        ResponseEntity<Kurs[]> response = restTemplate.exchange(URL_KursLIST,
                HttpMethod.GET, entity, Kurs[].class);
      //  ResponseEntity<List<Kurs>> kurslist = restTemplate.getForObject(uri, ResponseEntity.class);

        return  response.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }

    public List<Kurs> findByRaum(String raum) {
        final String uri = URL_FINDBYLASTNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("raum", raum);
        HttpEntity<Kurs> entity = new HttpEntity<Kurs>(getHeader());
        ResponseEntity<Kurs[]> response = restTemplate.getForEntity(uri,
                Kurs[].class,
                urlParameters);
        return response.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
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
