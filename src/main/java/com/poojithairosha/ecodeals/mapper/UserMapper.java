package com.poojithairosha.ecodeals.mapper;

import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.dto.user.UserRespDto;
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
                .deleted(false)
                .build();
    }

    public static UserRespDto toResponse(User user) {
        return UserRespDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .deleted(user.isDeleted())
                .createdBy(user.getCreatedBy())
                .createdDate(user.getCreatedDate())
                .lastModifiedBy(user.getLastModifiedBy())
                .lastModifiedDate(user.getLastModifiedDate())
                .build();
    }

    public static User updateEntity(User existingUser, UserReqDto userReqDto) {
        existingUser.setFirstName(userReqDto.firstName());
        existingUser.setLastName(userReqDto.lastName());
        existingUser.setMobile(userReqDto.mobile());
        existingUser.setEmail(userReqDto.email());
        return existingUser;
    }

}
