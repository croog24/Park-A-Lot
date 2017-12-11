-- Create tables
CREATE TABLE parking_lot(id INTEGER, name TEXT);
CREATE TABLE rating(id INTEGER, parking_lot_id INTEGER, value INTEGER, hour INTEGER, day_of_week TEXT, submitted_by INTEGER);

