use `apjung`;

ALTER TABLE `shops_view_logs` CHANGE `access_count` `accessed_count` int NOT NULL DEFAULT 1;