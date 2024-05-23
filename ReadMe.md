CREATE TABLE dim_sample (
bacteria_id INT PRIMARY KEY,
bacteria_string VARCHAR(255),
created_date DATE NOT NULL,
created_by VARCHAR(10)
);
CREATE TABLE fact_sample (
id INT PRIMARY KEY,
bacteria_fk_id INT,
sample_id VARCHAR(10),
bacteria_count INT,
bacteria_value  NUMERIC(10,7),
created_date DATE NOT NULL,
created_by VARCHAR(10)
);
ALTER TABLE fact_sample
ADD CONSTRAINT fk_bacteria_id
FOREIGN KEY (bacteria_fk_id)
REFERENCES dim_sample(bacteria_id);

