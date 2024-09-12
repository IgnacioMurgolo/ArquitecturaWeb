package service;

import dto.ClienteDTO;
import dao.ClienteDao;
import model.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    ClienteDao cliente;

    public ClienteService(ClienteDao cliente) {
        this.cliente = cliente;
    }

    public void addClientes(String path) {
        cliente.readCSV(path);
    }

    public void createTable() throws SQLException {
        cliente.createTable();
    }

    public void getClientesOrdenadosPorFacturacion() throws SQLException {
        List<ClienteDTO> clientes = cliente.getClientesOrdenadosPorFacturacion();
        System.out.println("Los clientes que mas facturaron son: ");
        for (ClienteDTO cliente : clientes) {
            System.out.println(cliente.toString());
        }
    }
}
