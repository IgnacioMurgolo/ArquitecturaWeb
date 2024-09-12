import dao.*;
import service.ClienteService;
import service.FacturaProductoService;
import service.FacturaService;
import service.ProductoService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Inicio el driver
        registerMySqlDriver();

        //Instancio la conexion Mysql
        ConnectionManager conn = new MysqlConnectionManager();

        //Instancio la clase DAO Mysql de cada entidad
        ClienteDao clienteDao = new MysqlClienteDao(conn);
        FacturaDao facturaDao = new MysqlFacturaDao(conn);
        FacturaProductoDao facturaProductoDao = new MysqlFacturaProductoDao(conn);
        ProductoDao productoDao = new MysqlProductoDao(conn);

        //Instancio la clase de servicios de cada entidad
        ClienteService clienteService = new ClienteService(clienteDao);
        FacturaService facturaService = new FacturaService(facturaDao);
        FacturaProductoService facturaProductoService = new FacturaProductoService(facturaProductoDao);
        ProductoService productoService = new ProductoService(productoDao);

        //Guardo los path de los archivos csv
        String pathCliente = "src/main/resources/csv/clientes.csv";
        String pathFactura = "src/main/resources/csv/facturas.csv";
        String pathProducto = "src/main/resources/csv/productos.csv";
        String pathFacturaProducto = "src/main/resources/csv/facturas-productos.csv";

        // Creo las tablas en la base de datos
        //clienteService.createTable();
        // facturaService.createTable();
        // facturaProductoService.createTable();
        // productoService.createTable();

        // Inserto los datos en las tablas
        //clienteService.addClientes(pathCliente);
        // facturaService.addFacturas(pathFactura);
        //facturaProductoService.addFacturaProducto(pathFacturaProducto);
        // productoService.addProductos(pathProducto);

        // Imprimo servicio del ejercicio 3: Producto de mayor recaudación
        productoService.obtenerProductoConMasRecaudacion();

        // Imprimo servicio del ejercicio 4: lista de clientes ordenada se más facturacion a menos
        clienteService.getClientesOrdenadosPorFacturacion();

    }

    private static void registerMySqlDriver() {
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}