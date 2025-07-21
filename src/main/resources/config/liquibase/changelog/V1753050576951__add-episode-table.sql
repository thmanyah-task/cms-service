create table cms."episode"(
id int8 PRIMARY KEY,
created_date TIMESTAMP,
updated_date TIMESTAMP,
published_date DATE,
episode_number int4,
subject VARCHAR (250),
description TEXT,
duration DOUBLE PRECISION,
episode_url TEXT,
programme_id int8,
FOREIGN KEY (programme_id) REFERENCES cms."programme"(id)
);

CREATE SEQUENCE episode_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."episode"
    ALTER COLUMN id SET DEFAULT nextval('episode_id_seq');