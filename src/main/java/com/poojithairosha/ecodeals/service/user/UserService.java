package com.poojithairosha.ecodeals.service.user;

import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.dto.user.UserRespDto;
import com.poojithairosha.ecodeals.exception.UserNotFoundException;
import com.poojithairosha.ecodeals.mapper.UserMapper;
import com.poojithairosha.ecodeals.model.user.User;
import com.poojithairosha.ecodeals.repository.user.UserRepository;
import com.poojithairosha.ecodeals.repository.user.UserSpecification;
import com.poojithairosha.ecodeals.util.EntityPatchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserRespDto> getAll() {
        log.info("Fetching all users");
        List<UserRespDto> userList = userRepository.findAll().stream().map(UserMapper::toResponse).toList();
        log.info("Fetched all users of size: {}", userList.size());
        return userList;
    }

    public UserRespDto getById(Long id) {
        log.info("Fetching user by id: {}", id);
        UserRespDto userRespDto = userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        log.info("Fetched user by id: {}", id);
        return userRespDto;
    }

    public UserRespDto updateUser(Long id, UserReqDto userReqDto) {
        log.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    User updatedUser = UserMapper.updateEntity(existingUser, userReqDto);
                    userRepository.save(updatedUser);
                    log.info("Successfully updated user with id: {}", id);
                    return UserMapper.toResponse(updatedUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserRespDto partialUpdateUser(Long id, Map<String, Object> updates) {
        log.info("Partially updating user with id: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    updates.forEach((key, value) -> {
                        EntityPatchUtil.applyPatch(existingUser, key, value);
                    });
                    userRepository.save(existingUser);
                    log.info("Successfully partially updated user with id: {}", id);
                    return UserMapper.toResponse(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setDeleted(true);
            userRepository.save(user);
        }, () -> {
            throw new UserNotFoundException("User not found with id: " + id);
        });
        log.info("Successfully deleted user with id: {}", id);
    }

    public List<UserRespDto> searchUsers(String firstName, String lastName, String mobile, String email, String role) {
        log.info("Searching users with firstName: {}, lastName: {}, mobile: {}, email: {}", firstName, lastName, mobile, email);
        List<UserRespDto> users = userRepository.findAll(Specification.where(UserSpecification.hasFirstName(firstName))
                        .and(UserSpecification.hasLastName(lastName))
                        .and(UserSpecification.hasEmail(email))
                        .and(UserSpecification.hasMobile(mobile))
                        .and(UserSpecification.hasRole(role)))
                .stream()
                .map(UserMapper::toResponse)
                .toList();
        log.info("Found {} users", users.size());
        return users;
    }

    public Page<UserRespDto> getAllPaginated(int page, int size, String sortBy, String direction) {
        log.info("Fetching paginated users: page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<User> usersPage = userRepository.findAll(pageable);
        log.info("Fetched {} users on page {}", usersPage.getContent().size(), page);
        return usersPage.map(UserMapper::toResponse);
    }

    public Page<UserRespDto> getAllPaginatedWithSearch(int page, int size, String sortBy, String direction, String firstName, String lastName, String mobile, String email, String role) {
        log.info("Fetching paginated users with search: page={}, size={}, sortBy={}, direction={}, firstName={}, lastName={}, mobile={}, email={}, role={}", page, size, sortBy, direction, firstName, lastName, mobile, email, role);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<User> usersPage = userRepository.findAll(Specification.where(UserSpecification.hasFirstName(firstName))
                .and(UserSpecification.hasLastName(lastName))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasMobile(mobile))
                .and(UserSpecification.hasRole(role)), pageable);
        log.info("Fetched {} users on page with search", usersPage.getContent().size());
        return usersPage.map(UserMapper::toResponse);
    }
}
