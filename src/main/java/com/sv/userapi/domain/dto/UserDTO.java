package com.sv.userapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserDTO(@Schema(hidden = true) UUID id, String name, String email, String password, Set<PhoneDTO> phones, Boolean isActive, @Schema(hidden = true) String token, long lastLogin, long createdDate) {
}
