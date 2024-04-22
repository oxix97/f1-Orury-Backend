package org.orury.client.auth.interfaces.request;

import jakarta.validation.constraints.Size;
import org.orury.domain.global.domain.Region;
import org.orury.domain.global.validation.EnumValues;
import org.orury.domain.user.domain.dto.UserDto;
import org.orury.domain.user.domain.dto.UserStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

public record SignUpRequest(
        int signUpType,
        String email,
        String nickname,
        int gender,
        LocalDate birthday,
        String profileImage,

        @Size(min = 1, max = 3, message = "활동 지역은 최소 1개, 최대 3개까지만 추가할 수 있습니다.")
        @EnumValues(enumClass = Region.class, message = "유효하지 않은 지역이 포함되어 있습니다.")
        List<Region> regions
) {
    // UUID 비밀번호를 암호화 시키기 위한 PasswordEncoder
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static SignUpRequest of(int signUpType, String email, String nickname, int gender, LocalDate birthday, String profileImage, List<Region> regions) {
        return new SignUpRequest(signUpType, email, nickname, gender, birthday, profileImage, regions);
    }

    public UserDto toDto() {
        return new UserDto(
                null,
                email,
                nickname,
                bCryptPasswordEncoder.encode(Integer.toString(signUpType)),
                signUpType,
                gender,
                birthday,
                profileImage,
                null,
                null,
                UserStatus.ENABLE,
                regions
        );
    }
}
