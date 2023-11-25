ALTER TABLE rating
    ADD book_id BIGINT;

ALTER TABLE rating
    ALTER COLUMN book_id SET NOT NULL;

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_BOOK FOREIGN KEY (book_id) REFERENCES booking (book_id);

ALTER TABLE rating
DROP
CONSTRAINT fk_rating_on_available_time;

ALTER TABLE rating
DROP
CONSTRAINT fk_rating_on_rating;

ALTER TABLE rating
DROP
COLUMN rating_id;

ALTER TABLE rating
DROP
COLUMN available_time_id;

ALTER TABLE rating
    ADD CONSTRAINT pk_rating PRIMARY KEY (user_id);