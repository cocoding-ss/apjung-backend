use `apjung`;

DROP TABLE IF EXISTS `shops_view_stats`;
CREATE TABLE `shops_view_stats`
(
    `shop_id` bigint NOT NULL,
    `page_view` bigint NOT NULL DEFAUlt 0,
    `unique_visitor` bigint NOT NULL DEFAUlt 0
);