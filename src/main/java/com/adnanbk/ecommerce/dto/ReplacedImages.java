package com.adnanbk.ecommerce.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public record ReplacedImages(@NotNull  List<String> imageUrls, @NotNull List<MultipartFile> imageFiles) {
}
