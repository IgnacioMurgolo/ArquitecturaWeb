package dao;

import dto.ClienteDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ClienteDao {

    void createTable() throws SQLException;

    void readCSV(String path);

    List<ClienteDTO> getClientesOrdenadosPorFacturacion() throws SQLException;

}
