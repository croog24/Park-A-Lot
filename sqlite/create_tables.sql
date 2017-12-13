-- Create tables
CREATE TABLE parking_lot(parking_lot_id INTEGER, name TEXT);
CREATE TABLE rating(rating_id INTEGER, parking_lot_id INTEGER, value INTEGER, hour INTEGER, day_of_week TEXT, submitted_by INTEGER);

