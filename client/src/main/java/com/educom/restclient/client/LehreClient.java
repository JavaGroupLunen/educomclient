package com.educom.restclient.client;

import com.educom.restclient.client.service.HttpService;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.ui.controller.LoginController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Log4j2
public class LehreClient implements HttpService<Lehre> {
    static final String URL_UPDATE_LEHRE = "http://localhost:8082/api/lehre/updatelehre/{id}";
    static final String URL_FINDBYLASTNAME="http://localhost:8082/api/lehre/findByLastName/{lastname}";
    static final String URL_FINDBYFIRSNAME="http://localhost:8082/api/lehre/findByName/{firstname}";
    static final String URL_FINDBYEMAIL= "http://localhost:8082/api/lehre//findByEmail/{email}";
    static final String URL_DELETEBYID="http://localhost:8082/api/lehre/deletebyId/{id}";
    static final String URL_ADD_LEHRE = "http://localhost:8082/api/lehre/add";

    @Autowired
    RestTemplate restTemplate;
    private final WebClient webClient = WebClient.builder().build();

    public LehreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


   public Flux<Lehre> findByEmail(String email){
       return webClient.get().uri(URL_FINDBYEMAIL,email )
               .header("Authorization", "Bearer " + LoginController.authenticationText)
               .retrieve()
               .bodyToFlux(Lehre.class)
               .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
               .doOnError(IOException.class,
                       e -> log.info(() -> "Closing stream for " + email + ". gefunden " + e.getMessage())).log();

   }
    public Flux<Lehre> getLehreList() {
        System.out.println(LoginController.authenticationText);
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/lehre/lehrelist")
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Lehre.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + ". Received " + e.getMessage()));
    }

    public Flux<Lehre> getLehreById(Long id) {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/lehre/getbyId/{id}", id)
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Lehre.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + id + ". Received " + e.getMessage()));
    }
    @Override
    public String update(Long id, Lehre lehre) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = URL_UPDATE_LEHRE;
        HttpEntity<Lehre> entity = new HttpEntity<>(lehre, getHeader());
        try {
        restTemplate.exchange(baseUrl, HttpMethod.PUT,entity, String.class,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public String add(Lehre lehre) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        try {
            final String baseUrl = URL_ADD_LEHRE;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpEntity<Lehre> entity = new HttpEntity<Lehre>(lehre, getHeader());
        String response = restTemplate.postForObject(uri, entity, String.class);
        return response;
    }



    @Override
    public String delete(Long id) {
        RestTemplate restTemplate=new RestTemplate();
        final String uri = URL_DELETEBYID;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        HttpEntity<Lehre> entity = new HttpEntity<>(getHeader());
        restTemplate.exchange(uri, HttpMethod.DELETE,entity,String.class, params);
        return "removed";
    }

    public Flux<Lehre> findByFirstName(String firstname){
        return webClient.get().uri(URL_FINDBYFIRSNAME,firstname )
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Lehre.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + firstname + ". gefunden " + e.getMessage())).log();

    }
    public Flux<Lehre> findByName(String name){
    return webClient.get().uri(URL_FINDBYLASTNAME,name )
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Lehre.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + name + ". gefunden " + e.getMessage())).log();

         }




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
