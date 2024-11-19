package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.dto.request.account.UserUpdate;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest request);
    void updateUser(UserUpdate userUpdate,@MappingTarget User user);
    UserResponse toUserResponse(User user);
}
