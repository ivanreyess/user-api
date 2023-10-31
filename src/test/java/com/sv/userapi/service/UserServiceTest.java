package com.sv.userapi.service;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.User;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.repository.UserRepository;
import com.sv.userapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sv.userapi.domain.User.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    User user;
    UserDTO userDTO;
    String name = "Ivan Reyes", email = "ivan@mail.com", password ="123", countryCode = "506", cityCode = "1", number = "60434343";
    boolean active = true;

    Set<Phone> phones;

    UUID uuid, phoneUuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        phoneUuid = UUID.randomUUID();
        phones = Stream.of(
                new Phone[]{Phone.builder().id(uuid)
                .countryCode(countryCode)
                .cityCode(cityCode)
                .number(number).build()
        }).collect(Collectors.toSet());

        user = User.builder()
                .id(uuid)
                .name(name)
                .email(email)
                .password(password)
                .phones(phones)
                .active(active)
                .build();

        userDTO = UserDTO.builder()
                .id(uuid)
                .name(name)
                .email(email)
                .password(password)
                .phones(phones)
                .isActive(active)
                .build();

    }

    @Test
    void save() {
        User savedUser = toEntity(userDTO);
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        UserDTO result = userService.save(userDTO);
        assertEquals(uuid, result.id());
        assertEquals(name, result.name());
        assertEquals(email, result.email());
    }

    @Test
    void update() {
        User updatedUser = toEntity(userDTO);
        given(userRepository.save(any(User.class))).willReturn(updatedUser);
        UserDTO result = userService.update(userDTO);
        assertEquals(uuid, result.id());
        assertEquals(name, result.name());
        assertEquals(email, result.email());
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
        doNothing().when(userRepository).deleteById(uuid);
        assertDoesNotThrow(() -> userService.delete(uuid));
    }
}