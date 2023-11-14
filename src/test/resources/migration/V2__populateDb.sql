INSERT INTO client (name)
VALUES
('Bogdan'),
('Volodymyr'),
('Roman'),
('Olexandr'),
('Maksym'),
('Roman'),
('Anatoliy'),
('Nazar'),
('Pavlo'),
('Nazar');

INSERT INTO planet (id, name)
VALUES
('VEN','Venera'),
('MARS','Mars'),
('MER','Mercury'),
('SAT','Saturn'),
('NEP','Neptune');

INSERT INTO ticket (client_id, from_planet_id, to_planet_id)
VALUES
(1,'MARS','MER'),
(2,'MARS','MARS'),
(1,'MER','NEP'),
(5,'MARS','SAT'),
(3,'VEN','MARS'),
(4,'MARS','SAT'),
(2,'SAT','MARS'),
(1,'MARS','MER'),
(1,'VEN','SAT'),
(4,'NEP','MER'),
(5,'VEN','NEP');