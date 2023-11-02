package com.sv.userapi.service.impl;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.User;
import com.sv.userapi.domain.dto.PhoneDTO;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.repository.UserRepository;
import com.sv.userapi.service.PhoneService;
import com.sv.userapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sv.userapi.domain.User.toDto;
import static com.sv.userapi.domain.User.toEntity;

/**
 * Service Implementation for managing {@link com.sv.userapi.domain.User}.
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PhoneService phoneService;

    public UserServiceImpl(UserRepository userRepository, PhoneService phoneService) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;

    }

    @Override
    @Transactional
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        User user = toEntity(userDTO);
        user.setActive(true);
        user.setToken(UUID.randomUUID().toString());
        user = userRepository.save(user);
        user.setLastLogin(user.getCreatedDate());
        if (!userDTO.phones().isEmpty()) {
            List<PhoneDTO> savedPhones = phoneService.savePhones(userDTO.phones(), user);
            user.setPhones(savedPhones.stream().map(Phone::toEntity).collect(Collectors.toSet()));
        }
        return toDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = toEntity(userDTO);
        user.setLastLogin(Instant.now().toEpochMilli());
        user = userRepository.save(user);
        return toDto(user);
    }

    @Override
    public Optional<UserDTO> partialUpdate(UserDTO userDTO) {
        log.debug("Request to partially update User : {}", userDTO);
        return userRepository
            .findById(userDTO.id())
            .map(existingUser -> {
                if (userDTO.name() != null) existingUser.setName(userDTO.name());
                if (userDTO.email() != null) existingUser.setEmail(userDTO.email());
                if (userDTO.password() != null) existingUser.setPassword(userDTO.name());
                if (userDTO.isActive() != null) existingUser.setActive(userDTO.isActive());
                return existingUser;
            })
            .map(userRepository::save)
            .map(User::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.debug("Request to get all UserS");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(User::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(UUID id) {
        log.debug("Request to get user : {}", id);
        return userRepository.findById(id).map(User::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete user : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean exists(UUID id) {
        return userRepository.existsById(id);
    }
}
