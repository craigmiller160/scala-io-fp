DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS transaction_type;

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
