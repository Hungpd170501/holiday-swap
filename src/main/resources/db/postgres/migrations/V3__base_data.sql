INSERT INTO role (role_id, name, status)
VALUES (1, 'Admin', TRUE);
INSERT INTO role (role_id, name, status)
VALUES (2, 'Membership', TRUE);
INSERT INTO role (role_id, name, status)
VALUES (3, 'Staff', TRUE);
INSERT INTO role (role_id, name, status)
VALUES (4, 'Guest', TRUE);

INSERT INTO users ("user_id", "avatar", "dob", "email", "email_verified", "full_name", "gender", "password_hash", "phone", "phone_verified", "status", "username", "role_id", "wallet_id")
VALUES (1, NULL, '2023-09-05', 'admin@gmail.com', TRUE, NULL, 'MALE', '$2a$10$iowWaYOKQ3fkC3BI7.Z47uxOEEWk0N2ig2BJeaDjoYAKAqCsHpqpu', '0333325363', FALSE, 'ACTIVE', 'admin', 1, NULL);
INSERT INTO users ("user_id", "avatar", "dob", "email", "email_verified", "full_name", "gender", "password_hash", "phone", "phone_verified", "status", "username", "role_id", "wallet_id")
VALUES (2, NULL, '2023-09-06', 'hungpd170501@gmail.com', FALSE, NULL, 'MALE', '$2a$10$hn0WQbePITfcNEjC5EBmG./alyyK7cEJ1jDZQBArGBUdcDxqN9wxu', '0333325363', FALSE, 'ACTIVE', 'hungpd7', 2, NULL);
INSERT INTO users ("user_id", "avatar", "dob", "email", "email_verified", "full_name", "gender", "password_hash", "phone", "phone_verified", "status", "username", "role_id", "wallet_id")
VALUES (3, NULL, '2001-09-12', 'phuhungx1810@gmail.com', FALSE, NULL, 'MALE', '$2a$10$KquaYZL0ho1Sp1y3UtMdA.BekZf5.UIpFRRuUYzKghiSpNrWl8j6W', '0760760789', FALSE, 'ACTIVE', 'phuhung', 2, NULL);
INSERT INTO users ("user_id", "avatar", "dob", "email", "email_verified", "full_name", "gender", "password_hash", "phone", "phone_verified", "status", "username", "role_id", "wallet_id")
VALUES (4, NULL, '2001-01-01', 'staff@gmail.com', FALSE, NULL, 'MALE', '$2a$10$qRyFLBGDAceflcr858PC7OCufnUU6Yf7FxR9hCSl2YNVwT0VvqSTm', '0333325363', FALSE, 'ACTIVE', 'staff', 3, NULL);
