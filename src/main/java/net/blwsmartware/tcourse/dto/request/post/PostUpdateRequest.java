package net.blwsmartware.tcourse.dto.request.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUpdateRequest {
    int price , discount;
    String  name, description,
            title ,
            content ;

    List<String> skills ;
}
