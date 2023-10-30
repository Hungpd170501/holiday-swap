ALTER TABLE notification
    ADD href VARCHAR(255);

ALTER TABLE notification
    ADD is_deleted BOOLEAN;

ALTER TABLE notification
    ADD is_read BOOLEAN;

ALTER TABLE notification
    ADD user_id BIGINT;

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE notification
    DROP COLUMN status;