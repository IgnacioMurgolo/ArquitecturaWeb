package dao;

import java.sql.SQLException;

public interface FacturaProductoDao {

    void createTable() throws SQLException;

    void readCSV(String path);
}
