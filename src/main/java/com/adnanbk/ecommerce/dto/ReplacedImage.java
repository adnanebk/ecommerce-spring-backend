package com.adnanbk.ecommerce.dto;

import org.springframework.web.multipart.MultipartFile;


public record ReplacedImage(String url, MultipartFile file) {
}
