use `apjung`;

CREATE INDEX `view_log_index` ON `shops_view_logs` (`user_id`, `shop_id`, `accessed_at`);