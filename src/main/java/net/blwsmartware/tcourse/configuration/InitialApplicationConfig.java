package net.blwsmartware.tcourse.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.CategoryDefault;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Role;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.repository.CategoryRepository;
import net.blwsmartware.tcourse.repository.RoleRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InitialApplicationConfig {
    @Value("${config.admin.name}")
    @NonFinal
    String adminName;

    @Value("${config.admin.password}")
    @NonFinal
    String adminPassword;

    @Value("${config.admin.email}")
    @NonFinal
    String adminEmail;

    @Value("${config.admin.username}")
    @NonFinal
    String username;

    PasswordEncoder passwordEncoder  ;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository ,
                                        RoleRepository roleRepository ,
                                        CategoryRepository categoryRepository){
        log.info("********** Initializing application...");

        return args -> {
            log.info("********** adminEmail ... {}", adminEmail);
            log.info("********** adminPassword ... {}", adminPassword);
            log.info("********** adminName ... {}", adminName);
            log.info("********** username ... {}", username);

            if (userRepository.findByEmail(adminEmail).isEmpty()) {

                Role admin = roleRepository.save(Role.builder()
                                .name(PredefinedRole.ADMIN_ROLE)
                                .description("Admin")
                        .build());
                Role user = roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User default")
                        .build());
                Role teacher = roleRepository.save(Role.builder()
                        .name(PredefinedRole.TEACHER_ROLE)
                        .description("Techer default")
                        .build());

                Set<Role> roles = new HashSet<>();
                roles.add(user);
                //roles.add(teacher);
                roles.add(admin);

                User userAdmin = User.builder()
                        .email(adminEmail)
                        .name(adminName)
                        .username(username)
                        .password(passwordEncoder.encode(adminPassword))
                        .roles(roles)
                        .build();

                userRepository.save(userAdmin);
                log.info("********** Application init successfully for admin...");
            }
            log.info("********** Application initialization completed before...");
            log.info("********** Categories adding...");
            if(categoryRepository.findByDescription(CategoryDefault.DEFAULT).isEmpty()) {
                Category first = Category.builder().name("Tài chính").description(CategoryDefault.DEFAULT).build();
                Category twice = Category.builder().name("Công nghệ").description(CategoryDefault.DEFAULT).build();
                Category foure = Category.builder().name("Ngoại ngữ").description(CategoryDefault.DEFAULT).build();
                Category fire = Category.builder().name("Lập trình").description(CategoryDefault.DEFAULT).build();
                List<Category> list = List.of(fire,foure,twice,first);
                categoryRepository.saveAll(list);
            }
            log.info("********** Categories completed...");

        };
    }


}
