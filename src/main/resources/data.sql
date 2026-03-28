DELETE FROM players;
DELETE FROM fighters;

insert into players (name, username, email, password, role) values
('Sample Player', 'player1', 'player1@tekken.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiA6H6H8VEWilQoNqT9XyM54q9WlH8G', 'PLAYER');

insert into fighters (name, health, damage, resistance) values
('Jin', 1200, 85, 7),
('Kazuya', 1300, 95, 6);