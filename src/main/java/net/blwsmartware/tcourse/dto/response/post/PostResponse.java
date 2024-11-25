package net.blwsmartware.tcourse.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.entity.Skill;
import net.blwsmartware.tcourse.entity.Tag;

import java.time.Instant;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {
    Long id;
    String  created_by,name,
            thumbnail , coverPhoto, price, discount,description,
            title ,
            content ;
    Instant createAt;

    Instant updateAt;

    Instant publishedAt;
    int status;
    Set<Skill> skills ;
    Set<Section> sections ;
    Set<Tag> tags ;
    Set<Category> categories ;

}
