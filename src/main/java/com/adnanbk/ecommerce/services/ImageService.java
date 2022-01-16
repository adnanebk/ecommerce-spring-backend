package com.adnanbk.ecommerce.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImageService {

    CompletableFuture<String> CreateImage(MultipartFile multipartFile);

    Resource load(String filename);
}
