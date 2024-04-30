package org.orury.domain.user.domain.dto;

import lombok.Getter;

@Getter
public enum Report {
    INVALID_SELLING("부적절한 상품을 팔거나 홍보하고 있습니다.", 0),
    INFRINGEMENT("저작권, 명예훼손 사칭 등 기타 권리를 침해하는 내용입니다.", 1),
    CONTAIN_PERSONAL_INFO("특정인의 개인정보가 포함되어 있습니다.", 2),
    HATE_SPEECH("혐오를 조장하는 내용입니다.", 3),
    OBSCENE_CONTENT("음란한 내용을 담고 있습니다", 4),
    SUICIDE_CONTENT("자해나 자살과 관련된 내용입니다.", 5),
    ETC("기타", 6);

    private final String description;
    private final int code;

    Report(String description, int code) {
        this.description = description;
        this.code = code;
    }

}
