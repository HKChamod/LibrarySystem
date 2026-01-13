package com.library.system.config;

import com.library.system.model.Category;
import com.library.system.model.Role;
import com.library.system.model.Role.RoleType;
import com.library.system.model.User;
import com.library.system.repository.CategoryRepository;
import com.library.system.repository.RoleRepository;
import com.library.system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Init Roles
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(null, RoleType.ROLE_USER));
                roleRepository.save(new Role(null, RoleType.ROLE_LIBRARIAN));
                roleRepository.save(new Role(null, RoleType.ROLE_ADMIN));
            }

            // Init Categories
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(null, "Fiction", null));
                categoryRepository.save(new Category(null, "Non-Fiction", null));
                categoryRepository.save(new Category(null, "Science", null));
                categoryRepository.save(new Category(null, "Technology", null));
            }

            // Init Admin User
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User("admin", "admin@library.com", passwordEncoder.encode("admin123"));
                Set<Role> roles = new HashSet<>();
                Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).get();
                roles.add(adminRole);
                admin.setRoles(roles);
                userRepository.save(admin);
            }
        };
    }
}
