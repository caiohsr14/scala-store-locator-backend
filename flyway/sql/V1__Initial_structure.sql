CREATE TABLE store (
    id                          SERIAL PRIMARY KEY,
    address_lines               TEXT,
    store_name                  TEXT NOT NULL,
    phone_number                TEXT,
    street_address_line_1       TEXT NOT NULL,
    street_address_line_2       TEXT,
    street_address_line_3       TEXT,
    city                        TEXT NOT NULL,
    country_subdivision_code    TEXT NOT NULL,
    country_code                TEXT NOT NULL,
    postal_code                 TEXT NOT NULL,
    open_status_text            TEXT NOT NULL,
    location_type               TEXT NOT NULL,
    latitude                    FLOAT NOT NULL,
    longitude                   FLOAT NOT NULL,
    geography_point             GEOGRAPHY GENERATED ALWAYS AS (ST_MakePoint(longitude, latitude)::geography) STORED
);

CREATE INDEX ON store USING GIST(geography_point);
