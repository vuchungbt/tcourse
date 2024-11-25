package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.request.post.SectionRequest;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    Section toSection(SectionRequest request);
//    void updateSection(SectionUpdate p, @MappingTarget Section ps);
//    SectionResponse toSectionResponse(Section p);
}
