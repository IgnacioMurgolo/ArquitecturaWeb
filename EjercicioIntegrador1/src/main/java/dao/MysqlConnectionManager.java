package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionManager implements ConnectionManager {

    @Override
    public Connection getConnection() throws SQLException {
        String URI = "jdbc:mysql://localhost:3306/EjercicioIntegrador1";
        return DriverManager.getConnection(URI, "root", "password");
    }

}
