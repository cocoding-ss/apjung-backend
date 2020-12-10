use `apjung`;

DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
    `tag_id` bigint NOT NULL AUTO_INCREMENT,
    `file_id` bigint,
    `name` VARCHAR(100) NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`tag_id`)
);