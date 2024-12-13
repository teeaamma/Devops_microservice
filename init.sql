DROP TABLE IF EXISTS asset;
CREATE TABLE asset(id SERIAL PRIMARY KEY, figi VARCHAR(50), name VARCHAR(50), type VARCHAR(50), price FLOAT);

INSERT INTO asset (figi, name, type, price) VALUES ('BBG004730N88', 'Сбербанк России', 'STOCK', '230.90');
INSERT INTO asset (figi, name, type, price) VALUES ('BBG004730RP0', 'Газпром', 'STOCK', '113.55');
INSERT INTO asset (figi, name, type, price) VALUES ('USD', 'Доллар США', 'CURRENCY', '103.95');
INSERT INTO asset (figi, name, type, price) VALUES ('DJF', 'Франк Джибути', 'CURRENCY', '0.58');
INSERT INTO asset (figi, name, type, price) VALUES ('BTC', 'Биткоин', 'CRYPTOCURRENCY', '10547521.39');
INSERT INTO asset (figi, name, type, price) VALUES ('DOGE', 'Dogecoin', 'CRYPTOCURRENCY', '43.13');