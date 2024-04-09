package org.orury.client.crew.interfaces.response;

import org.orury.domain.user.domain.dto.UserDto;

import java.util.Objects;

public record CrewMembersResponse(
        Long id,
        String nickname,
        String profileImage,
        boolean isCrewCreator,
        boolean isMe
) {
    public static CrewMembersResponse of(
            Long id,
            String nickname,
            String profileImage,
            boolean isCrewCreator,
            boolean isMe
    ) {
        return new CrewMembersResponse(
                id,
                nickname,
                profileImage,
                isCrewCreator,
                isMe
        );
    }

    public static CrewMembersResponse of(UserDto userDto, Long userId, Long crewCreatorId) {
        return CrewMembersResponse.of(
                userDto.id(),
                userDto.nickname(),
                userDto.profileImage(),
                Objects.equals(crewCreatorId, userDto.id()),
                Objects.equals(userId, userDto.id())
        );
    }
}
