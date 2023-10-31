package com.sv.userapi.service;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.dto.PhoneDTO;
import com.sv.userapi.repository.PhoneRepository;
import com.sv.userapi.service.impl.PhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.sv.userapi.config.AppConstants.*;
import static com.sv.userapi.domain.Phone.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @InjectMocks
    PhoneServiceImpl phoneService;

    @Mock
    PhoneRepository phoneRepository;

    String countryCode = "506", cityCode = "1", number = "60434343";

    Phone phone;
    PhoneDTO phoneDTO, phoneDTO1;
    UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        phone = Phone.builder()
                .id(uuid)
                .countryCode(countryCode)
                .cityCode(cityCode)
                .number(number)
                .build();

        phoneDTO = PhoneDTO.builder()
                .id(uuid)
                .countryCode(countryCode)
                .cityCode(cityCode)
                .number(number)
                .build();

        phoneDTO1 = PhoneDTO.builder()
                .id(UUID.randomUUID())
                .countryCode(countryCode)
                .cityCode(cityCode)
                .number(number)
                .build();
    }

    @Test
    void save() {
        Phone savedPhone = toEntity(phoneDTO);
        given(phoneRepository.save(any(Phone.class))).willReturn(savedPhone);
        PhoneDTO result = phoneService.save(phoneDTO);
        assertEquals(uuid, result.id());
        assertEquals(countryCode, result.countryCode());
        assertEquals(number, result.number());
    }

    @Test
    void update() {
        Phone savedPhone = toEntity(phoneDTO);
        given(phoneRepository.save(any(Phone.class))).willReturn(savedPhone);
        PhoneDTO result = phoneService.update(phoneDTO);
        assertEquals(uuid, result.id());
        assertEquals(countryCode, result.countryCode());
        assertEquals(number, result.number());
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void findAll() {
        List<Phone> phones = Stream.of(phoneDTO, phoneDTO1).map(Phone::toEntity).toList();
        Page<Phone> phonePage = new PageImpl<>(phones);
        given(phoneRepository.findAll(any(Pageable.class))).willReturn(phonePage);
        Page<PhoneDTO> result = phoneService.findAll(Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_SIZE),DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void findOne() {
        Phone phone = toEntity(phoneDTO);
        given(phoneRepository.findById(uuid)).willReturn(Optional.of(phone));
        Optional<PhoneDTO> result = phoneService.findOne(uuid);
        assertTrue(result.isPresent());
    }

    @Test
    void delete() {
        doNothing().when(phoneRepository).deleteById(uuid);
        assertDoesNotThrow(() -> phoneService.delete(uuid));
    }
}