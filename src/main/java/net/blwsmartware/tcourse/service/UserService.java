package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.account.*;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.Card;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse addCard(Card request,long userId);
    DataResponse<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    List<UserResponse> getAllByRoleName(Integer pageNumber, Integer pageSize, String sortBy,List<String> category);
    DataResponse<UserResponse> getAllPageByRoleName(Integer pageNumber, Integer pageSize, String sortBy,List<String> category);
    List<UserResponse> getAll();
    UserResponse getUserByID(long id);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByUsername(String username);
    UserResponse updateUser(long id, UserUpdate request);
    UserResponse updateRoleOfUser(long id, RoleOfUpdate request);
    UserResponse updateUsernameEmail(long id, UsernameOrEmailUserUpdate request);
    UserResponse updatePassword(long id, PasswordUserUpdate request);
    UserResponse updatePhoto(long id, PhotoUserUpdate request);
    UserResponse disableUser(long id, ActiveUserUpdate request);
    void deleteUser(long id);

}
