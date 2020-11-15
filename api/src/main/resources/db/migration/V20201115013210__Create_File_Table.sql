use `apjung`;

DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
    `file_id` bigint NOT NULL AUTO_INCREMENT,
    `prefix` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `extension` varchar(255) DEFAULT NULL,
    `original_name` varchar(255) DEFAULT NULL,
    `original_extension` varchar(255) DEFAULT NULL,
    `public_url` varchar(255) DEFAULT NULL,

    `size` bigint NOT NULL,
    `width` int NOT NULL,
    `height` int NOT NULL,
    `is_image` bit(1) NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`file_id`)
);

ALTER TABLE `shops` ADD COLUMN `file_id` bigint AFTER `shop_id`;