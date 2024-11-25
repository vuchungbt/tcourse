package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.entity.Tag;

public interface TagService {
    Tag create(String request);
    Tag getByID(String id);
    void delete(String id);
   // DataResponse<Tag> getAll(Integer pageNumber, Integer pageSize, String sortBy);
}
