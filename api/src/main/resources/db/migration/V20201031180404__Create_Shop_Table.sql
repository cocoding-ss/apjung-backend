use `apjung`;

DROP TABLE IF EXISTS `shops`;
CREATE TABLE `shops` (
    `shop_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `url` varchar(255) DEFAULT NULL,
    `overview` varchar(255) DEFAULT NULL,

    `safe_at` DATETIME NOT NULL,
    `safe_level` VARCHAR(10) NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`shop_id`)
)