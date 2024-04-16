package org.orury.domain.global.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegionValidator.class)
public @interface ValidRegions {
    String message() default "활동 지역은 정확한 값을 입력해 주세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
