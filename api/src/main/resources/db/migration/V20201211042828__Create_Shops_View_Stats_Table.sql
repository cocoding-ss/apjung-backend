use `apjung`;

DROP TABLE IF EXISTS `shops_view_stats`;
CREATE TABLE `shops_view_stats`
(
    `shop_view_stats_id` bigint NOT NULL AUTO_INCREMENT,
    `shop_id` bigint NOT NULL,
    `page_view` bigint NOT NULL DEFAUlt 0,
    `unique_visitor` bigint NOT NULL DEFAUlt 0,
    PRIMARY KEY (`shop_view_stats_id`)
);