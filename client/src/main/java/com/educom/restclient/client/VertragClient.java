package com.educom.restclient.client;

import com.educom.restclient.client.service.HttpService;
import com.educom.restclient.model.Vertrag;
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
public class VertragClient implements HttpService<Vertrag> {
    static final String URL_UPDATE_Vertrag = "http://localhost:8082/api/vertrag/updatevertrag/{id}";
    static final String URL_ELTERNNAME = "http://localhost:8082/api/vertrag/findbyschuler/{name}";
    static final String URL_FINDBYFIRSNAME = "http://localhost:8082/api/vertrag/findByName/{firstname}";
    static final String URL_FINDBYEMAIL = "http://localhost:8082/api/vertrag/findByEmail/{email}";
    static final String URL_DELETEBYID = "http://localhost:8082/api/vertrag/deletebyId/{id}";
    static final String URL_ADDVertrag = "http://localhost:8082/api/vertrag/add";
    static final String URL_VertragLIST = "http://localhost:8082/api/vertrag/vertraglist";
    static final String URL_GETBYID = "http://localhost:8082/api/vertrag/{id}";

    @Autowired
    RestTemplate restTemplate;
    private WebClient webClient=WebClient.builder().build();

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
    public Flux<Vertrag> findByName(String name) {
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
    public List<Vertrag> findByEltern(String elternname) {
        final String uri = URL_ELTERNNAME;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("elternname", elternname);
        HttpEntity<Vertrag> entity = new HttpEntity<Vertrag>(getHeader());
        ResponseEntity<Vertrag[]> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, Vertrag[].class,urlParameters);
        return entity.getBody() != null ? Arrays.asList((response.getBody())) :  Collections.emptyList();

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
        HttpEntity<Vertrag> entity = new HttpEntity<Vertrag>(vertrag, getHeader());
        String response = restTemplate.postForObject(uri, entity, String.class);
        return response;

    }



    public List<Vertrag> getAllVertrag() {
        final String uri = URL_VertragLIST;
        restTemplate = new RestTemplate();
        HttpEntity<Vertrag> entity = new HttpEntity<Vertrag>(getHeader());
        ResponseEntity<Vertrag[]> response = restTemplate.exchange(URL_VertragLIST,
                HttpMethod.GET, entity, Vertrag[].class);
        return response.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }
    public Flux<Vertrag> getVertragById(Long id) {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/vertrag/getbyId/{id}",id)
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Vertrag.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + ". Received " + e.getMessage()));
    }
    public Flux<Vertrag> getVertragList() {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/vertrag/vertraglist")
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Vertrag.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + ". Received " + e.getMessage()));
    }
    public Flux<Vertrag> getVertragByEltern(String name) {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/vertrag/findbyelternname/{elternname}", name)
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Vertrag.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + name + ". Received " + e.getMessage()));
    }
    public Flux<Vertrag> getSchulerByName(String name) {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri(URL_ELTERNNAME, name)
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Vertrag.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + name + ". Received " + e.getMessage()));
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
