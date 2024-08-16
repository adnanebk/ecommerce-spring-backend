package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageServiceImp implements FileService {


    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    private Path root;

    @PostConstruct
    public void init() {
        createUploadingDirectory();
    }

    private void createUploadingDirectory() {
        try {
            root = Paths.get(uploadDir);
                Files.createDirectories(root);
        } catch (IOException e) {
            throw new CustomFileException("Could not initialize folder for upload!");
        }
    }

    @Async
    public CompletableFuture<String> upload(MultipartFile image) {
        if(image==null)
            return CompletableFuture.completedFuture("");
        String fileName = trimImageName(image.getOriginalFilename());
        validateImageExtension(fileName);
        Path filePath = this.root.resolve(fileName);
        try {
            image.transferTo(filePath);
        } catch (IOException e) {
            throw new CustomFileException("we Could not write the file, please try again");
        }
        return CompletableFuture.completedFuture(fileName);
    }

    @Override
    public String load(String filename) {
        try {
            Path path = root.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return  path.toString();
            } else {
                throw new CustomFileException("file either not exists or not readable");
            }
        } catch (IOException e) {
            throw new CustomFileException("we Could not read the file, please try again");
        }
    }

    private void validateImageExtension(String fileName) {
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg"))
            throw new CustomFileException("Image type not supported , we accept only jpg or png files");
    }

    private String trimImageName(String imageName) {
        return Objects.requireNonNullElse(imageName, "")
                .replace(" ", "").trim();
    }

}
