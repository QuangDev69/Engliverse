package com.spring.news.service.serviceImpl;

import com.spring.news.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements FileStorageService {

    @Override
    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            //    Path uploadDirectory = Paths.get("D:/eng/image"); // Hoặc cấu hình đường dẫn thông qua application.properties
            Path uploadDirectory = Paths.get("/Users/admin/Documents/eng/image/"); // Hoặc cấu hình đường dẫn thông qua application.properties
            Path filePath = uploadDirectory.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Triển khai các phương thức khác nếu cần
}
