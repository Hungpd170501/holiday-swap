ALTER TABLE booking
    ADD date_booking VARCHAR(255);

ALTER TABLE booking
    ALTER COLUMN date_booking SET NOT NULL;