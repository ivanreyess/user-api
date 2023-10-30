package com.sv.userapi.domain.dto;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String password, Boolean isActive) {
}
