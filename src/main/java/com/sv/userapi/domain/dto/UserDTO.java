package com.sv.userapi.domain.dto;

import com.sv.userapi.domain.Phone;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserDTO(UUID id, String name, String email, String password, Set<Phone> phones, Boolean isActive) {
}
