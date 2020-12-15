USE `apjung`;

ALTER TABLE `users` ADD COLUMN `password_token` varchar(255) DEFAULT NULL after `password`;
ALTER TABLE `users` ADD COLUMN `password_changed_at` DATETIME DEFAULT NULL after `password_token`;

