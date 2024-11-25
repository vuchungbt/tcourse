package net.blwsmartware.tcourse.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdate {

    @NotNull(message = "NAME_NOT_NULL")
    String name;

    String title,email, description;

    String tel;

    LocalDate dob;

}
