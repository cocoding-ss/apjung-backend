use `apjung`;

DROP TABLE IF EXISTS `shops_view_logs`;
CREATE TABLE `shops_view_logs`
(
    `shop_view_log_id` bigint NOT NULL,
    `shop_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    `accessed_at` date NOT NULL,
    `access_count` int NOT NULL,
    PRIMARY KEY (`shop_view_log_id`)
);