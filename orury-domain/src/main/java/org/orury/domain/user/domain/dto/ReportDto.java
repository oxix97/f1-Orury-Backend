package org.orury.domain.user.domain.dto;

public record ReportDto(
        Long id,
        UserDto reporterDto,
        UserDto reporteeDto,
        Report report,
        Long targetId
) {
    public static ReportDto of(
            Long id,
            UserDto reporterDto,
            UserDto reporteeDto,
            Report report,
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
}
