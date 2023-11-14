ALTER TABLE rating
    ADD available_time_id BIGINT;

ALTER TABLE rating
    ADD rating_type VARCHAR(255);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_AVAILABLE_TIME FOREIGN KEY (available_time_id) REFERENCES available_time (available_time_id);

ALTER TABLE rating
DROP
CONSTRAINT fk_rating_on_property;

ALTER TABLE rating
DROP
COLUMN property_id;

ALTER TABLE rating
    ADD CONSTRAINT pk_rating PRIMARY KEY (available_time_id, user_id);