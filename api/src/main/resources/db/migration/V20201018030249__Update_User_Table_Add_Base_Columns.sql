USE `apjung`;
ALTER TABLE `user` RENAME TO `users`;
-- RENAME TABLE `user` TO `users`;

ALTER TABLE `users` ADD COLUMN `created_at` DATETIME;
ALTER TABLE `users` ADD COLUMN `updated_at` DATETIME;
ALTER TABLE `users` ADD COLUMN `deleted_at` DATETIME;
ALTER TABLE `users` ADD COLUMN `created_by` BIGINT;
ALTER TABLE `users` ADD COLUMN `updated_by` BIGINT;

