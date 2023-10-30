package com.sv.userapi.domain.dto;

import java.util.UUID;

public record PhoneDTO(UUID id, String number, String cityCode, String countryCode) {
}
