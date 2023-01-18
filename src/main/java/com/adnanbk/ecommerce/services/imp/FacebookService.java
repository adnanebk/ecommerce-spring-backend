package com.adnanbk.ecommerce.services.imp;


import com.adnanbk.ecommerce.services.SocialService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "facebook")
@Getter
@Setter
public class FacebookService implements SocialService {

    private final RestTemplate restTemplate;

    private String clientId;

    private String clientSecret;

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
