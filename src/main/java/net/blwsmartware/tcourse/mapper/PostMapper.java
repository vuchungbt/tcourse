package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateSection;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "skills", ignore = true)
    Post toPost(PostRequest request);
    void updatePost(PostUpdate post, @MappingTarget Post p);
    void updatePostSection(PostUpdateSection post, @MappingTarget Post p);
    @Mapping(target = "avgVote", ignore = true)
    PostResponse toPostResponse(Post post);
}
