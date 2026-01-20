package raf.aleksabuncic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;
import raf.aleksabuncic.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponseDto> login(@Valid @RequestBody AuthorizationRequestDto authorizationRequestDto) {
        return new ResponseEntity<>(authService.login(authorizationRequestDto), HttpStatus.OK);
    }
}
