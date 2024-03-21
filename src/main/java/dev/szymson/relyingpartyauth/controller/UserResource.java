package dev.szymson.relyingpartyauth.controller;

import dev.szymson.relyingpartyauth.domain.UserDTO;
import dev.szymson.relyingpartyauth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Szymon.Romanowski
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> startRegistration(@Validated @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.noContent().build();
    }
}
