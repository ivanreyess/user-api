package com.sv.userapi.domain.dto;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserDTO(UUID id, String name, String email, String password, Set<PhoneDTO> phones, Boolean isActive) {
}
