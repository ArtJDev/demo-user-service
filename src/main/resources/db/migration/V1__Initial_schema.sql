CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    username varchar (25) UNIQUE NOT NULL ,
    first_name varchar (25),
    last_name varchar (25),
    rating smallint,
    phone_number varchar (25)
);

INSERT INTO users (username, first_name, last_name, rating, phone_number) VALUES ('First', 'Jack', 'Black', 10, '987654'),
                                                                                 ('Second', 'John', 'Patrick', 10, '932101');
