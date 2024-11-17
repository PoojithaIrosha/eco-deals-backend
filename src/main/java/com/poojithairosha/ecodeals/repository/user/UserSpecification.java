package com.poojithairosha.ecodeals.repository.user;

import com.poojithairosha.ecodeals.model.user.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                firstName == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")),
                        "%" + firstName.toLowerCase() + "%"
                );
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                lastName == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")),
                        "%" + lastName.toLowerCase() + "%"
                );
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"
                );
    }

    public static Specification<User> hasMobile(String mobile) {
        return (root, query, criteriaBuilder) ->
                mobile == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("mobile")), mobile.toLowerCase() + "%");
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, criteriaBuilder) ->
                role == null ? null : criteriaBuilder.equal(
                        criteriaBuilder.lower(root.join("role").get("name")),
                        role.toLowerCase()
                );
    }
}