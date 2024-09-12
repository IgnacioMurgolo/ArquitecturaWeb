package dao;

import model.FacturaProducto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MysqlFacturaProductoDao implements FacturaProductoDao {
    private final Connection conn;

    public MysqlFacturaProductoDao(ConnectionManager connectionManager) throws SQLException {
        this.conn = connectionManager.getConnection();
    }


    @Override
    public void createTable() throws SQLException {
        conn.setAutoCommit(false);
        String table = "CREATE TABLE IF NOT EXISTS facturaProducto(" +
                "idFactura int  NOT NULL," +
                "idProducto int  NOT NULL," +
                "cantidad int  NOT NULL," +
                "CONSTRAINT facturaProducto_pk PRIMARY KEY (idFactura, idProducto)" +
                ");";
        String addConstraint1 = "ALTER TABLE facturaProducto " +
                "ADD CONSTRAINT facturaProducto_Factura " +
                "FOREIGN KEY (idFactura) " +
                "REFERENCES factura (idFactura)" +
                ";";
        String addConstraint2 = "ALTER TABLE facturaProducto " +
                "ADD CONSTRAINT facturaProducto_Producto " +
                "FOREIGN KEY (idProducto) " +
                "REFERENCES producto (idProducto)" +
                ";";
        conn.prepareStatement(table).execute();
        conn.prepareStatement(addConstraint1).execute();
        conn.prepareStatement(addConstraint2).execute();
        conn.commit();
    }

    @Override
    public void readCSV(String path) {
        CSVParser parser;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord row : parser) {
                int idFactura = Integer.parseInt(row.get("idFactura"));
                int idProducto = Integer.parseInt(row.get("idProducto"));
                int cantidad = Integer.parseInt(row.get("cantidad"));
                FacturaProducto facturaProducto = new FacturaProducto(idFactura, idProducto, cantidad);
                this.addFacturaProducto(facturaProducto);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFacturaProducto(FacturaProducto facturaProducto) throws SQLException{
        conn.setAutoCommit(false);
        String insert = "INSERT INTO facturaProducto (idFactura, idProducto, cantidad) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, facturaProducto.getIdFactura());
        ps.setInt(2, facturaProducto.getIdProducto());
        ps.setInt(3, facturaProducto.getCantidad());
        ps.executeUpdate();
        ps.close();
        conn.commit();
    }
}
