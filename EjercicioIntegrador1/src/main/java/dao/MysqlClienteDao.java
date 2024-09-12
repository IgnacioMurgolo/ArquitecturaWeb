package dao;

import dto.ClienteDTO;
import model.Cliente;
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
import java.util.List;

public class MysqlClienteDao implements ClienteDao {
    private final Connection conn;

    public MysqlClienteDao(ConnectionManager conectionManager) throws SQLException {
        this.conn = conectionManager.getConnection();
    }

    @Override
    public void createTable() throws SQLException {
        conn.setAutoCommit(false);
        String table = "CREATE TABLE cliente(" +
                "idCliente INT," +
                "nombre VARCHAR(500)," +
                "email VARCHAR(150)," +
                "PRIMARY KEY (idCliente))";
        conn.prepareStatement(table).execute();
        conn.commit();
    }

    @Override
    public void readCSV(String path) {
        CSVParser parser;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord row : parser) {
                int idCliente = Integer.parseInt(row.get("idCliente"));
                String nombre = row.get("nombre");
                String email = row.get("email");
                Cliente cliente = new Cliente(idCliente, nombre, email);
                this.addCliente(cliente);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addCliente(Cliente cliente) throws SQLException {
        conn.setAutoCommit(false);
        String insert = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, cliente.getIdCliente());
        ps.setString(2, cliente.getNombre());
        ps.setString(3, cliente.getEmail());
        ps.executeUpdate();
        ps.close();
        conn.commit();
    }

    @Override
    public List<ClienteDTO> getClientesOrdenadosPorFacturacion() {
        List<ClienteDTO> clientes = new ArrayList<>();
        try {
            String select = "SELECT c.nombre, c.email, sum(fp.cantidad * p.valor) AS montoFacturado " +
                    "FROM " +
                    "cliente c " +
                    "JOIN " +
                    "factura f USING (idCliente) " +
                    "JOIN " +
                    "facturaProducto fp USING (idFactura) " +
                    "JOIN " +
                    "producto p USING (idProducto) " +
                    "group by c.idCliente, c.nombre, c.email " +
                    "order by montoFacturado DESC";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO(rs.getString("nombre"), rs.getString("email"), rs.getFloat("montoFacturado"));
                clientes.add(cliente);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

}
