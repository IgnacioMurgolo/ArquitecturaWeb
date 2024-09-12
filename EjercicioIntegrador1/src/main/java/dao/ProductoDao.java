package dao;

import dto.ProductoDTO;

import java.sql.SQLException;

public interface ProductoDao {

    void createTable() throws SQLException;
    void readCSV(String path);
    ProductoDTO obtenerProductoConMasRecaudacion();
}
