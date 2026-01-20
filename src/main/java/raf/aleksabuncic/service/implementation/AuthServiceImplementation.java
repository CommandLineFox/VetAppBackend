package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;
import raf.aleksabuncic.exception.BadUsernameOrPasswordException;
import raf.aleksabuncic.security.CustomuserDetailsService;
import raf.aleksabuncic.security.JwtUtil;
import raf.aleksabuncic.service.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthorizationResponseDto login(AuthorizationRequestDto authorizationRequestDto) {
        log.info("Login attempt for license number: {}", authorizationRequestDto);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authorizationRequestDto.getLicenseNumber().toString(),
                            authorizationRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadUsernameOrPasswordException("Invalid license number or password");
        }

        String token = jwtUtil.generateToken(authorizationRequestDto.getLicenseNumber());

        AuthorizationResponseDto response = new AuthorizationResponseDto();
        response.setToken(token);

        log.info("Login successful for {}", authorizationRequestDto);
        return response;
    }
}
