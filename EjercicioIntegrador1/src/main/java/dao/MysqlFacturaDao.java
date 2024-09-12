package dao;

import model.Factura;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlFacturaDao implements FacturaDao {
    private final Connection conn;

    public MysqlFacturaDao(ConnectionManager connectionManager) throws SQLException {
        this.conn = connectionManager.getConnection();
    }

    @Override
    public void createTable() throws SQLException {
        conn.setAutoCommit(false);
        String table = "CREATE TABLE factura (" +
                "idFactura int NOT NULL," +
                "idCliente int NOT NULL," +
                "CONSTRAINT factura_pk PRIMARY KEY (idFactura))";
        String addConstraint = "ALTER TABLE factura " +
                "ADD CONSTRAINT facturaCliente " +
                "FOREIGN KEY facturaCliente (idCliente)" +
                "REFERENCES cliente (idCliente)" +
                ";";
        conn.prepareStatement(table).execute();
        conn.prepareStatement(addConstraint).execute();
        conn.commit();
    }

    @Override
    public void readCSV(String path) {
        CSVParser parser;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord record : parser) {
                int idFactura = Integer.parseInt(record.get("idFactura"));
                int idCliente = Integer.parseInt(record.get("idCliente"));
                Factura factura = new Factura(idFactura, idCliente);
                this.addFactura(factura);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFactura(Factura factura) throws SQLException {
        conn.setAutoCommit(false);
        String insert = "INSERT INTO factura (idFactura,idCliente) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, factura.getIdFactura());
        ps.setInt(2, factura.getIdCliente());
        ps.executeUpdate();
        ps.close();
        conn.commit();
    }
}