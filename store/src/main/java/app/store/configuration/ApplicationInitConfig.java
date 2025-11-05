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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            // Create roles if they don't exist
            Role roleAdmin = roleRepository.findByName("ADMIN").orElse(null);
            if (roleAdmin == null) {
                roleAdmin = new Role();
                roleAdmin.setName("ADMIN");
                roleAdmin.setDescription("Admin role");
                roleAdmin = roleRepository.save(roleAdmin);
                log.info("Created ADMIN role");
            }

            Role roleUser = roleRepository.findByName("USER").orElse(null);
            if (roleUser == null) {
                roleUser = new Role();
                roleUser.setName("USER");
                roleUser.setDescription("User role");
                roleUser = roleRepository.save(roleUser);
                log.info("Created USER role");
            }

            // Create admin user if it doesn't exist
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(roleAdmin) // Assign ADMIN role to admin user
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with password: admin, please change it after login");
            }
        };
    }
}
