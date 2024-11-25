package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.blwsmartware.tcourse.entity.ImageStorage;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.ImageRepository;
import net.blwsmartware.tcourse.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StorageServiceImp implements StorageService {

    @Value("${config.file.upload-dir}")
    @NonFinal
    String uploadDir;

    ImageRepository imageRepository;

    @Override
    @Transactional
    public ImageStorage save(String name) {
        ImageStorage image = ImageStorage.builder()
                .name(name)
                .build();
        image = imageRepository.save(image) ;
        return image ;
    }

    @Override
    public String getNameByID(long id) {
        ImageStorage imageStorage = imageRepository.findById(id).orElseThrow(
                () -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
        return imageStorage.getName() ;
    }

    @Override
    public long getIdByName(String name) {
        ImageStorage imageStorage = imageRepository.findByName(name).orElseThrow(
                () -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
        return imageStorage.getId();
    }

    @Override
    public void deleteByID(long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public ImageStorage update(long id, String name) {
        ImageStorage old = imageRepository.findById(id).orElseThrow(
                () -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
        old.setName(name);
        imageRepository.save(old);
        return old;
    }

    @Override
    @Transactional
    public ImageStorage saveToStorage(MultipartFile img) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        ImageStorage image =  save(img.getOriginalFilename());

        System.out.printf(" prepairing: " + image);

        String coverPhotoFilename = image.getId() + "_" + image.getName();
        Path coverPhotoPath = uploadPath.resolve(coverPhotoFilename);

        Files.copy(img.getInputStream(), coverPhotoPath, StandardCopyOption.REPLACE_EXISTING);



        return image;
    }
}
