package com.adnanbk.ecommerce.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImageService {

    CompletableFuture<String> createImage(MultipartFile multipartFile);

    String load(String filename);
}
