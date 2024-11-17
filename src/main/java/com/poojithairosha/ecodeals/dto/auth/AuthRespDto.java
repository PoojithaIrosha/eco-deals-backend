package com.poojithairosha.ecodeals.dto.auth;

import lombok.Builder;

@Builder
public record AuthRespDto(
        String token,
        String email,
        String role
) {
}
