CREATE TABLE `report`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT,
    `type`        int      NOT NULL COMMENT '신고 유형, 1: 게시글 2: 댓글',
    `reason`      int      NOT NULL COMMENT '신고 사유 코드',
    `description` varchar(100) NULL COMMENT '신고 사유',
    `reportee_id` bigint(32) NOT NULL COMMENT '신고 대상 유저 id',
    `reporter_id` bigint(32) NOT NULL COMMENT '신고자 id',
    `target_id`   bigint   NOT NULL COMMENT '신고 대상 id',
    `created_at`  DATETIME NOT NULL COMMENT '@CreatedDate',
    `updated_at`  DATETIME NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `report`
    ADD CONSTRAINT `FK_reportee_id_TO_report` FOREIGN KEY (`reportee_id`)
    REFERENCES `user` (`id`),
    ADD CONSTRAINT `FK_reporter_id_TO_report` FOREIGN KEY (`reporter_id`)
    REFERENCES `user` (`id`);