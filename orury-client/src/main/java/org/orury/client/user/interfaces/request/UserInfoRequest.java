package org.orury.client.user.interfaces.request;

import org.hibernate.validator.constraints.Length;
import org.orury.common.util.ImageUrlConverter;
import org.orury.domain.user.domain.dto.UserDto;

public record UserInfoRequest(
        String nickname,
        @Length(max = 200, message = "자기소개는 최대 200자까지 작성 가능합니다.")
        String selfIntroduction
) {
    public UserDto toDto(UserDto userDto) {
        return UserDto.of(
                userDto.id(),
                userDto.email(),
                nickname,
                userDto.password(),
                userDto.signUpType(),
                userDto.gender(),
                userDto.birthday(),
                ImageUrlConverter.splitUrlToImage(userDto.profileImage()),
                userDto.createdAt(),
                null,
                userDto.status(),
                userDto.regions(),
                selfIntroduction
        );
    }
}
