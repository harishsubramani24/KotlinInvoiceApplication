package com.example.InvoiceGeneration;

import com.example.InvoiceGeneration.entity.User;
import com.example.InvoiceGeneration.entity.UserBuilder;
import com.example.InvoiceGeneration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User harish;
    private User patrick;

    @BeforeEach
    void setUp() {
        harish = UserBuilder.builder()
                .username("Harish")
                .password("harish")
                .email("harish@gmail.com")
                .creationDate(LocalDateTime.now())
                .build();

        patrick = UserBuilder.builder()
                .username("Patrick")
                .password("patrick")
                .email("patrick@gmail.com")
                .creationDate(LocalDateTime.now())
                .build();

        userRepository.save(harish);
        userRepository.save(patrick);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindByEmail() {
        assertTrue(userRepository.findByEmail("harish@gmail.com").isPresent());
        assertFalse(userRepository.findByEmail("nonexistent@example.com").isPresent());
    }

    @Test
    void testFindByUsername() {
        assertTrue(userRepository.findByUserName("Harish").isPresent());
        assertFalse(userRepository.findByUserName("nonexistentuser").isPresent());
    }

    @Test
    void testFindAll() {
        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
    }

    @Test
    void testFindById() {
        assertTrue(userRepository.findById(harish.getId()).isPresent());
        assertFalse(userRepository.findById(100L).isPresent());
    }

    @Test
    void testSaveUser() {
        User newUser = UserBuilder.builder()
                .username("NewUser")
                .password("newuser")
                .email("newuser@gmail.com")
                .creationDate(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
        assertTrue(userRepository.findById(newUser.getId()).isPresent());
    }

    @Test
    void testUpdateUser() {
        User userToUpdate = userRepository.findByUserName("Harish").orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setUserName("updatedUsername");
            userRepository.save(userToUpdate);
            assertEquals("updatedUsername", userRepository.findById(userToUpdate.getId()).orElse(null).getUserName());
        }
    }

    @Test
    void testDeleteUser() {
        User userToDelete = userRepository.findByUserName("Harish").orElse(null);
        if (userToDelete != null) {
            userRepository.deleteById(userToDelete.getId());
            assertFalse(userRepository.findById(userToDelete.getId()).isPresent());
        }
    }
}
