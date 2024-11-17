package com.poojithairosha.ecodeals.dto.user;

public record UserReqDto(
        String firstName,
        String lastName,
        String mobile,
        String email,
        String password
) {
}
