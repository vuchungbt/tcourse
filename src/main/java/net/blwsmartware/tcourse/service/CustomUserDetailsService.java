package net.blwsmartware.tcourse.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        log.info("Loading user by username: {}", username);
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
            log.info("Found user: {}", user);
            log.info("User roles: {}", user.getRoles());

            CustomUserDetails userDetails = new CustomUserDetails(user);
            log.info("Created CustomUserDetails with authorities: {}", userDetails.getAuthorities());

            return userDetails;
        } catch (Exception e) {
            log.error("Error loading user", e);
            throw new UsernameNotFoundException(">>>>>>Error loading user", e);
        }
    }

}

