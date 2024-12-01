package net.blwsmartware.tcourse.dto.request.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordUpdate {

    String oldpwd;
    String enteroldpwd;
    String password;

}
