ALTER TABLE booking
    ADD status_check_return BOOLEAN;

ALTER TABLE booking
    ADD total_member INTEGER;

ALTER TABLE apartment_wallet
    DROP CONSTRAINT fk_apartment_wallet_on_property;

DROP TABLE apartment_wallet CASCADE;

ALTER TABLE all_transaction_log
    DROP COLUMN amount;

ALTER TABLE all_transaction_log
    ADD amount DOUBLE PRECISION;

ALTER TABLE all_transaction_log
    ALTER COLUMN amount DROP NOT NULL;

ALTER TABLE transaction_log
    DROP COLUMN amount_point;

ALTER TABLE transaction_log
    ADD amount_point DOUBLE PRECISION;

ALTER TABLE transaction_log
    ALTER COLUMN amount_point DROP NOT NULL;