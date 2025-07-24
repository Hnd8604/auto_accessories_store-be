package app.store.configuration;

import app.store.entity.Role;
import app.store.entity.User;
import app.store.repository.RoleRepository;
import app.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<Role>();
                Role roleAdmin = new Role();
                roleAdmin.setName("ADMIN");
                roleAdmin.setDescription("Admin role");
                roleAdmin = roleRepository.save(roleAdmin);

                Role roleUser = new Role();
                roleUser.setName("USER");
                roleUser.setDescription("User role");
                roleUser = roleRepository.save(roleUser);

                roles.add(roleAdmin);
                roles.add(roleUser);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
             userRepository.save(user);
             log.warn("admin user has been created with password: admin, please change it after login");
            }
        };
    }
}
