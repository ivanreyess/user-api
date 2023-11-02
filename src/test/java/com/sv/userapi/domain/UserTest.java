package com.sv.userapi.domain;

import com.sv.userapi.domain.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;
    UserDTO userDTO;
    UUID uuid;


    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        user = User.builder()
                .id(uuid)
                .email("ivan@mail.com")
                .password("password")
                .phones(new HashSet<>())
                .active(true)
                .build();

        userDTO = UserDTO.builder()
                .id(uuid)
                .email("ivan@mail.com")
                .password("password")
                .phones(new HashSet<>())
                .isActive(true)
                .build();
    }

    @Test
    void toDto() {
        UserDTO result = User.toDto(user);
        assertEquals(uuid, result.id());
        assertEquals("ivan@mail.com", result.email());
        assertEquals("password", result.password());
        assertTrue(result.isActive());

    }

    @Test
    void toEntity() {
        User result = User.toEntity(userDTO);
        assertEquals(uuid, result.getId());
        assertEquals("ivan@mail.com", result.getEmail());
        assertEquals("password", result.getPassword());
        assertTrue(result.getActive());
    }
}