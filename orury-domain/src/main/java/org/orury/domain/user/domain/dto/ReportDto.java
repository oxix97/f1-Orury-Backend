package org.orury.domain.user.domain.dto;

import org.orury.domain.user.domain.entity.Report;

public record ReportDto(
        Long id,
        UserDto reporterDto,
        UserDto reporteeDto,
        ReportInfo report,
        Long targetId
) {
    public static ReportDto of(
            Long id,
            UserDto reporterDto,
            UserDto reporteeDto,
            ReportInfo report,
            Long targetId
    ) {
        return new ReportDto(
                id,
                reporterDto,
                reporteeDto,
                report,
                targetId
        );
    }

    public Report toEntity() {
        return Report.of(
                id,
                reporterDto.toEntity(),
                reporteeDto.toEntity(),
                report,
                targetId
        );
    }
}
