package org.orury.client.user.interfaces.request;

import jakarta.validation.constraints.NotEmpty;
import org.orury.domain.global.validation.EnumValue;
import org.orury.domain.user.domain.dto.Report;
import org.orury.domain.user.domain.dto.ReportDto;
import org.orury.domain.user.domain.dto.UserDto;

public record UserReportRequest(
        Long userId,

        @EnumValue(enumClass = Report.class, message = "유효하지 않은 신고사유 코드입니다.")
        Report type,

        @NotEmpty(message = "신고물 id는 필수 입력 사항입니다.")
        Long targetId

) {
    public static UserReportRequest of(
            Long userId,
            Report type,
            Long targetId
    ) {
        return new UserReportRequest(userId, type, targetId);
    }

    public ReportDto toDto(UserDto reporterDto, UserDto reporteeDto) {
        return ReportDto.of(
                null,
                reporterDto,
                reporteeDto,
                type,
                targetId
        );
    }
}

