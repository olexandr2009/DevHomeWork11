-- Create the Planet table
CREATE TABLE planet (
    id character varying(10) PRIMARY KEY CHECK (id ~ '^[A-Z0-9]+$'),
    name character varying(500) NOT NULL
);
-- Create the Client table
CREATE TABLE client (
    id serial PRIMARY KEY,
    name character varying(200) NOT NULL CHECK (char_length(name) >= 3)
);
-- Create the Ticket table
CREATE TABLE ticket (
    id serial PRIMARY KEY,
    created_at timestamp with time zone DEFAULT current_timestamp,
    client_id integer REFERENCES client(id),
    from_planet_id character varying(10) REFERENCES planet(id),
    to_planet_id character varying(10) REFERENCES planet(id)
);
