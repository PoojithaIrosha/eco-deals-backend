package com.poojithairosha.ecodeals.service.auth;

import com.poojithairosha.ecodeals.dto.auth.AuthLoginDto;
import com.poojithairosha.ecodeals.dto.auth.AuthRespDto;
import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.mapper.UserMapper;
import com.poojithairosha.ecodeals.model.user.User;
import com.poojithairosha.ecodeals.model.user.UserDetailsImpl;
import com.poojithairosha.ecodeals.repository.user.UserRepository;
import com.poojithairosha.ecodeals.repository.user.UserRoleRepository;
import com.poojithairosha.ecodeals.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthRespDto login(AuthLoginDto loginDto) {
        log.info("Authenticating user with email: {}", loginDto.email());
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        log.info("User {} successfully authenticated", authenticate.getName());

        UserDetailsImpl userDetails = userRepository.findByEmail(authenticate.getName())
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", authenticate.getName());
                    return new UsernameNotFoundException("User not found");
                });

        return getAuthRespDto(userDetails);
    }

    public AuthRespDto register(UserReqDto userReqDto) {
        log.info("Registering user with email: {}", userReqDto.email());
        User user = UserMapper.toEntity(userReqDto, passwordEncoder.encode(userReqDto.password()));
        userRoleRepository.findByName("User").ifPresentOrElse(user::setRole, () -> log.warn("Default role 'User' not found. User {} will have no role assigned.", userReqDto.email()));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with email: {}", savedUser.getEmail());

        UserDetailsImpl userDetails = new UserDetailsImpl(savedUser);
        return getAuthRespDto(userDetails);
    }

    private AuthRespDto getAuthRespDto(UserDetailsImpl userDetails) {
        try {
            log.info("Generating JWT token for user: {}", userDetails.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            log.info("Token generated successfully for user: {}", userDetails.getUsername());

            return AuthRespDto.builder()
                    .email(userDetails.getUsername())
                    .token(token)
                    .role(userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                    .build();
        } catch (IOException e) {
            log.error("Error occurred while generating token for user: {}", userDetails.getUsername(), e);
            throw new RuntimeException("Error occurred while generating token");
        }
    }

}
