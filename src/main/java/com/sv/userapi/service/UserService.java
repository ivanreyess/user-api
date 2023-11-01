package com.sv.userapi.service;

import com.sv.userapi.domain.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.sv.userapi.domain.User}.
 */
public interface UserService {
    /**
     * Save a user.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Updates a user.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Partially updates a user.
     *
     * @param userDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDTO> partialUpdate(UserDTO userDto);

    /**
     * Get all the user.
     *
     * @return the list of entities.
     */
    public Page<UserDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Get the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDTO> findOne(UUID id);

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    boolean exists(UUID id);
}
