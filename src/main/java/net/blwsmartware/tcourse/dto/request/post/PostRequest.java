package net.blwsmartware.tcourse.dto.request.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    double price;
    String  name, description, thumbnail,coverPhoto,
            title , created_by,
            content ;

    List<String> skills ;
}
