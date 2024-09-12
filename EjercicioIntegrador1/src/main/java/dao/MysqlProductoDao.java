package dao;

import dto.ProductoDTO;
import model.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlProductoDao implements ProductoDao {
    private final Connection conn;

    public MysqlProductoDao(ConnectionManager connectionManager) throws SQLException {
        this.conn = connectionManager.getConnection();
    }

    @Override
    public void createTable() throws SQLException {
        conn.setAutoCommit(false);
        String table = "CREATE TABLE producto(" +
                "idProducto INT NOT NULL," +
                "nombre VARCHAR(200)," +
                "valor float(15,7)," +
                "CONSTRAINT Producto_pk PRIMARY KEY (idProducto))";
        conn.prepareStatement(table).execute();
        conn.commit();
    }

    @Override
    public void readCSV(String path) {
        CSVParser parser;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord row : parser) {
                int id = Integer.parseInt(row.get("idProducto"));
                String nombre = row.get("nombre");
                float value = Float.parseFloat(row.get("valor"));
                Producto producto = new Producto(id, nombre, value);
                this.addProducto(producto);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProducto(Producto prod) throws SQLException {
        conn.setAutoCommit(false);
        String insert = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, prod.getIdProducto());
        ps.setString(2, prod.getNombre());
        ps.setFloat(3, prod.getValor());
        ps.execute();
        ps.close();
        conn.commit();
    }

    @Override
    public ProductoDTO obtenerProductoConMasRecaudacion() {
        ProductoDTO producto = null;
        try {
            String select = "SELECT p.nombre, p.valor, sum(p.valor * fp.cantidad) AS recaudacion " +
                    "FROM producto p " +
                    "JOIN " +
                    "facturaProducto fp USING (idProducto) " +
                    "group by p.idProducto " +
                    "order by recaudacion desc " +
                    "limit 1";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producto = new ProductoDTO(rs.getString("nombre"), rs.getFloat("valor"), rs.getFloat("recaudacion"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
}




















