package raf.aleksabuncic.service.implementation;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.AuthorizationGoogleRequestDto;
import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;
import raf.aleksabuncic.exception.BadUsernameOrPasswordException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.repository.VeterinarianRepository;
import raf.aleksabuncic.security.JwtUtil;
import raf.aleksabuncic.service.AuthService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final VeterinarianRepository veterinarianRepository;

    @Value("${google.client-id}")
    private String googleClientId;

    @Override
    public AuthorizationResponseDto login(AuthorizationRequestDto authorizationRequestDto) {
        log.info("Login attempt for license number: {}", authorizationRequestDto);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authorizationRequestDto.getLicenseNumber().toString(),
                            authorizationRequestDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadUsernameOrPasswordException("Invalid license number or password");
        }

        String token = jwtUtil.generateToken(authorizationRequestDto.getLicenseNumber());

        AuthorizationResponseDto response = new AuthorizationResponseDto();
        response.setToken(token);

        log.info("Login successful for {}", authorizationRequestDto);
        return response;
    }

    @Override
    public AuthorizationResponseDto loginWithGoogle(AuthorizationGoogleRequestDto authorizationGoogleRequestDto) {
        log.info("Login attempt with Google");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory()
        ).setAudience(Collections.singletonList(googleClientId)).build();

        try {
            GoogleIdToken idToken = verifier.verify(authorizationGoogleRequestDto.getToken());

            if (idToken == null) {
                throw new BadUsernameOrPasswordException("Google authentication failed: invalid token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            Veterinarian veterinarian = veterinarianRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found"));

            String token = jwtUtil.generateToken(veterinarian);

            log.info("Login successful for {}", veterinarian.getLicenseNumber());

            AuthorizationResponseDto response = new AuthorizationResponseDto();
            response.setToken(token);
            return response;
        } catch (GeneralSecurityException | IOException e) {
            throw new BadUsernameOrPasswordException("Google authentication failed ");
        }
    }
}
