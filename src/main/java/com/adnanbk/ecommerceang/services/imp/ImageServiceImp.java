package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.exceptions.CustomFileException;
import com.adnanbk.ecommerceang.services.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageServiceImp implements ImageService {


    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    private  Path root;

    @PostConstruct
    public void init() {
        createUploadingDirectory();
    }

    private void createUploadingDirectory() {
        try {
            root=Paths.get(uploadDir);
            if(!Files.isDirectory(Paths.get(uploadDir)))
            Files.createDirectory(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new CustomFileException("Could not initialize folder for upload!");
        }
    }

    @Async
    public CompletableFuture<String> CreateImage(MultipartFile image)  {

        if(image==null || !StringUtils.hasLength(image.getOriginalFilename()))
            throw new CustomFileException("you must upload  a valid image ");
        String fileName=image.getOriginalFilename().trim();
        if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png"))
            throw new CustomFileException("Image type not supported , we accept only jpg or png files");
        Path filePath = this.root.resolve(fileName);
        try {
            image.transferTo(filePath);
        } catch (IOException e) {
            throw new CustomFileException("we Could not write the file, please try again");
        }
        return  CompletableFuture.completedFuture(fileName);
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new CustomFileException("we Could not read the file, please try again");
            }
        } catch (MalformedURLException e) {
            throw new CustomFileException("Error: " + e.getMessage());
        }
    }

}
