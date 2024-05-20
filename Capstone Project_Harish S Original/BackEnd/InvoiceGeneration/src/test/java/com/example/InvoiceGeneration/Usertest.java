package com.example.InvoiceGeneration;

import com.example.InvoiceGeneration.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class Usertest {

    @InjectMocks
    private User user;

    @BeforeEach
    public void setUp() {
        user.setId(1L);
        user.setUserName("Harish");
        user.setPassword("Harish7");
        user.setEmail("harish@gmail.com");
        user.setCreationDate(LocalDateTime.now());
    }

    @Test
    public void testGetId() {
        assertEquals(1L, user.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("Harish", user.getUserName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("Harish7", user.getPassword());
    }

    @Test
    public void testGetEmail() {
        assertEquals("harish@gmail.com", user.getEmail());
    }

    @Test
    public void testGetCreationDate() {
        assertEquals(LocalDateTime.now().getDayOfMonth(), user.getCreationDate().getDayOfMonth());
    }

    @Test
    public void testSetId() {
        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
    public void testSetUsername() {
        user.setUserName("Patrick");
        assertEquals("Patrick", user.getUserName());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("Password");
        assertEquals("Password", user.getPassword());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("patrickwilson@gmail.com");
        assertEquals("patrickwilson@gmail.com", user.getEmail());
    }

    @Test
    public void testSetCreationDate() {
        LocalDateTime newCreationDate = LocalDateTime.now().plusDays(1);
        user.setCreationDate(newCreationDate);
        assertEquals(newCreationDate.getDayOfMonth(), user.getCreationDate().getDayOfMonth());
    }
}
