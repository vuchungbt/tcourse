package net.blwsmartware.tcourse.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ImageController {

    StorageService storageService;

    @Value("${config.file.upload-dir}")
    @NonFinal
    String uploadDir;

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable String id) {
        String pth = "default_image.jpg";
        long _id =0;
        try {
            _id =Long.parseLong(id) ;
            pth = id + "_"+ storageService.getNameByID(_id);
        }  catch (Exception ignored) {  }


        try {
            Path imagePath = Paths.get(uploadDir).resolve(pth).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (!resource.exists() ||
                    !resource.isReadable() || !isValidImageExtension(imagePath)) {
                // Sử dụng ảnh mặc định nếu không tìm thấy ảnh hoặc không đọc được
                Path defaultImagePath = Paths.get(uploadDir).resolve("default_image.jpg").normalize();
                resource = new UrlResource(defaultImagePath.toUri());
                if (!resource.exists() || !resource.isReadable()) {
                    // Trả về lỗi nếu ảnh mặc định cũng không tồn tại
                    return ResponseEntity.notFound().build();
                }
            }

            String contentType = Files.probeContentType(resource.getFile().toPath());

            // Nếu không xác định được kiểu MIME, mặc định là binary
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/stream/{id}")
    public ResponseEntity<byte[]> streamVideo(@PathVariable String id) {
        String pth = "";
        long _id =0;
        try {
            _id = Long.parseLong(id) ;
            pth = id + "_"+ storageService.getNameByID(_id);
            log.info("AAAa {}",pth);
        }  catch (Exception ignored) {  }
        try {
            Path videoPath = Paths.get(uploadDir + File.separator + pth);
            log.info("SSSSSS1 {}" ,videoPath.getFileName() );
            log.info("SSSSSS2 {}" ,videoPath );
            byte[] videoBytes = Files.readAllBytes(videoPath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "video/mp4");
            headers.setContentLength(videoBytes.length);
            return new ResponseEntity<>(videoBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    private boolean isValidImageExtension(Path imagePath) {
        String fileName = imagePath.getFileName().toString().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
                fileName.endsWith(".svg") || fileName.endsWith(".webp");
    }

}
