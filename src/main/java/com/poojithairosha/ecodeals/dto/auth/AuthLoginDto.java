package com.poojithairosha.ecodeals.dto.auth;

import lombok.Builder;

@Builder
public record AuthLoginDto(
        String email,
        String password
) {
}
