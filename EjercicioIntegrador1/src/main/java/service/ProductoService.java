package service;

import dao.ProductoDao;
import dto.ProductoDTO;
import model.Producto;

import java.sql.SQLException;

public class ProductoService {
    ProductoDao producto;

    public ProductoService(ProductoDao producto) {
        this.producto = producto;
    }

    public void addProductos(String path) {
        producto.readCSV(path);
    }

    public void createTable() throws SQLException {
        producto.createTable();
    }

    public void obtenerProductoConMasRecaudacion() throws SQLException {
        ProductoDTO prod = producto.obtenerProductoConMasRecaudacion();
        System.out.println("El producto con mayor recaudacion es: " + prod.toString());
    }
}
