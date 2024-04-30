package org.orury.domain.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ReportType {
    POST("게시글", 1),
    COMMENT("댓글", 2);

    private final String description;
    private final int code;

    ReportType(String description, int code) {
        this.description = description;
        this.code = code;
    }

    @JsonCreator
    public static ReportType getEnumFromValue(String value) {
        for (ReportType reportType : ReportType.values()) {
            if (reportType.name().equals(value)) {
                return reportType;
            }
        }
        return null;
    }

}

