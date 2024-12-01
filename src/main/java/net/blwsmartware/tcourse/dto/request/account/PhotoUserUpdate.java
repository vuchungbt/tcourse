package net.blwsmartware.tcourse.dto.request.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotoUserUpdate {

    String name;

    MultipartFile photo;

}
