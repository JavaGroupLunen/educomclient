package com.educom.restclient.client;

import com.educom.restclient.model.Kurs;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.model.Schuler;
import com.educom.restclient.model.Vertrag;
import com.educom.restclient.ui.controller.LoginController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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


@Log4j2
public class WebClientStockClient implements StockClient {
    private final WebClient webClient;
    static final String URL_ELTERNNAME = "http://localhost:8082/api/vertrag/findbyschuler/{name}";
    public WebClientStockClient(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
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

    public Flux<Kurs> getKursList() {
        log.info("WebClientStockClient");
        return webClient.get()
                .uri("localhost:8082/api/kurs/kurslist")
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Kurs.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + ". Received " + e.getMessage()));
    }
    //
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
    @Override
    public Flux<Lehre>  delete(Lehre lehre) {
        log.info("WebClientStockClient");
        return webClient.delete()
                .uri("localhost:8082/api/lehre/deletebyId/{id}", lehre.getId())
                .header("Authorization", "Bearer " + LoginController.authenticationText)
                .retrieve()
                .bodyToFlux(Lehre.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(5))
                .doOnError(IOException.class,
                        e -> log.info(() -> "Closing stream for " + lehre + ". deleted " + e.getMessage())).log();



    }
    @Override
    public String saveLehre(Lehre lehre)  {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            final String baseUrl = "http://localhost:8082/api/lehre/lehre";
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();        }

        HttpEntity<Lehre> entity = new HttpEntity<Lehre>(lehre, getHeader());
        //String response = restTemplate.postForObject(uri, entity, String.class);
        String result = restTemplate.postForObject(uri, entity, String.class);
        return result;
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
