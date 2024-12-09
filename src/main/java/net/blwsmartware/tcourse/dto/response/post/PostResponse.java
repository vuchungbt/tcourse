package net.blwsmartware.tcourse.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.entity.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {
    Long id;
    String  created_by,name,
            thumbnail , coverPhoto, description,
            title ,
            content ;
    Instant createAt;

    Instant updateAt;
    int price, discount,discountPercent,finalPrice;
    double avgVote;
    Instant publishedAt;
    int status;
    Set<Skill> skills ;
    List<Section> sections ;
    Set<Category> categories ;
    List<Comment> comments ;
    Set<Discount> discounts ;
    Set<Vote> votes ;
}
