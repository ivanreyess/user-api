package com.sv.userapi.service;

import com.sv.userapi.domain.dto.PhoneDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.sv.userapi.domain.Phone}.
 */
public interface PhoneService {
    /**
     * Save a phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
    PhoneDTO save(PhoneDTO phoneDTO);

    /**
     * Updates a phone.
     *
     * @param phoneDTO the entity to update.
     * @return the persisted entity.
     */
    PhoneDTO update(PhoneDTO phoneDTO);

    /**
     * Partially updates a phone.
     *
     * @param phoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhoneDTO> partialUpdate(PhoneDTO phoneDTO);

    /**
     * Get all the phones.
     *
     * @return the list of entities.
     */
    Page<PhoneDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Get the "id" phone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneDTO> findOne(UUID id);

    /**
     * Delete the "id" phone.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
