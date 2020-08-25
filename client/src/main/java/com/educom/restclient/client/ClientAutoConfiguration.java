package com.educom.restclient.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    @Profile(ClientProfiles.SSE)
    StockClient webStockClient(WebClient webClient) {
        return new WebClientStockClient(webClient);
    }
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
}
}
