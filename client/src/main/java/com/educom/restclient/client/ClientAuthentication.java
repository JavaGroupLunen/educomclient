package com.educom.restclient.client;

import com.educom.restclient.model.AuthenticationRequest;
import com.educom.restclient.model.AuthenticationResponse;
import com.educom.restclient.model.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ClientAuthentication {
    @Autowired
    RestTemplate restTemplate;
    static final String URL_USERLIST = "http://localhost:8082/api/auth/userlist";
    static final String URL_REGISTER = "http://localhost:8082/api/auth/register";
    private final String URL_LOGIN="http://localhost:8082/api/auth/signin";
    private final String URL_GETBYID="http://localhost:8082/api/auth/getbyId/{id}";
    private final String URL_DELETE="http://localhost:8082/api/auth/delete";


    public  String postLogin(AuthenticationRequest authenticationRequest) {
        // Request Header
        HttpHeaders headers = new HttpHeaders();
        // RestTemplate
         restTemplate = new RestTemplate();
        // POST Login
         URI uri = null;
         try {
             final String baseUrl = URL_LOGIN;
             uri = new URI(baseUrl);
         } catch (URISyntaxException e) {
             e.printStackTrace();
         }
         ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(uri, authenticationRequest, AuthenticationResponse.class);
        return response.getBody().getJwt();
    }



    public String delete(Long id) {
        restTemplate=new RestTemplate();
        final String uri =URL_DELETE ;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        System.out.println(id);
        restTemplate.delete(uri,String.class, params);
        return "removed";
    }

//    @Override
//    public String update(Long id, Kurs kurs) {
//        final String uri = URL_UPDATE_Kurs;
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", String.valueOf(id));
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.put(uri, kurs, params);
//        return "updated";
//    }


    public String add(SignupRequest signupRequest) {
         restTemplate = new RestTemplate();

        URI uri = null;
        try {
            final String baseUrl = URL_REGISTER;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

       // HttpEntity<Kurs> entity = new HttpEntity<Kurs>(kurs, getHeader());
       ResponseEntity<SignupRequest> response = restTemplate.postForEntity(uri, signupRequest,SignupRequest.class);

        return response.getBody().toString();
    }

}
