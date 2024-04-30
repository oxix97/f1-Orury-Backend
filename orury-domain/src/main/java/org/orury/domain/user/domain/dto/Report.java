package org.orury.domain.user.domain.dto;

import lombok.Getter;

@Getter
public enum Report {
    INVALID_SELLING("부적절한 상품을 팔거나 홍보하고 있습니다."),
    

    // ("저작권, 명예훼손 사칭 등 기타 권리를 침해하는 내용입니다.")

    private final String description;

    Report(String description) {
        this.description = description;
    }
}
