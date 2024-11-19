package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.role.RoleRequest;
import net.blwsmartware.tcourse.dto.request.role.RoleUpdate;
import net.blwsmartware.tcourse.dto.response.role.RoleResponse;
import net.blwsmartware.tcourse.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    void updateRole(RoleUpdate userUpdate, @MappingTarget Role newRole);
    RoleResponse toRoleResponse(Role role);

}
