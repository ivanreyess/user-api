package com.sv.userapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PhoneDTO(@Schema(hidden = true) UUID id, String number, String cityCode, String countryCode) {
}
