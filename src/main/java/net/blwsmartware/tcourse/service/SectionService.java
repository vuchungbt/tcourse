package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.post.SectionRequest;
import net.blwsmartware.tcourse.entity.Section;

public interface SectionService {
    Section save(Section section);
    Section create(SectionRequest request);
    Section getByID(long id);
    void delete(long id);
   // DataResponse<Tag> getAll(Integer pageNumber, Integer pageSize, String sortBy);
}
