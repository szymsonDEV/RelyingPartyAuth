package dev.szymson.relyingpartyauth.controller;

import dev.szymson.relyingpartyauth.domain.UserDTO;
import dev.szymson.relyingpartyauth.service.UserService;
import dev.szymson.relyingpartyauth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Szymon.Romanowski
 **/
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authorize(@Validated @RequestBody UserDTO userDTO) {

        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(userDTO.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDTO getAccount(Authentication authentication) {
        return userService.getUser(authentication.getName());
    }

}
