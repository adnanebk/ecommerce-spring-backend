package com.adnanbk.ecommerce.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface FileService {

    CompletableFuture<String> upload(MultipartFile multipartFile);

    String load(String filename);
}
