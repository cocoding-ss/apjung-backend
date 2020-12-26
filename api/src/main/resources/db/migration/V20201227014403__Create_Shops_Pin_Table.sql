use `apjung`;

DROP TABLE IF EXISTS `shops_pin`;
CREATE TABLE `shops_pin` (
    `shop_pin_id` bigint NOT NULL AUTO_INCREMENT,
    `shop_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    PRIMARY KEY (`shop_pin_id`)
);