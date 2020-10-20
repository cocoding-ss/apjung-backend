USE `apjung`;

ALTER TABLE `users` MODIFY COLUMN `email` varchar(255) NOT NULL;
ALTER TABLE `users` MODIFY COLUMN `password` varchar(255) NOT NULL;
ALTER TABLE `users` MODIFY COLUMN `name` varchar(255) NOT NULL;
ALTER TABLE `users` MODIFY COLUMN `name` varchar(255) NOT NULL;
ALTER TABLE `users` MODIFY COLUMN `is_email_auth` BIT(1) NOT NULL DEFAULT 0;