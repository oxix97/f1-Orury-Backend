package org.fastcampus.oruryadmin.domain.admin.converter.response;

public record AdminResponse(
        String name,
        String email,
        String password,
        String accessToken
) {
    public static AdminResponse of(
            String name,
            String email,
            String password,
            String accessToken
    ) {
        return new AdminResponse(
                name,
                email,
                password,
                accessToken
        );
    }
}
