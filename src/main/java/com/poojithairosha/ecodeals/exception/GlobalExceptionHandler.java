package com.poojithairosha.ecodeals.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.Calendar;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException e, WebRequest request) {
        ProblemDetail problemDetail
                = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setInstance(URI.create(request.getContextPath()));
        problemDetail.setProperty("timestamp", Calendar.getInstance().getTime());
        problemDetail.setProperty("errorType", "AUTHENTICATION_ERROR");
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        log.info("Access denied: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setInstance(URI.create(request.getContextPath()));
        problemDetail.setProperty("timestamp", Calendar.getInstance().getTime());
        problemDetail.setProperty("errorType", "ACCESS_DENIED");
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {
        log.info("Username not found: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setInstance(URI.create(request.getContextPath()));
        problemDetail.setProperty("timestamp", Calendar.getInstance().getTime());
        problemDetail.setProperty("errorType", "USERNAME_NOT_FOUND");
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        log.info("UserNotFoundException: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("User not found");
        problemDetail.setInstance(URI.create(request.getContextPath()));
        problemDetail.setProperty("timestamp", Calendar.getInstance().getTime());
        problemDetail.setProperty("errorType", "USER_NOT_FOUND");
        return problemDetail;
    }

}
