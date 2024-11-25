package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.account.*;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    DataResponse<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    List<UserResponse> getAllByRoleName(Integer pageNumber, Integer pageSize, String sortBy,List<String> category);
    List<UserResponse> getAll();
    UserResponse getUserByID(long id);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByUsername(String username);
    UserResponse updateUser(long id, UserUpdate request);
    UserResponse updateRoleOfUser(long id, RoleOfUpdate request);
    UserResponse updateEmail(long id, EmailUserUpdate request);
    UserResponse updatePassword(long id, PasswordUserUpdate request);
    UserResponse disableUser(long id, ActiveUserUpdate request);
    void deleteUser(long id);

}
