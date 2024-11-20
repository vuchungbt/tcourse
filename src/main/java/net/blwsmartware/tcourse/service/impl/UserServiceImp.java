package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.dto.request.account.*;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.role.RoleResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.Role;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.UserMapper;
import net.blwsmartware.tcourse.repository.RoleRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.service.UserService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        Role roleUserDefault = roleRepository.findByName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.ROLE_NOT_EXISTED) );
        Set<Role> roleSet = Set.of(roleUserDefault);
        user.setRoles(roleSet);

        return userMapper.toUserResponse(user);
    }

    @Override
    public DataResponse<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<User> pageOfUsers = userRepository.findAll(pageable);
        List<User> userList = pageOfUsers.getContent();

        List<UserResponse> userResponses = userList.stream().map(userMapper::toUserResponse).toList();

        return DataResponseUtils.convertPageInfo(pageOfUsers,userResponses);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUserByID(long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND))
        );
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND))
        );
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND))
        );
    }


    @Override
    @Transactional
    public UserResponse updateUser(long id, UserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));

        userMapper.updateUser(request,old);

        return userMapper.toUserResponse(userRepository.save(old));
    }

    @Override
    @Transactional
    public UserResponse updateRoleOfUser(long id, RoleOfUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        var roles = roleRepository.findAllById(request.getRoleIds());
        old.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(old));
    }

    @Override
    @Transactional
    public UserResponse updateEmail(long id, EmailUserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        old.setEmail(request.getEmail());
        return userMapper.toUserResponse(userRepository.save(old));
    }


    @Override
    @Transactional
    public UserResponse updatePassword(long id, PasswordUserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        if(passwordEncoder.matches(old.getPassword(),passwordEncoder.encode(request.getOld_password())))
            old.setPassword(passwordEncoder.encode(request.getPassword()));
        else throw new AppRuntimeException(ErrorResponse.PASSWORD_INCORRECT);

        return userMapper.toUserResponse(userRepository.save(old));
    }

    @Override
    public UserResponse disableUser(long id, ActiveUserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        old.setActive(request.isStatus());
        return userMapper.toUserResponse(userRepository.save(old));
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
