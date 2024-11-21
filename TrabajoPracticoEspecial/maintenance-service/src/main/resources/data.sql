CREATE DATABASE IF NOT EXISTS maintenancedb;

-- Usar la base de datos
USE maintenancedb;

INSERT INTO maintenance (id, maintenance_date, maintenance_description, maintenance_status, scooter_id) VALUES
                                                                                                            (1, '2024-10-01', 'Battery replacement', 'Completed', 1),
                                                                                                            (2, '2024-10-15', 'Brake adjustment', 'Pending', 2),
                                                                                                            (3, '2024-11-01', 'Tire change', 'Completed', 3),
                                                                                                            (4, '2024-11-05', 'Software update', 'In Progress', 4),
                                                                                                            (5, '2024-11-20', 'General inspection', 'Pending', 5);