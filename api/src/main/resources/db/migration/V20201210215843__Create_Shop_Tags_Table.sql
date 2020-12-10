use `apjung`;

DROP TABLE IF EXISTS `shops_tags`;
CREATE TABLE `shops_tags` (
    `shop_tag_id` bigint NOT NULL AUTO_INCREMENT,
    `tag_id` bigint NOT NULL,
    `shop_id` bigint NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`shop_tag_id`)
);