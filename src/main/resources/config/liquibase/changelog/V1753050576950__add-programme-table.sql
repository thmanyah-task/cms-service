create table cms."programme"(
id int8 PRIMARY KEY,
created_date TIMESTAMP,
updated_date TIMESTAMP,
subject VARCHAR (250),
description TEXT,
category_id int4,
language_id int4,
programme_url TEXT,
FOREIGN KEY (category_id) REFERENCES cms."category"(id),
FOREIGN KEY (language_id) REFERENCES cms."language"(id)
);

CREATE SEQUENCE programme_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."programme"
    ALTER COLUMN id SET DEFAULT nextval('programme_id_seq');