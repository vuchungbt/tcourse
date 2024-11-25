package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.post.SectionRequest;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.entity.Tag;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.SectionMapper;
import net.blwsmartware.tcourse.repository.SectionRepository;
import net.blwsmartware.tcourse.repository.TagRepository;
import net.blwsmartware.tcourse.service.SectionService;
import net.blwsmartware.tcourse.service.TagService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionServiceImpl implements SectionService {
    SectionRepository sectionRepository;
    SectionMapper sectionMapper;

    @Override
    public Section create(SectionRequest request) {

        return sectionRepository.save(sectionMapper.toSection(request));
    }

    @Override
    public Section getByID(long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USERNAME_NOT_NULL));
    }


    @Override
    public void delete(long id) {
            sectionRepository.deleteById(id);
    }

//    @Override
//    public DataResponse<Tag> getAll(Integer pageNumber, Integer pageSize, String sortBy) {
//        return null;
//    }
}
