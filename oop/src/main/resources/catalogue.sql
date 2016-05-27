DROP TYPE IF EXISTS discount_t;
CREATE TYPE discount_t AS ENUM ('regular', 'promotion');
CREATE TABLE IF NOT EXISTS catalogue(
    id serial PRIMARY KEY NOT NULL,
    discount discount_t,
    title varchar(50) NOT NULL,
    on_prescription bool NOT NULL,
    purchase_date date NOT NULL,
    description varchar(1000)
    CONSTRAINT title_q CHECK (title ~ '[A-Z]{1}\\w{2,49}')
);