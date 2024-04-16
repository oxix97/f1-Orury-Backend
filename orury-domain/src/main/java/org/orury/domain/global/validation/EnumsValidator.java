package org.orury.domain.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumsValidator implements ConstraintValidator<EnumValues, List<? extends Enum<?>>> {
    private Set<String> allowedValues;

    @Override
    public void initialize(EnumValues constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(List<? extends Enum<?>> valueList, ConstraintValidatorContext context) {
        if (valueList == null) {
            return false;
        }
        for (Enum<?> value : valueList) {
            if (Objects.isNull(value) || !allowedValues.contains(value.name())) {
                return false;
            }
        }
        return true;
    }
}