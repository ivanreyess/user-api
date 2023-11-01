package com.sv.userapi.controller;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.dto.PhoneDTO;
import com.sv.userapi.service.PhoneService;
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
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PhoneControllerTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();

    @Autowired
    private MockMvc restPhoneMockMvc;

    @MockBean
    private PhoneService phoneService;

    Phone phone;
    PhoneDTO phoneDTO, phoneDTO1;
    UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        phone = Phone.builder()
                .id(uuid)
                .countryCode(DEFAULT_COUNTRY_CODE)
                .cityCode(DEFAULT_CITY_CODE)
                .number(DEFAULT_NUMBER)
                .build();

        phoneDTO = PhoneDTO.builder()
                .id(uuid)
                .countryCode(DEFAULT_COUNTRY_CODE)
                .cityCode(DEFAULT_CITY_CODE)
                .number(DEFAULT_NUMBER)
                .build();

        phoneDTO1 = PhoneDTO.builder()
                .id(UUID.randomUUID())
                .countryCode(DEFAULT_COUNTRY_CODE)
                .cityCode(DEFAULT_CITY_CODE)
                .number(DEFAULT_NUMBER)
                .build();
    }

    @Test
    @DisplayName("POST " + ENTITY_API_URL + " CREATED")
    void createPhone() throws Exception{
        PhoneDTO phoneDTO1 = PhoneDTO.builder()
                                .countryCode(DEFAULT_COUNTRY_CODE)
                                .cityCode(DEFAULT_CITY_CODE)
                                .number(DEFAULT_NUMBER)
                                .build();

        given(phoneService.save(any())).willReturn(phoneDTO);
        restPhoneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneDTO1)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(equalToObject(DEFAULT_NUMBER)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countryCode").value(equalToObject(DEFAULT_COUNTRY_CODE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityCode").value(equalToObject(DEFAULT_CITY_CODE)));
    }

    @Test
    @DisplayName("PUT " + ENTITY_API_URL  + " OK")
    void updatePhone() throws Exception {
        given(phoneService.exists(uuid)).willReturn(true);
        given(phoneService.update(phoneDTO)).willReturn(phoneDTO);

        restPhoneMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, phoneDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(phoneDTO))
                )
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("PATCH " + ENTITY_API_URL  + " OK")
    void partialUpdatePhone() throws Exception {
        given(phoneService.exists(uuid)).willReturn(true);
        given(phoneService.partialUpdate(phoneDTO)).willReturn(Optional.ofNullable(phoneDTO));
        restPhoneMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, phoneDTO.id())
                                .contentType("application/merge-patch+json")
                                .content(TestUtil.convertObjectToJsonBytes(phoneDTO))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK")
    void getAllPhones() throws Exception {
        List<PhoneDTO> phones = Stream.of(phoneDTO).toList();
        Page<PhoneDTO> phoneDTOPage = new PageImpl<>(phones);
        given(phoneService.findAll(anyInt(), anyInt(), anyString(), anyString())).willReturn(phoneDTOPage);
        restPhoneMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(phoneDTO.id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)));
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK ")
    void getPhone() throws Exception{
        given(phoneService.findOne(uuid)).willReturn(Optional.ofNullable(phoneDTO));
        restPhoneMockMvc
                .perform(get(ENTITY_API_URL_ID, phoneDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(equalToObject(DEFAULT_NUMBER)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countryCode").value(equalToObject(DEFAULT_COUNTRY_CODE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityCode").value(equalToObject(DEFAULT_CITY_CODE)));
    }

    @Test
    @DisplayName("DELETE " + ENTITY_API_URL  + " no content ")
    void deletePhone() throws Exception{
        doNothing().when(phoneService).delete(uuid);
        restPhoneMockMvc.perform(delete(ENTITY_API_URL_ID, uuid))
                .andExpect(status().isNoContent());
    }
}