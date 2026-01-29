package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.AuthorizationGoogleRequestDto;
import raf.aleksabuncic.dto.AuthorizationRequestDto;
import raf.aleksabuncic.dto.AuthorizationResponseDto;

public interface AuthService {
    /**
     * Handle login request
     *
     * @param authorizationRequestDto Authorization request object
     * @return Authorization response object
     */
    AuthorizationResponseDto login(AuthorizationRequestDto authorizationRequestDto);

    /**
     * Handle google login request
     *
     * @param googleToken Google token
     * @return Authorization response object
     */
    AuthorizationResponseDto loginWithGoogle(AuthorizationGoogleRequestDto authorizationGoogleRequestDto);
}
