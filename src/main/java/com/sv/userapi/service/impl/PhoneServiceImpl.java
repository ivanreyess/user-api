package com.sv.userapi.service.impl;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.repository.PhoneRepository;
import com.sv.userapi.service.PhoneService;
import com.sv.userapi.domain.dto.PhoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sv.userapi.domain.Phone.toDto;
import static com.sv.userapi.domain.Phone.toEntity;

/**
 * Service Implementation for managing {@link com.sv.userapi.domain.Phone}.
 */
@Service
@Transactional
@Slf4j
public class PhoneServiceImpl implements PhoneService {


    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public PhoneDTO save(PhoneDTO phoneDTO) {
        log.debug("Request to save Phone : {}", phoneDTO);
        Phone phone = toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        return toDto(phone);
    }

    @Override
    public PhoneDTO update(PhoneDTO phoneDTO) {
        log.debug("Request to update Phone : {}", phoneDTO);
        Phone phone = toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        return toDto(phone);
    }

    @Override
    public Optional<PhoneDTO> partialUpdate(PhoneDTO phoneDTO) {
        log.debug("Request to partially update Phone : {}", phoneDTO);

        return phoneRepository
            .findById(phoneDTO.id())
            .map(existingPhone -> {
//                phoneMapper.partialUpdate(existingPhone, phoneDTO);

                return existingPhone;
            })
            .map(phoneRepository::save)
            .map(Phone::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhoneDTO> findAll() {
        log.debug("Request to get all Phones");
        return phoneRepository.findAll().stream().map(Phone::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneDTO> findOne(UUID id) {
        log.debug("Request to get Phone : {}", id);
        return phoneRepository.findById(id).map(Phone::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Phone : {}", id);
        phoneRepository.deleteById(id);
    }
}
