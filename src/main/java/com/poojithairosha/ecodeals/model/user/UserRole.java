package com.poojithairosha.ecodeals.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
