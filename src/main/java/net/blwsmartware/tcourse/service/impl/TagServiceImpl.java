package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.role.RoleRequest;
import net.blwsmartware.tcourse.dto.request.role.RoleUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.role.RoleResponse;
import net.blwsmartware.tcourse.entity.Role;
import net.blwsmartware.tcourse.entity.Tag;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.RoleMapper;
import net.blwsmartware.tcourse.repository.RoleRepository;
import net.blwsmartware.tcourse.repository.TagRepository;
import net.blwsmartware.tcourse.service.RoleService;
import net.blwsmartware.tcourse.service.TagService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;


    @Override
    public Tag create(String request) {
        Tag tag = Tag.builder().name(request).build();
        return tagRepository.save(tag);
    }

    @Override
    public Tag getByID(String id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USERNAME_NOT_NULL));
    }


    @Override
    public void delete(String id) {
            tagRepository.deleteById(id);
    }

//    @Override
//    public DataResponse<Tag> getAll(Integer pageNumber, Integer pageSize, String sortBy) {
//        return null;
//    }
}
