package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.dto.request.account.*;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.*;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.UserMapper;
import net.blwsmartware.tcourse.repository.CardRepository;
import net.blwsmartware.tcourse.repository.RoleRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.service.StorageService;
import net.blwsmartware.tcourse.service.UserService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    StorageService storageService;
    CardRepository cardRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role roleUserDefault = roleRepository.findByName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.ROLE_NOT_EXISTED) );
        Set<Role> roleSet = Set.of(roleUserDefault);
        user.setRoles(roleSet);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse addCard(Card request, long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND) );
        Card card = Card.builder()
                .name(request.getName())
                .CVV(request.getCVV())
                .address(request.getAddress())
                .expr(request.getExpr())
                .isDefault(request.isDefault())
                .number(request.getNumber())
                .build();
        card = cardRepository.save(card);
        Set<Card> set = u.getCards() ;
        set.add(card);
        return userMapper.toUserResponse(userRepository.save(u));
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
    public List<UserResponse> getAllByRoleName(Integer pageNumber, Integer pageSize, String sortBy, List<String> category) {
        if(pageNumber<0) {
            if(category.isEmpty() || category.getFirst().equals(PagePrepare.CATEGORY))
                return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();

            return userRepository.findByRoles_NameIn(category).stream().map(userMapper::toUserResponse).toList();
        }
        return null;
    }

    @Override
    public DataResponse<UserResponse> getAllPageByRoleName(Integer pageNumber, Integer pageSize, String sortBy, List<String> category) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<User> pageOfPost = userRepository.findByRoles_NameIn(category,pageable);
        List<User> userList = pageOfPost.getContent();
        List<UserResponse> userResponses = userList.stream().map(userMapper::toUserResponse).toList();
        return DataResponseUtils.convertPageInfo(pageOfPost,userResponses);
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
    public UserResponse updateUsernameEmail(long id, UsernameOrEmailUserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        old.setEmail(request.getEmail());
        old.setUsername(request.getUsername());
        return userMapper.toUserResponse(userRepository.save(old));
    }


    @Override
    @Transactional
    public UserResponse updatePassword(long id, PasswordUserUpdate request) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        if(passwordEncoder.matches(request.getOld_password() , old.getPassword())) {
            old.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(old);
            System.out.println("----saved new Pw");
        }

        else throw new AppRuntimeException(ErrorResponse.PASSWORD_INCORRECT);

        return userMapper.toUserResponse(userRepository.save(old));
    }

    @Override
    public UserResponse updatePhoto(long id, PhotoUserUpdate request) {

        User old = userRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));

        try {
            ImageStorage avatar2 = storageService.saveToStorage(request.getPhoto());
            if (request.getName().equals("avatar")) {
                old.setAvatar(avatar2.getId() + "");
            } else {
                old.setCoverPhoto(avatar2.getId() + "");
            }
            return userMapper.toUserResponse(userRepository.save(old));
        }
        catch (Exception e) {
            return null;
        }
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
