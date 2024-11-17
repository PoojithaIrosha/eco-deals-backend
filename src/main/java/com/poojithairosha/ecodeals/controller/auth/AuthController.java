package com.poojithairosha.ecodeals.controller.auth;

import com.poojithairosha.ecodeals.dto.auth.AuthLoginDto;
import com.poojithairosha.ecodeals.dto.auth.AuthRespDto;
import com.poojithairosha.ecodeals.dto.user.UserReqDto;
import com.poojithairosha.ecodeals.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.poojithairosha.ecodeals.util.ApiUrlConstants.AuthUrlConstants.*;
import static com.poojithairosha.ecodeals.util.ApiUrlConstants.CommonUrlConstants.API_V_1;

@Slf4j
@RestController
@RequestMapping(API_V_1 + AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login")
    @PostMapping(LOGIN)
    public ResponseEntity<AuthRespDto> login(@RequestBody AuthLoginDto loginDto) {
        log.info("Login attempt with email: {}", loginDto.email());
        AuthRespDto response = authService.login(loginDto);
        log.info("Login successful for email: {}", loginDto.email());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User register")
    @PostMapping(REGISTER)
    public ResponseEntity<AuthRespDto> register(@RequestBody UserReqDto registerDto) {
        log.info("Registration attempt for email: {}", registerDto.email());
        AuthRespDto response = authService.register(registerDto);
        log.info("Registration successful for email: {}", registerDto.email());
        return ResponseEntity.ok(response);
    }

}
