package com.adnanbk.ecommerce.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String upload(MultipartFile multipartFile);

    List<String> upload(List<MultipartFile> images);

}
