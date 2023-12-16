ALTER TABLE booking
    ADD qrcode VARCHAR(255);

ALTER TABLE booking
    ADD uuid VARCHAR(255);

ALTER TABLE booking
    ALTER COLUMN qrcode SET NOT NULL;

ALTER TABLE booking
    ALTER COLUMN uuid SET NOT NULL;

ALTER TABLE all_transaction_log
    ALTER COLUMN amount DROP NOT NULL;