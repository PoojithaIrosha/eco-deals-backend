package com.poojithairosha.ecodeals.dto.user;

import com.poojithairosha.ecodeals.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRespDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private UserRole role;
    private boolean active = true;
    private boolean deleted;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
