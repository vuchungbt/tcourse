package net.blwsmartware.tcourse.service;

import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // Ánh xạ email làm tên chính
        String email = (String) oAuth2User.getAttributes().get("email");
        String id = (String) oAuth2User.getAttributes().get("id");
        String name = (String) oAuth2User.getAttributes().get("name");
        System.out.println("==============email load:"+email);
        System.out.println("==============name load:"+name);
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth provider");
        }

        System.out.println("==============userService.existEmail(email):"+userService.existEmail(email));
        if(!userService.existEmail(email)){
            UserRequest user= new  UserRequest();
            user.setGgID(id);
            user.setEmail(email);
            user.setUsername(email);
            user.setName(name);
            log.info("========userRequest: {}",userRequest);
            userService.createUser(user);
        }


        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}

