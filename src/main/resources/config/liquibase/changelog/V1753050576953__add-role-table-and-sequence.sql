CREATE TABLE cms."role" (
    id int8 PRIMARY KEY,
    name VARCHAR(255)
);

CREATE SEQUENCE role_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."role"
    ALTER COLUMN id SET DEFAULT nextval('role_id_seq');