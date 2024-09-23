package com.adnanbk.ecommerce.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String upload(MultipartFile multipartFile);

    String upload(List<MultipartFile> images);

    String load(String filename);
}
