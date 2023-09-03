CREATE TABLE IF NOT EXISTS weather_data
(
    city VARCHAR(255) NOT NULL,
    country VARCHAR(10) NOT NULL,
    raw_data VARCHAR(1000000) NOT NULL,
    updated_date TIMESTAMP WITH TIME ZONE,

    CONSTRAINT city_country PRIMARY KEY (city, country)
);
