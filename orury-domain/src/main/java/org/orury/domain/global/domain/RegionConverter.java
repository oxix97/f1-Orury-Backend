package org.orury.domain.global.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RegionConverter implements AttributeConverter<List<Region>, String> {
    @Override
    public String convertToDatabaseColumn(List<Region> regions) {
        if (regions == null || regions.isEmpty()) {
            return "";
        }
        return regions.stream()
                .map(Region::getDescription)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Region> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(","))
                .map(String::trim) // 공백 제거
                .map(dbValue -> {
                    for (Region region : Region.values()) {
                        if (region.getDescription().equals(dbValue)) {
                            return region;
                        }
                    }
                    throw new IllegalArgumentException("유효하지 않은 지역 : " + dbValue); // 유효하지 않은 지역 설명에 대한 예외 처리
                })
                .collect(Collectors.toList());
    }
}
