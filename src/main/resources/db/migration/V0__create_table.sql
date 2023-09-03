CREATE TABLE IF NOT EXISTS weather_data
(
    id BIGINT PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(5) NOT NULL,
    weather_description VARCHAR(255) NOT NULL,
    updated_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE UNIQUE INDEX weather_data ON weather_data(city, country);
