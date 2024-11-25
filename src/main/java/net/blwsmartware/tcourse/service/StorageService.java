package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.entity.ImageStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    ImageStorage save(String name);
    String getNameByID(long id);
    long getIdByName(String id);
    void deleteByID(long id);
    ImageStorage update(long id,String name);
    ImageStorage saveToStorage(MultipartFile img) throws IOException;
}
