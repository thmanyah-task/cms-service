create table cms.category(
id int4 PRIMARY KEY,
name_ar VARCHAR(50),
name_en VARCHAR(50)
);

CREATE SEQUENCE category_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms.category
    ALTER COLUMN id SET DEFAULT nextval('category_id_seq');

INSERT INTO cms.category (name_ar, name_en) VALUES
('وثائقيات', 'Documentaries'),
('سينما', 'Cinema'),
('رياضة', 'Sports'),
('تسويق', 'Marketing'),
('أعمال', 'Business'),
('بيئة', 'Environment'),
('تعليم', 'Education');


