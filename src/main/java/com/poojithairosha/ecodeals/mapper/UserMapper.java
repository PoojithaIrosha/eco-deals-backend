package com.poojithairosha.ecodeals.mapper;

import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.model.user.User;

public class UserMapper {

    public static User toEntity(UserReqDto userReqDto, String encodedPassword) {
        return User.builder()
                .firstName(userReqDto.firstName())
                .lastName(userReqDto.lastName())
                .mobile(userReqDto.mobile())
                .email(userReqDto.email())
                .password(encodedPassword)
                .active(true)
                .build();
    }

}
