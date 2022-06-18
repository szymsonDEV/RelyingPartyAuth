package dev.szymson.example.RelyingPartyAuth.controller;

import dev.szymson.example.RelyingPartyAuth.domain.UserDTO;
import dev.szymson.example.RelyingPartyAuth.service.UserService;
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
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> startRegistration(@Validated @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.noContent().build();
    }
}
