package com.educom.restclient.client;

import com.educom.restclient.model.AuthenticationRequest;
import com.educom.restclient.model.AuthenticationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientAuthentication {
    private final String URL_LOGIN="http://localhost:8082/api/auth/signin";
    //Login olurken bunu kullanmaliyim
     public  String postLogin(AuthenticationRequest authenticationRequest) {
        // Request Header
        HttpHeaders headers = new HttpHeaders();
        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // POST Login
         URI uri = null;
         try {
             final String baseUrl = URL_LOGIN;
             uri = new URI(baseUrl);
         } catch (URISyntaxException e) {
             e.printStackTrace();
         }

         ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(uri, authenticationRequest, AuthenticationResponse.class);

        // ResponseEntity<AuthenticationRequest> response = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, requestEntity, AuthenticationRequest.class);
       //System.out.println(response);
      //response header handle yapip bi yere kaydetmeliyim
      //System.out.println(response.getBody().substring(7,139));
        return response.getBody().getJwt();
    }


}
