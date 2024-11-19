package net.blwsmartware.tcourse.dto.request.post;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {

    @NotNull(message = "NAME_NOT_NULL")
    String name;

    String description;
    String type;
    String source;
    String thumbnail;
    LocalTime time;
    int itemOrder;

}
