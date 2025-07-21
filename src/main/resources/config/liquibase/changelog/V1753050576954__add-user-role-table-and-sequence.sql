CREATE TABLE cms."user_role" (
    id int8 PRIMARY KEY,
    user_id int8,
    role_id int8,
    FOREIGN KEY (user_id) REFERENCES cms."user"(id),
    FOREIGN KEY (role_id) REFERENCES cms."role"(id)
);


CREATE SEQUENCE user_role_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."user_role"
    ALTER COLUMN id SET DEFAULT nextval('user_role_id_seq');