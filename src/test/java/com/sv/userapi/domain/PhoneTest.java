package com.sv.userapi.domain;

import com.sv.userapi.domain.dto.PhoneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    Phone phone;
    PhoneDTO phoneDTO;
    UUID uuid;
    @BeforeEach
    void setUp() {

        uuid = UUID.randomUUID();

        phone = Phone.builder()
                .id(uuid)
                .countryCode("506")
                .cityCode("1")
                .number("60434343")
                .build();

        phoneDTO = PhoneDTO.builder()
                .id(uuid)
                .countryCode("506")
                .cityCode("1")
                .number("60434343")
                .build();

    }

    @Test
    void toDto() {
        PhoneDTO result = Phone.toDto(phone);
        assertEquals(uuid, phone.getId());
        assertEquals("506", result.countryCode());
        assertEquals("1", result.cityCode());
        assertEquals("60434343", result.number());
    }

    @Test
    void toEntity() {
        Phone result = Phone.toEntity(phoneDTO);
        assertEquals(uuid, phone.getId());
        assertEquals("506", result.getCountryCode());
        assertEquals("1", result.getCityCode());
        assertEquals("60434343", result.getNumber());
    }
}