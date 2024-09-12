package service;

import dao.FacturaDao;

import java.sql.SQLException;

public class FacturaService {
    FacturaDao factura;

    public FacturaService(FacturaDao factura) {
        this.factura = factura;
    }

    public void addFacturas(String path) {
        factura.readCSV(path);
    }

    public void createTable() throws SQLException {
        factura.createTable();
    }
}
