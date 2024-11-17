package com.poojithairosha.ecodeals.controller.user;

import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.dto.user.UserRespDto;
import com.poojithairosha.ecodeals.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.poojithairosha.ecodeals.util.ApiUrlConstants.CommonUrlConstants.*;
import static com.poojithairosha.ecodeals.util.ApiUrlConstants.UserUrlConstants.USERS;

@Slf4j
@RestController
@RequestMapping(API_V_1 + USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @GetMapping
    public ResponseEntity<List<UserRespDto>> getAll() {
        log.info("Request to fetch all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Get user by id")
    @GetMapping(BY_ID)
    public ResponseEntity<UserRespDto> getById(@PathVariable Long id) {
        log.info("Request to fetch user by id: {}", id);
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Update user by id")
    @PutMapping(BY_ID)
    public ResponseEntity<UserRespDto> updateUser(@PathVariable Long id, @RequestBody UserReqDto userReqDto) {
        log.info("Request to update user with id: {}", id);
        UserRespDto updatedUser = userService.updateUser(id, userReqDto);
        log.info("Updated user with id: {}", id);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Partially update user by id")
    @PatchMapping(BY_ID)
    public ResponseEntity<UserRespDto> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        log.info("Request to partially update user with id: {}", id);
        UserRespDto updatedUser = userService.partialUpdateUser(id, updates);
        log.info("Partially updated user with id: {}", id);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping(BY_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Request to delete user with id: {}", id);
        userService.deleteUser(id);
        log.info("Deleted user with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search users by firstName, lastName, mobile, email, role")
    @GetMapping(SEARCH)
    public ResponseEntity<List<UserRespDto>> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role
    ) {
        log.info("Request to search users with firstName: {}, lastName: {}, mobile: {}, email: {}, role: {}", firstName, lastName, mobile, email, role);
        List<UserRespDto> users = userService.searchUsers(firstName, lastName, mobile, email, role);
        log.info("Found {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get all paginated and sorted users")
    @GetMapping(PAGINATED)
    public ResponseEntity<Page<UserRespDto>> getAllPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        log.info("Request to fetch paginated and sorted users: page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);
        Page<UserRespDto> result = userService.getAllPaginated(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get all paginated and sorted users with search")
    @GetMapping(PAGINATED + SEARCH)
    public ResponseEntity<Page<UserRespDto>> getAllPaginatedWithSearch(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role
    ) {
        log.info("Request to fetch paginated and sorted users with search: page={}, size={}, sortBy={}, direction={}, firstName={}, lastName={}, mobile={}, email={}, role={}", page, size, sortBy, direction, firstName, lastName, mobile, email, role);
        Page<UserRespDto> result = userService.getAllPaginatedWithSearch(page, size, sortBy, direction, firstName, lastName, mobile, email, role);
        return ResponseEntity.ok(result);
    }


}
