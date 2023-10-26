-- Database: meu-news

-- DROP DATABASE IF EXISTS "meu-news";

CREATE DATABASE "meu-news"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
CREATE TABLE Category (
    id VARCHAR PRIMARY KEY,
    category_name VARCHAR
);

CREATE TABLE news_detail (
    id VARCHAR PRIMARY KEY,
    title TEXT,
    img TEXT,
    publish_date VARCHAR(10),
    category_id VARCHAR REFERENCES Category (id),
    detail_sapo TEXT,
    news_content TEXT
);

-- Function for auto generating UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE OR REPLACE FUNCTION generate_uuid()
RETURNS UUID AS $$
BEGIN
    RETURN UUID_GENERATE_V4();
END;
$$ LANGUAGE plpgsql;

-- Trigger for auto-generating UUID before adding Category object
CREATE OR REPLACE FUNCTION category_generate_uuid()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.id IS NULL THEN
        NEW.id = generate_uuid();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER category_auto_uuid
    BEFORE INSERT
    ON Category
    FOR EACH ROW
    EXECUTE FUNCTION category_generate_uuid();

	-- Add value to category
INSERT INTO Category (category_name) VALUES
  ('Trang chủ'),
  ('Thời sự'),
  ('Thế giới'),
  ('Pháp luật'),
  ('Kinh doanh'),
  ('Công nghệ'),
  ('Xe'),
  ('Du lịch'),
  ('Nhịp sống trẻ'),
  ('Văn hoá'),
  ('Giải trí'),
  ('Thể thao'),
  ('Giáo dục'),
  ('Nhà đất'),
  ('Sức khoẻ'),
  ('Giả thật'),
  ('Bạn đọc');

	
	

