package com.sv.userapi.controller;

import com.sv.userapi.repository.PhoneRepository;
import com.sv.userapi.service.PhoneService;
import com.sv.userapi.domain.dto.PhoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.sv.userapi.config.AppConstants.*;

/**
 * REST controller for managing {@link com.sv.userapi.domain.Phone}.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class PhoneController {

    private static final String ENTITY_NAME = "phone";

    @Value("${spring.application.name}")
    private String applicationName;

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * {@code POST  /phones} : Create a new phone.
     *
     * @param phoneDTO the phoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneDTO, or with status {@code 400 (Bad Request)} if the phone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phones")
    public ResponseEntity<PhoneDTO> createPhone(@RequestBody PhoneDTO phoneDTO) throws URISyntaxException {
        log.debug("REST request to save Phone : {}", phoneDTO);
        if (phoneDTO.id() != null) {
            return ResponseEntity.badRequest().build();
        }
        PhoneDTO result = phoneService.save(phoneDTO);
        return ResponseEntity
            .created(new URI("/api/phones/" + result.id()))
            .body(result);
    }

    /**
     * {@code PUT  /phones/:id} : Updates an existing phone.
     *
     * @param id the id of the phoneDTO to save.
     * @param phoneDTO the phoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneDTO,
     * or with status {@code 400 (Bad Request)} if the phoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phones/{id}")
    public ResponseEntity<PhoneDTO> updatePhone(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PhoneDTO phoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Phone : {}, {}", id, phoneDTO);
        if (phoneDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, phoneDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!phoneService.exists(id)) {
            return ResponseEntity.badRequest().build();
        }

        PhoneDTO result = phoneService.update(phoneDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /phones/:id} : Partial updates given fields of an existing phone, field will ignore if it is null
     *
     * @param id the id of the phoneDTO to save.
     * @param phoneDTO the phoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneDTO,
     * or with status {@code 400 (Bad Request)} if the phoneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the phoneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the phoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhoneDTO> partialUpdatePhone(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PhoneDTO phoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Phone partially : {}, {}", id, phoneDTO);
        if (phoneDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, phoneDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!phoneService.exists(id)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<PhoneDTO> result = phoneService.partialUpdate(phoneDTO);

        return result.map(phoneUpdated -> ResponseEntity.ok().body(phoneUpdated)).orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @param pageNo the page number.
     * @param pageSize the page size.
     * @param sortBy property to be sorted.
     * @param sortDir direction: ASC or DESC of the sorted property.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping("/phones")
    public ResponseEntity<List<PhoneDTO>> getAllPhones(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                       @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                       @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        log.debug("REST request to get all Phones");
        Page<PhoneDTO> phoneDTOS = phoneService.findAll(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(phoneDTOS.getContent());
    }

    /**
     * {@code GET  /phones/:id} : get the "id" phone.
     *
     * @param id the id of the phoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phones/{id}")
    public ResponseEntity<PhoneDTO> getPhone(@PathVariable UUID id) {
        log.debug("REST request to get Phone : {}", id);
        Optional<PhoneDTO> phoneDTO = phoneService.findOne(id);
        return phoneDTO.map(phoneDTOFound -> ResponseEntity.ok().body(phoneDTOFound)).orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /phones/:id} : delete the "id" phone.
     *
     * @param id the id of the phoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phones/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable UUID id) {
        log.debug("REST request to delete Phone : {}", id);
        phoneService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
