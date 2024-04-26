CREATE TABLE IF NOT EXISTS `review_tag` (
                                          `id`	            BIGINT   	       NOT NULL    AUTO_INCREMENT,
                                          `review_id`	    BIGINT             NOT NULL,
                                          `tag`             VARCHAR(30)        NOT NULL,
    PRIMARY KEY (`id`)
    );

ALTER TABLE `review_tag` ADD CONSTRAINT `FK_review_TO_review_tag_1` FOREIGN KEY (`review_id`)
    REFERENCES `review` (`id`)
    ON DELETE CASCADE;