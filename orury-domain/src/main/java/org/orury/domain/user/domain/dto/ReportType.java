package org.orury.domain.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.orury.common.error.code.CommentErrorCode;
import org.orury.common.error.code.PostErrorCode;
import org.orury.common.error.exception.BusinessException;
import org.orury.domain.comment.domain.CommentReader;
import org.orury.domain.post.domain.PostReader;

@Getter
public enum ReportType {
    POST("게시글", 1) {
        @Override
        public void checkReportTarget(Long targetId, PostReader postReader, CommentReader commentReader) {
            postReader.findById(targetId)
                    .orElseThrow(() -> new BusinessException(PostErrorCode.NOT_FOUND));
        }
    },
    COMMENT("댓글", 2) {
        @Override
        public void checkReportTarget(Long targetId, PostReader postReader, CommentReader commentReader) {
            commentReader.findCommentById(targetId)
                    .orElseThrow(() -> new BusinessException(CommentErrorCode.NOT_FOUND));
        }
    };

    private final String description;
    private final int code;

    public abstract void checkReportTarget(Long targetId, PostReader postReader, CommentReader commentReader);

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

