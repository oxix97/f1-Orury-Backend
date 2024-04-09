package org.orury.domain.gym.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GymType {
    BOULDERING("볼더링", "B"),
    WALL("인공 암벽", "W");

    private final String description;
    private final String code;

    @JsonCreator
    public static GymType getEnumFromValue(String value) {
        for (GymType gymType : GymType.values()) {
            if (gymType.name().equals(value)) {
                return gymType;
            }
        }
        return null;
    }
}
