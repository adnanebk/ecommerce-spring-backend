package com.adnanbk.ecommerce;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Collections;
import java.util.Locale;


@SpringBootApplication
@EnableAsync
public class EcommerceAngApplication {


    public static void main(String[] args) {

        SpringApplication.run(EcommerceAngApplication.class, args);
    }


    @Bean
    public GoogleIdTokenVerifier googleverifier() {

        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("517151847301-i200k7o5tivqaitri8ri8j1obrjp0bd6.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("ValidationMessages");
        return source;
    }




}
