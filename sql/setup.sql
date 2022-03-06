DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS transaction_type;
DROP PROCEDURE IF EXISTS transaction_load;

CREATE TABLE transaction_type (
    id UUID NOT NULL,
    type_name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE people (
    id UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transactions (
    id UUID NOT NULL,
    type_id UUID NOT NULL,
    person_id UUID NOT NULL,
    amount DECIMAL NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO people (id, first_name, last_name)
VALUES (gen_random_uuid(), 'Bob', 'Saget'), (gen_random_uuid(), 'Jane', 'Foster');

INSERT INTO transaction_type (id, type_name)
VALUES (gen_random_uuid(), 'Withdrawal'), (gen_random_uuid(), 'Deposit');

CREATE PROCEDURE transaction_load () LANGUAGE plpgsql
    AS $$
    DECLARE
        bob_id UUID;
        jane_id UUID;
        withdrawal_id UUID;
        deposit_id UUID;
    BEGIN
        SELECT id INTO bob_id FROM people WHERE first_name = 'Bob' AND last_name = 'Saget';
        SELECT id INTO jane_id FROM people WHERE first_name = 'Jane' AND last_name = 'Foster';
        SELECT id INTO withdrawal_id FROM transaction_type WHERE type_name = 'Withdrawal';
        SELECT id INTO deposit_id FROM transaction_type WHERE type_name = 'Deposit';
    END $$;
