CREATE DATABASE IF NOT EXISTS admindb;

-- Usar la base de datos
USE admindb;

-- Tabla Admin
INSERT INTO admin (id, name, email, price_per_km, extra_price_pause, future_price, effective_date) VALUES
                                                                                                       (1, 'Admin1', 'admin1@example.com', 1.2, 0.5, 1.5, '2024-12-01'),
                                                                                                       (2, 'Admin2', 'admin2@example.com', 1.3, 0.4, 1.6, '2024-12-10'),
                                                                                                       (3, 'Admin3', 'admin3@example.com', 1.1, 0.6, 1.4, '2024-12-15'),
                                                                                                       (4, 'Admin4', 'admin4@example.com', 1.5, 0.3, 1.8, '2024-11-25'),
                                                                                                       (5, 'Admin5', 'admin5@example.com', 1.0, 0.7, 1.2, '2024-12-05');