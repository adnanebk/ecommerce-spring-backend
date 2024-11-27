package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImp implements FileService {


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

    public String upload(MultipartFile file) {
        String fileName = trimFileName(file.getOriginalFilename());
        Path filePath = this.root.resolve(fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new CustomFileException("we Could not write the file, please try again");
        }
        return fileName;
    }

    @Override
    public List<String> upload(List<MultipartFile> files) {
        return files.stream().map(this::upload).toList();
    }


    private String trimFileName(String fileName) {
        return Objects.requireNonNullElse(fileName, "")
                .replace(" ", "").trim();
    }

}
