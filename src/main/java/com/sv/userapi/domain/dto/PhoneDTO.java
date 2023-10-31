package com.sv.userapi.domain.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PhoneDTO(UUID id, String number, String cityCode, String countryCode) {
}
