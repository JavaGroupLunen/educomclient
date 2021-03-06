package com.educom.restclient.client;


import com.educom.restclient.client.service.HttpService;
import com.educom.restclient.model.Schuler;
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
public class SchulerClient implements HttpService<Schuler> {
    static final String URL_UPDATE_schuler = "http://localhost:8082/api/schuler/updateschuler/{id}";
    static final String URL_FINDBYLASTNAME = "http://localhost:8082/api/schuler/findByLastName/{lastname}";
    static final String URL_FINDBYFIRSNAME = "http://localhost:8082/api/schuler/findByName/{firstname}";
    static final String URL_FINDBYEMAIL = "http://localhost:8082/api/schuler/findByEmail/{email}";
    static final String URL_DELETEBYID = "http://localhost:8082/api/schuler/deletebyId/{id}";
    static final String URL_ADDSCHULER = "http://localhost:8082/api/schuler/add";
    static final String URL_SCHULERLIST = "http://localhost:8082/api/schuler/schulerlist";
    static final String URL_GETBYID = "http://localhost:8082/api/schuler/{id}";
    static final String URL_ELTERNNAME = "http://localhost:8082/api/vertrag/findbyschuler/{name}";
    @Autowired
    RestTemplate restTemplate;
    private final WebClient webClient = WebClient.builder().build();

    @Override
    public String delete(Long id) {
        final String uri = URL_DELETEBYID;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        HttpEntity<Schuler> entity = new HttpEntity<Schuler>(getHeader());
        restTemplate.exchange(uri, HttpMethod.DELETE,entity,String.class, params);
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
    public Flux<Schuler> findByName(String lastname) {
        return webClient.get().uri(URL_FINDBYLASTNAME,lastname )
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Schuler.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + lastname + ". gefunden " + e.getMessage())).log();

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


//    public String updateschuler(Long id, Schuler schuler) {
//        final String uri = URL_UPDATE_schuler;
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", String.valueOf(id));
//        HttpEntity<Schuler> entity = new HttpEntity<Schuler>(schuler, getHeader());
//        restTemplate.exchange(uri, HttpMethod.PUT,entity,String.class, params);
//        return "updated";
//
//    }

    @Override
    public String update(Long id, Schuler schuler) {
        final String uri = URL_UPDATE_schuler;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        HttpEntity<Schuler> entity = new HttpEntity<Schuler>(schuler, getHeader());
        restTemplate.exchange(uri, HttpMethod.PUT,entity,String.class, params);
        return "updated";
    }
//    @Override
//    public String update(Long id, Schuler schuler) {
//        final String uri = URL_UPDATE_schuler;
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", String.valueOf(id));
//        HttpEntity<Schuler> entity = new HttpEntity<Schuler>(schuler, getHeader());
//        restTemplate.exchange(uri, HttpMethod.PUT,entity,String.class, params);
//        return "updated";
//
//    }

    @Override
    public String add(Schuler schuler) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        try {
            final String baseUrl = URL_ADDSCHULER;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpEntity<Schuler> entity = new HttpEntity<Schuler>(schuler, getHeader());
        String response = restTemplate.postForObject(uri, entity, String.class);
        return response;

    }
    public Flux<Schuler> getSchulerList() {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/schuler/schulerlist")
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Schuler.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + ". Received " + e.getMessage()));
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
