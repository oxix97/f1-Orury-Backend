package org.orury.domain.user.domain.dto;

import org.orury.domain.user.domain.entity.Report;

public record ReportDto(
        Long id,
        int type,
        UserDto reporterDto,
        UserDto reporteeDto,
        ReportInfo reportInfo,
        Long targetId
) {
    public static ReportDto of(
            Long id,
            int type,
            UserDto reporterDto,
            UserDto reporteeDto,
            ReportInfo reportInfo,
            Long targetId
    ) {
        return new ReportDto(
                id,
                type,
                reporterDto,
                reporteeDto,
                reportInfo,
                targetId
        );
    }

    public Report toEntity() {
        return Report.of(
                id,
                type,
                reportInfo.getCode(),
                reportInfo.getDescription(),
                targetId,
                reporteeDto.toEntity(),
                reporterDto.toEntity()
        );
    }
}
