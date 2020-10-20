INSERT INTO `roles`
(`code`, `description`, `created_at`, `updated_at`, `created_by`, `updated_by`)
VALUES
('ADMIN', '압정 관리자', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), NULL, NULL),
('USER', '일반 사용자', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), NULL, NULL);