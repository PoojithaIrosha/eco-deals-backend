package com.poojithairosha.ecodeals.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poojithairosha.ecodeals.util.JwtTokenUtil;
import io.fusionauth.jwt.JWTExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.getSubject(jwtToken);
                log.debug("JWT token found for user: {}", username);
            } catch (JWTExpiredException e) {
                log.error("JWT token expired: {}", jwtToken);
                sendErrorResponse(response, "JWT token expired", UNAUTHORIZED);
                return;
            }
        } else {
            log.debug("Authorization header not found or does not start with Bearer.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("Username found and authentication context is null, attempting authentication for user: {}", username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.debug("User details loaded for: {}", username);


            if (jwtTokenUtil.isTokenValid(jwtToken, userDetails)) {
                log.debug("JWT token is valid, setting authentication for user: {}", username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setType(URI.create("https://localhost:8080/api/v1/exception/jwt-expired"));
        problemDetail.setProperty("dateTime", Calendar.getInstance().getTime());
        response.setContentType("application/json");
        response.setStatus(status.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));
    }
}
