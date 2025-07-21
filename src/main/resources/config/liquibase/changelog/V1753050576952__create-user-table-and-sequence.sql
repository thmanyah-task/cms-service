CREATE TABLE cms."user" (
    id int8 PRIMARY KEY,
    created_date TIMESTAMP,
    user_name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255)
);

CREATE SEQUENCE user_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."user"
    ALTER COLUMN id SET DEFAULT nextval('user_id_seq');
