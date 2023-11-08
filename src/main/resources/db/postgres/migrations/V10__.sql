CREATE TABLE rating
(
    comment     VARCHAR(255),
    rating      DOUBLE PRECISION NOT NULL,
    property_id BIGINT           NOT NULL,
    user_id     BIGINT           NOT NULL,
    CONSTRAINT pk_rating PRIMARY KEY (property_id, user_id)
);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES property (property_id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);