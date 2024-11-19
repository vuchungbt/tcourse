package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.role.RoleRequest;
import net.blwsmartware.tcourse.dto.request.role.RoleUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.role.RoleResponse;
import net.blwsmartware.tcourse.entity.Role;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.RoleMapper;
import net.blwsmartware.tcourse.repository.RoleRepository;
import net.blwsmartware.tcourse.service.RoleService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request){
        Role role = roleMapper.toRole(request);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public DataResponse<RoleResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Role> pageOfRole = roleRepository.findAll(pageable);
        List<Role> roleList = pageOfRole.getContent();
        List<RoleResponse> roleResponseList = roleList.stream().map(roleMapper::toRoleResponse).toList();

        return DataResponseUtils.convertPageInfo(pageOfRole,roleResponseList);
    }

    @Override
    public RoleResponse getRoleByID(int id) {
        return roleMapper.toRoleResponse(roleRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.ROLE_NOT_EXISTED))
        ) ;
    }
    @Override
    public RoleResponse getRoleByName(String name) {
        return roleMapper.toRoleResponse(roleRepository.findByName(name)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.ROLE_NOT_EXISTED))
        ) ;
    }
    @Override
    public RoleResponse updateRole(int id, RoleUpdate request) {
        Role old = roleRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.ROLE_NOT_EXISTED));
        roleMapper.updateRole(request, old);
        return roleMapper.toRoleResponse(roleRepository.save(old)) ;
    }
    @Override
    public void delete(int id){
        roleRepository.deleteById(id);
    }

}
