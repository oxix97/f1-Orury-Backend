package org.orury.domain.user.domain.dto;

import org.orury.domain.global.domain.Region;
import org.orury.domain.user.domain.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link User}
 */
public record UserDto(
        Long id,
        String email,
        String nickname,
        String password,
        int signUpType,
        int gender,
        LocalDate birthday,
        String profileImage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserStatus status,
        List<Region> regions,
        String selfIntroduction
) {
    public static UserDto of(
            Long id,
            String email,
            String nickname,
            String password,
            int signUpType,
            int gender,
            LocalDate birthday,
            String profileImage,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UserStatus status,
            List<Region> regions,
            String selfIntroduction
    ) {
        return new UserDto(
                id,
                email,
                nickname,
                password,
                signUpType,
                gender,
                birthday,
                profileImage,
                createdAt,
                updatedAt,
                status,
                regions,
                selfIntroduction
        );
    }

    public static UserDto from(User entity) {
        return UserDto.of(
                entity.getId(),
                entity.getEmail(),
                checkStatus(entity),
                entity.getPassword(),
                entity.getSignUpType(),
                entity.getGender(),
                entity.getBirthday(),
                entity.getProfileImage(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getStatus(),
                entity.getRegions(),
                entity.getSelfIntroduction()
        );
    }

    public static UserDto from(User entity, String profileImage) {
        return UserDto.of(
                entity.getId(),
                entity.getEmail(),
                checkStatus(entity),
                entity.getPassword(),
                entity.getSignUpType(),
                entity.getGender(),
                entity.getBirthday(),
                profileImage,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getStatus(),
                entity.getRegions(),
                entity.getSelfIntroduction()
        );
    }

    public User toEntity() {
        return User.of(
                id,
                email,
                nickname,
                password,
                signUpType,
                gender,
                birthday,
                profileImage,
                createdAt,
                updatedAt,
                status,
                regions,
                selfIntroduction
        );
    }

    public User toEntity(String newProfileImage) {
        return User.of(
                id,
                email,
                nickname,
                password,
                signUpType,
                gender,
                birthday,
                newProfileImage,
                createdAt,
                updatedAt,
                status,
                regions,
                selfIntroduction
        );
    }

    public User toEntity(UserStatus status) {
        return User.of(
                id,
                email,
                nickname,
                password,
                signUpType,
                gender,
                birthday,
                profileImage,
                createdAt,
                updatedAt,
                status,
                regions,
                selfIntroduction
        );
    }

    private static String checkStatus(User user) {
        var status = user.getStatus();
        if (status == UserStatus.ENABLE) return user.getNickname();
        return status.getDescription();
    }
}