package service;

import dao.FacturaProductoDao;
import model.FacturaProducto;

import java.sql.SQLException;

public class FacturaProductoService {
    FacturaProductoDao facturaProducto;

    public FacturaProductoService(FacturaProductoDao facturaProducto) {
        this.facturaProducto = facturaProducto;
    }

    public void addFacturaProducto(String path) {
        facturaProducto.readCSV(path);
    }

    public void createTable() throws SQLException {
        facturaProducto.createTable();
    }
}
