-- Datos de prueba para el entorno H2 en memoria.
-- Credenciales de prueba USER: ana.gomez@example.com / password.
-- La contrasena se almacena con BCrypt, igual que en SecurityConfig.
INSERT INTO usuarios ( nombre, apellido, correo, password, role)
VALUES ('Ana', 'Gomez', 'ana.gomez@example.com',
        '$2a$10$pwS4SkpItkSBNtaa1ORpLe1BufqvQMzF7/6MVd9SPARjQUvlUR5ea', 'USER');

INSERT INTO usuarios (nombre, apellido, correo, password, role)
VALUES ('Carlos', 'Admin', 'carlos.admin@example.com',
        '$2a$10$pwS4SkpItkSBNtaa1ORpLe1BufqvQMzF7/6MVd9SPARjQUvlUR5ea', 'ADMIN');

-- Los prestamos conservan id_user y correo porque la entidad actual los modela como campos simples.
INSERT INTO pretamo_user ( id_user, monto, plazo_date, correo, status)
VALUES ( 1, 15000, DATE '2026-09-15', 'ana.gomez@example.com', 'PENDING');

INSERT INTO pretamo_user ( id_user, monto, plazo_date, correo, status)
VALUES (1, 8500, DATE '2026-10-30', 'ana.gomez@example.com', 'APPROVED');

INSERT INTO pretamo_user ( id_user, monto, plazo_date, correo, status)
VALUES (2, 30000, DATE '2026-12-01', 'carlos.admin@example.com', 'REJECTED');