package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;

public interface AuthService {
    AuthorizationResponseDto login(AuthorizationRequestDto authorizationRequestDto);
}
