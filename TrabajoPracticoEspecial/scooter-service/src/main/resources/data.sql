CREATE DATABASE IF NOT EXISTS scooterdb;

-- Usar la base de datos
USE scooterdb;

-- Tabla Scooter
INSERT INTO scooter (id, status, ubication, kilometers_traveled, hours_used, current_station_id, current_trip_id) VALUES
                                                                                                                      (1, TRUE, 'Station A', 120.5, 50.2, 1, 1),
                                                                                                                      (2, FALSE, 'Station B', 200.3, 80.1, 2, 2),
                                                                                                                      (3, TRUE, 'Station C', 310.8, 120.5, 3, NULL),
                                                                                                                      (4, TRUE, 'Station D', 150.0, 60.0, 4, 3),
                                                                                                                      (5, FALSE, 'Station E', 100.7, 40.3, 5, NULL);

-- Inserciones para la tabla intermedia scooter_maintenance_ids
INSERT INTO scooter_maintenance_ids (scooter_id, maintenance_id) VALUES
                                                                     (1, 1),
                                                                     (2, 2),
                                                                     (3, 3),
                                                                     (4, 4),
                                                                     (5, 5);

-- Tabla Station
INSERT INTO station (id, name, ubication, scooters_id) VALUES
                                                           (1, 'Station A', 'Downtown', '1,2'),
                                                           (2, 'Station B', 'Uptown', '3,4'),
                                                           (3, 'Station C', 'Midtown', '5'),
                                                           (4, 'Station D', 'Suburb', NULL),
                                                           (5, 'Station E', 'City Outskirts', '2,3');