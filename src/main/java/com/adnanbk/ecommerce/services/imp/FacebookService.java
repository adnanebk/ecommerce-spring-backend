package com.adnanbk.ecommerce.services.imp;


import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.services.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FacebookService implements SocialService {

    private final RestTemplate restTemplate;

    @Value("${facebook.clientId}")
    private String clientId;
    @Value("${facebook.clientSecret}")
    private String clientSecret;
    @Value("${facebook.inspectorUrl}")
    private  String apiInspector;


    public boolean verify(JwtDto jwtDto) {
        String token = jwtDto.getToken();

        if (token != null) {

            String accessToken = clientId + '|' + clientSecret;
            ResponseEntity<String> response = restTemplate.getForEntity(apiInspector + "?input_token={token}&access_token={app-token}"
                    , String.class, token, accessToken);

            return response.getStatusCode().is2xxSuccessful();

        }
        return false;
    }

}
