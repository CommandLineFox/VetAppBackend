package raf.aleksabuncic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.AuthorizationGoogleRequestDto;
import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;
import raf.aleksabuncic.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "API for managing authentication")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponseDto> login(@Valid @RequestBody AuthorizationRequestDto authorizationRequestDto) {
        return new ResponseEntity<>(authService.login(authorizationRequestDto), HttpStatus.OK);
    }

    @PostMapping("/google")
    public ResponseEntity<AuthorizationResponseDto> loginWithGoogle(@Valid @RequestBody AuthorizationGoogleRequestDto authorizationGoogleRequestDto) {
        return new ResponseEntity<>(authService.loginWithGoogle(authorizationGoogleRequestDto), HttpStatus.OK);
    }
}
