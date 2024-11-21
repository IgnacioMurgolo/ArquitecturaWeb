CREATE DATABASE IF NOT EXISTS userdb;

-- Usar la base de datos
USE userdb;

-- Tabla Account
INSERT INTO account (id, balance, creation_date, active, users_id, trips_id)
VALUES (1, 50.0, '2024-09-01', TRUE, '1,2', '1,2'),
       (2, 20.5, '2024-10-01', TRUE, '3', '3'),
       (3, 75.3, '2024-07-15', FALSE, '4', NULL),
       (4, 100.0, '2024-06-20', TRUE, '5', '4'),
       (5, 30.0, '2024-08-10', TRUE, '6', '5');

-- Tabla User
INSERT INTO app_user (id, user_name, name, last_name, phone_number, email, accounts_id)
VALUES (1, 'jdoe', 'John', 'Doe', 123456789, 'jdoe@example.com', '1,2'),
       (2, 'asmith', 'Alice', 'Smith', 987654321, 'asmith@example.com', '2'),
       (3, 'bjones', 'Bob', 'Jones', 567890123, 'bjones@example.com', '3'),
       (4, 'kmiller', 'Karen', 'Miller', 789012345, 'kmiller@example.com', '4'),
       (5, 'tjohnson', 'Tom', 'Johnson', 345678901, 'tjohnson@example.com', '5');

INSERT INTO user_accounts_id (user_id, accounts_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

-- Inserciones para la tabla intermedia account_users_ids
INSERT INTO account_users_ids (account_id, user_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 5);

-- Inserciones para la tabla intermedia account_trips_ids
INSERT INTO account_trips_ids (account_id, trip_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 5);
