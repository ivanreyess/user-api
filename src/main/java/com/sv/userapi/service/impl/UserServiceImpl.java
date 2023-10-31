package com.sv.userapi.service.impl;

import com.sv.userapi.domain.User;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.repository.UserRepository;
import com.sv.userapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        User user = toEntity(userDTO);
        user = userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = toEntity(userDTO);
        user = userRepository.save(user);
        return toDto(user);
    }

    @Override
    public Optional<UserDTO> partialUpdate(UserDTO userDTO) {
        log.debug("Request to partially update User : {}", userDTO);

        return userRepository
            .findById(userDTO.id())
            .map(existingUser -> {
//                UserMapper.partialUpdate(existingUser, UserDTO);

                return existingUser;
            })
            .map(userRepository::save)
            .map(User::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Request to get all UserS");
        return userRepository.findAll().stream().map(User::toDto).collect(Collectors.toCollection(LinkedList::new));
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
}
