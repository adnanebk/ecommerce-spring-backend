package com.adnanbk.ecommerceang.services.imp;


import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.services.SocialService;
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
    private String apiInspector = "https://graph.facebook.com/debug_token";


    public boolean verify(JwtResponse jwtResponse) {
        String token = jwtResponse.getToken();

        if (token != null) {

            String accessToken = clientId + '|' + clientSecret;
            ResponseEntity<String> response = restTemplate.getForEntity(apiInspector + "?input_token={token}&access_token={app-token}"
                    , String.class, token, accessToken);

            return response.getStatusCode().is2xxSuccessful();

        }
        return false;
    }

}
