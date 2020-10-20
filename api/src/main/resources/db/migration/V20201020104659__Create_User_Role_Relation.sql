USE `apjung`;

CREATE TABLE `users_roles` (
    `user_role_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,

    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `updated_by` BIGINT DEFAULT NULL
)