package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.blwsmartware.tcourse.entity.Item;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.ItemRepository;
import net.blwsmartware.tcourse.service.ItemService;
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
public class ItemServiceImp implements ItemService {


    ItemRepository itemRepository;

    @Override
    @Transactional
    public Item save(Item item) {
        return  itemRepository.save(item) ;
    }

    @Override
    public Item getByID(long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
    }

    @Override
    public void deleteByID(long id) {
        itemRepository.deleteById(id);
    }
//
//    @Override
//    public ImageStorage update(long id, String name) {
//        ImageStorage old = imageRepository.findById(id).orElseThrow(
//                () -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
//        old.setName(name);
//        imageRepository.save(old);
//        return old;
//    }

}
