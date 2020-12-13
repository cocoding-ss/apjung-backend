use `apjung`;

DROP TABLE IF EXISTS `shops_safe_logs`;
CREATE TABLE `shops_safe_logs` (
    `shop_safe_log_id` bigint NOT NULL AUTO_INCREMENT,
    `shop_id` bigint NOT NULL,
    `safe_at` DATETIME NOT NULL,
    `safe_level` VARCHAR(10) NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`shop_safe_log_id`)
);