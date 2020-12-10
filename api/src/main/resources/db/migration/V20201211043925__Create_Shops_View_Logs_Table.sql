use `apjung`;

DROP TABLE IF EXISTS `shops_view_logs`;
CREATE TABLE `shops_view_logs`
(
    `shop_view_log_id` bigint NOT NULL AUTO_INCREMENT,
    `shop_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    `accessed_at` date NOT NULL,
    `access_count` int NOT NULL default 1,
    PRIMARY KEY (`shop_view_log_id`)
);