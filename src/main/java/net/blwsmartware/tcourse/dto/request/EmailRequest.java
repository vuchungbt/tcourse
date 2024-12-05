package net.blwsmartware.tcourse.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    String to;
    String name;
    String subject;
    String content;

}

