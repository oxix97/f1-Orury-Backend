package org.orury.domain.review.domain.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.orury.common.error.code.ReviewErrorCode;
import org.orury.common.error.exception.InfraImplException;

import java.util.Arrays;
import java.util.Objects;

@Converter
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {
    @Override
    public String convertToDatabaseColumn(Difficulty difficulty) {
        if (Objects.isNull(difficulty)) {
            return "";
        }
        return difficulty.name();
    }

    @Override
    public Difficulty convertToEntityAttribute(String dbData) {
        return Arrays.stream(Difficulty.values())
                .filter(it -> Objects.equals(it.name(), dbData))
                .findFirst()
                .orElseThrow(() -> new InfraImplException(ReviewErrorCode.INVALID_DIFFICULTY));
    }
}
