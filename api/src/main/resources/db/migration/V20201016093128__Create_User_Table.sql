USE `apjung`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `email_auth_token` varchar(255) DEFAULT NULL,
  `is_email_auth` bit(1) NOT NULL DEFAULT 0,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL,
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE INDEX `email_index` ON `users` (`email`);