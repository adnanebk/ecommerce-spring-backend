package com.adnanbk.ecommerce.services.imp;


import com.adnanbk.ecommerce.services.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacebookService implements SocialService {

    private final RestTemplate restTemplate;

    @Value("${facebook.clientId}")
    private String clientId;
    @Value("${facebook.clientSecret}")
    private String clientSecret;
    @Value("${facebook.inspectorUrl}")
    private String apiInspector;

    private  String access_token;

    @PostConstruct
    public void init(){
     this.access_token=clientId + '|' + clientSecret;
    }

    public boolean verify(String authToken) {
        return Optional.ofNullable(authToken)
                       .map(token->restTemplate.getForEntity(apiInspector + "?input_token={token}&access_token={app-token}"
                               , String.class, authToken, access_token).getStatusCode().is2xxSuccessful())
                       .orElse(false);

    }


}
