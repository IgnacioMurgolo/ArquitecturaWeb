package dao;

import java.sql.SQLException;

public interface FacturaDao {
    void createTable() throws SQLException;

    public void readCSV(String path);
}
