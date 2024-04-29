package org.orury.domain.review.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty {
    RED("빨강"),
    ORANGE("주황"),
    YELLOW("노랑"),
    GREEN("초록"),
    BLUE("파랑"),
    NAVY("남색"),
    PURPLE("보라"),
    GRAY("회색"),
    BLACK("검정"),
    COMP("컴피");

    private final String description;

    @JsonCreator
    public static Difficulty getEnumFromValue(String value) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.name().equals(value)) {
                return difficulty;
            }
        }
        return null;
    }
}
