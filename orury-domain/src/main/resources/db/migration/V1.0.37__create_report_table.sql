CREATE TABLE `report`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT,
    `type`        int      NOT NULL COMMENT '신고 유형, 1: 게시글 2: 댓글',
    `reason`      int      NOT NULL COMMENT '신고 사유 코드',
    `description` varchar(100) NULL COMMENT '신고 사유',
    `user_id`     bigint(32) NOT NULL,
    `target_id`   bigint   NOT NULL COMMENT '신고 대상 id',
    `created_at`  DATETIME NOT NULL COMMENT '@CreatedDate',
    `updated_at`  DATETIME NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `report`
    ADD CONSTRAINT `FK_user_TO_report` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`);