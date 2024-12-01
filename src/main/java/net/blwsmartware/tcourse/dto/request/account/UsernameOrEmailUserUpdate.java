package net.blwsmartware.tcourse.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UsernameOrEmailUserUpdate {

    @NotNull(message = "USERNAME_NOT_NULL")
    String username;

    String email;
}
