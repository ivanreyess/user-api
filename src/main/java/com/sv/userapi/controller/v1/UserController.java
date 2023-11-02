package com.sv.userapi.controller.v1;

import com.sv.userapi.repository.UserRepository;
import com.sv.userapi.service.UserService;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.util.HeaderUtil;
import com.sv.userapi.util.ResponseUtil;
import com.sv.userapi.util.exception.BadRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import static com.sv.userapi.config.AppConstants.DEFAULT_SORT_DIRECTION;

/**
 * REST controller for managing {@link com.sv.userapi.domain.User}.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "User", description = "resource for user service")
@Slf4j
public class UserController {

    private static final String ENTITY_NAME = "user";

    @Value("${spring.application.name}")
    private String applicationName;

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /users} : Create a new user.
     *
     * @param userDTO the userDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if (userDTO.id() != null) {
            throw new BadRequest("A new user cannot already have an ID", applicationName);
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity
            .created(new URI("/api/users/" + result.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /users/:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        log.debug("REST request to update User : {}, {}", id, userDTO);
        if (userDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, userDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        UserDTO result = userService.update(userDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /users/:id} : Partial updates given fields of an existing user, field will ignore if it is null
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDTO> partialUpdateUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update User partially : {}, {}", id, userDTO);
        if (userDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, userDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!userService.exists(id)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<UserDTO> result = userService.partialUpdate(userDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userDTO.id().toString())
        );
    }

    /**
     * {@code GET  /users} : get all the userS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userS in body.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        log.debug("REST request to get all UserS");
        Page<UserDTO> userDTOS = userService.findAll(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(userDTOS.getContent());
    }

    /**
     * {@code GET  /users/:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    /**
     * {@code DELETE  /users/:id} : delete the "id" user.
     *
     * @param id the id of the userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
