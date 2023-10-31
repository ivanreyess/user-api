package com.sv.userapi.service;

import com.sv.userapi.domain.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.sv.userapi.domain.User}.
 */
public interface UserService {
    /**
     * Save a userE.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Updates a userE.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Partially updates a userE.
     *
     * @param userDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDTO> partialUpdate(UserDTO userDto);

    /**
     * Get all the userES.
     *
     * @return the list of entities.
     */
    List<UserDTO> findAll();

    /**
     * Get the "id" userE.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDTO> findOne(UUID id);

    /**
     * Delete the "id" userE.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
