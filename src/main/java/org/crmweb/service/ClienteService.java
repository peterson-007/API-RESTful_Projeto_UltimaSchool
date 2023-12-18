package org.crmweb.service;

import org.crmweb.dto.ClienteDTO;

import java.sql.SQLException;

public interface ClienteService {

    void cadastrarCliente(ClienteDTO clienteDTO) throws SQLException;
}
