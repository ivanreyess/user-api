package com.sv.userapi.controller;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.User;
import com.sv.userapi.domain.dto.PhoneDTO;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.service.PhoneService;
import com.sv.userapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSOWRD = "AAAAAAAAAA";
    private static final String UPDATED_PASSOWRD = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/v1/users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MockMvc restUserEMockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PhoneService phoneService;

    User user;
    UserDTO userDTO;
    Set<PhoneDTO> phoneDTOs;
    Set<Phone> phones;
    UUID uuid, phoneUuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        phoneUuid = UUID.randomUUID();
        phones = Stream.of(
                new Phone[]{Phone.builder()
                        .id(uuid)
                        .countryCode(DEFAULT_COUNTRY_CODE)
                        .cityCode(DEFAULT_CITY_CODE)
                        .number(DEFAULT_NUMBER).build()
                }).collect(Collectors.toSet());

        user = User.builder()
                .id(uuid)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_EMAIL)
                .phones(phones)
                .active(DEFAULT_ACTIVE)
                .build();

        userDTO = UserDTO.builder()
                .id(uuid)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_EMAIL)
                .phones(phones.stream().map(Phone::toDto).collect(Collectors.toSet()))
                .isActive(DEFAULT_ACTIVE)
                .build();
    }

    @Test
    @DisplayName("POST " + ENTITY_API_URL + " CREATED")
    void createUser() throws Exception {
        UserDTO userDTO1 = UserDTO.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_EMAIL)
                .phones(phones.stream().map(Phone::toDto).collect(Collectors.toSet()))
                .isActive(DEFAULT_ACTIVE)
                .build();
        given(userService.save(any())).willReturn(userDTO);
        given(phoneService.savePhones(any(), any())).willReturn(phones.stream().map(Phone::toDto).toList());
        restUserEMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO1)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("PUT " + ENTITY_API_URL  + " OK")
    void updateUser() throws Exception {

        given(userService.exists(uuid)).willReturn(true);
        given(userService.update(userDTO)).willReturn(userDTO);

        restUserEMockMvc
                .perform(put(ENTITY_API_URL_ID, userDTO.id())
                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH " + ENTITY_API_URL  + " OK")
    void partialUpdateUser() throws Exception {
        given(userService.exists(uuid)).willReturn(true);
        given(userService.partialUpdate(userDTO)).willReturn(Optional.ofNullable(userDTO));
        restUserEMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, userDTO.id())
                                .contentType("application/merge-patch+json")
                                .content(TestUtil.convertObjectToJsonBytes(userDTO))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK")
    void getAllUsers() throws Exception {
        List<UserDTO> userDTOs = Stream.of(userDTO).toList();
        Page<UserDTO> userDTOPage = new PageImpl<>(userDTOs);
        given(userService.findAll(anyInt(), anyInt(), anyString(), anyString())).willReturn(userDTOPage);
        restUserEMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userDTO.id().toString())));
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK ")
    void getUser() throws Exception {
        given(userService.findOne(uuid)).willReturn(Optional.ofNullable(userDTO));
        restUserEMockMvc
                .perform(get(ENTITY_API_URL_ID, userDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("DELETE " + ENTITY_API_URL  + " no content ")
    void deleteUser() throws Exception {
        doNothing().when(userService).delete(uuid);
        restUserEMockMvc.perform(delete(ENTITY_API_URL_ID, uuid))
                .andExpect(status().isNoContent());
    }
}