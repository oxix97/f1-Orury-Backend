ALTER TABLE `review`
    DROP COLUMN angry_count,
    CHANGE interest_count want_to_go_count INT NOT NULL COMMENT '반응: 가고싶어요 누른 수' DEFAULT 0,
    CHANGE like_count helped_count INT NOT NULL COMMENT '반응: 도움돼요 누른 수' DEFAULT 0,
    CHANGE help_count great_count INT NOT NULL COMMENT '반응: 멋져요 누른 수' DEFAULT 0,
    CHANGE thumb_count funny_count INT NOT NULL COMMENT '반응: 재밌어요 누른 수' DEFAULT 0;