package org.orury.client.user.interfaces.request;

import jakarta.validation.constraints.NotNull;
import org.orury.domain.global.validation.EnumValue;
import org.orury.domain.user.domain.dto.ReportDto;
import org.orury.domain.user.domain.dto.ReportInfo;
import org.orury.domain.user.domain.dto.UserDto;

public record UserReportRequest(
        Long userId,
        int type,

        @EnumValue(enumClass = ReportInfo.class, message = "유효하지 않은 신고사유 코드입니다.")
        ReportInfo reportInfo,

        @NotNull(message = "신고물 id는 필수 입력 사항입니다.")
        Long targetId

) {
    public static UserReportRequest of(
            Long userId,
            int type,
            ReportInfo reportInfo,
            Long targetId
    ) {
        return new UserReportRequest(userId, type, reportInfo, targetId);
    }

    public ReportDto toDto(UserDto reporterDto, UserDto reporteeDto) {
        return ReportDto.of(
                null,
                type,
                reporterDto,
                reporteeDto,
                reportInfo,
                targetId
        );
    }
}

