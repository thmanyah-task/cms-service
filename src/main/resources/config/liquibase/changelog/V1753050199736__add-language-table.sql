create table cms."language"(
id int4 PRIMARY KEY,
name_ar VARCHAR(50),
name_en VARCHAR(50)
);

CREATE SEQUENCE language_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms."language"
    ALTER COLUMN id SET DEFAULT nextval('language_id_seq');

INSERT INTO cms."language" (name_ar, name_en) VALUES
('العربية', 'Arabic'),
('الإنجليزية', 'English'),
('الفرنسية', 'French'),
('الإسبانية', 'Spanish'),
('الألمانية', 'German'),
('الروسية', 'Russian'),
('الصينية', 'Chinese'),
('اليابانية', 'Japanese'),
('التركية', 'Turkish'),
('الهندية', 'Hindi'),
('الإيطالية', 'Italian'),
('الكورية', 'Korean'),
('البرتغالية', 'Portuguese'),
('الأوردو', 'Urdu'),
('الإندونيسية', 'Indonesian');
