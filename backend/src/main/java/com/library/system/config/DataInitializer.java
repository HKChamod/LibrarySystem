package com.library.system.config;

import com.library.system.model.Book;
import com.library.system.model.Category;
import com.library.system.model.Role;
import com.library.system.model.Role.RoleType;
import com.library.system.model.User;
import com.library.system.repository.BookRepository;
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
            BookRepository bookRepository,
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

            // Init Books
            if (bookRepository.count() == 0) {
                Category fiction = categoryRepository.findByName("Fiction").orElse(null);
                Category nonFiction = categoryRepository.findByName("Non-Fiction").orElse(null);
                Category science = categoryRepository.findByName("Science").orElse(null);
                Category tech = categoryRepository.findByName("Technology").orElse(null);

                if (fiction != null) {
                    Book b1 = new Book();
                    b1.setTitle("The Great Gatsby");
                    b1.setAuthor("F. Scott Fitzgerald");
                    b1.setIsbn("9780743273565");
                    b1.setCategory(fiction);
                    bookRepository.save(b1);

                    Book b2 = new Book();
                    b2.setTitle("To Kill a Mockingbird");
                    b2.setAuthor("Harper Lee");
                    b2.setIsbn("9780061120084");
                    b2.setCategory(fiction);
                    bookRepository.save(b2);
                }

                if (science != null) {
                    Book b3 = new Book();
                    b3.setTitle("A Brief History of Time");
                    b3.setAuthor("Stephen Hawking");
                    b3.setIsbn("9780553380163");
                    b3.setCategory(science);
                    bookRepository.save(b3);
                }

                if (tech != null) {
                    Book b4 = new Book();
                    b4.setTitle("Clean Code");
                    b4.setAuthor("Robert C. Martin");
                    b4.setIsbn("9780132350884");
                    b4.setCategory(tech);
                    bookRepository.save(b4);

                    Book b5 = new Book();
                    b5.setTitle("The Pragmatic Programmer");
                    b5.setAuthor("Andrew Hunt");
                    b5.setIsbn("9780201616224");
                    b5.setCategory(tech);
                    bookRepository.save(b5);
                }
            }
        };
    }
}
