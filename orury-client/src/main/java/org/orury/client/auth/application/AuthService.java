package org.orury.client.auth.application;

import jakarta.servlet.http.HttpServletRequest;
import org.orury.client.auth.interfaces.request.LoginRequest;
import org.orury.domain.auth.domain.dto.JwtToken;
import org.orury.domain.auth.domain.dto.LoginDto;
import org.orury.domain.auth.domain.dto.SignUpDto;
import org.orury.domain.user.domain.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    SignUpDto signUp(UserDto userDto, MultipartFile file);

    LoginDto login(LoginRequest loginRequest);

    JwtToken reissueJwtTokens(HttpServletRequest request);
}
