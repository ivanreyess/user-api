package com.sv.userapi.service.impl;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.User;
import com.sv.userapi.repository.PhoneRepository;
import com.sv.userapi.service.PhoneService;
import com.sv.userapi.domain.dto.PhoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    public List<PhoneDTO> savePhones(Set<PhoneDTO> phoneDTOS, User user) {
        phoneDTOS.stream().map(Phone::toEntity).forEach(phone -> {
            phone.setUser(user);
            phoneRepository.save(phone);
        });
        return this.findByUser(user);
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
                if (phoneDTO.countryCode() != null)existingPhone.setCountryCode(phoneDTO.countryCode());
                if (phoneDTO.cityCode() != null)existingPhone.setCityCode(phoneDTO.cityCode());
                if (phoneDTO.number() != null)existingPhone.setNumber(phoneDTO.number());
                return existingPhone;
            })
            .map(phoneRepository::save)
            .map(Phone::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhoneDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.debug("Request to get all Phones");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Phone> result = phoneRepository.findAll(pageable);
        return result.map(Phone::toDto);
    }

    @Override
    public List<PhoneDTO> findByUser(User user) {
        return phoneRepository.getPhoneByUser(user).stream().map(Phone::toDto).toList();
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

    @Override
    public boolean exists(UUID id) {
        return phoneRepository.existsById(id);
    }
}
