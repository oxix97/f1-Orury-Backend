package org.orury.domain.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.orury.domain.global.domain.Region;

import java.util.Arrays;
import java.util.List;

// 커스텀 검증어 구현체
public class RegionValidator implements ConstraintValidator<ValidRegions, List<Region>> {

    @Override
    public boolean isValid(List<Region> regions, ConstraintValidatorContext context) {
        if (regions == null) {
            return false; //
        }
        // Region 열거형에 정의된 모든 값을 가져옵니다.
        Region[] allRegions = Region.values();
        // 모든 입력된 지역이 유효한지 검사합니다.
        for (Region region : regions) {
            boolean isValid = Arrays.asList(allRegions).contains(region);
            if (!isValid) {
                return false;
            }
        }
        return true;
    }
}