package org.crmweb.service;

import org.crmweb.dto.ClienteCadastroDTO;
import org.crmweb.dto.ClienteDTO;
import org.crmweb.dto.RelatorioDesempenhoDTO;
import org.crmweb.models.Cliente;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public interface ClienteService {

    void cadastrarCliente(ClienteCadastroDTO clienteDTO) throws SQLException;

    Cliente converterClienteDTO(ClienteCadastroDTO clienteDTO);
    RelatorioDesempenhoDTO gerarRelatorioDesempenho();
    Optional<Cliente> buscarClientePorId(Long clienteId);
    Object converterClienteDTO(Cliente cliente);
    List<Cliente> buscarTodosClientes();
    void atualizarCliente(Long clienteId, ClienteCadastroDTO clienteDTO) throws Exception;
    void excluirCliente(Long clienteId);
}
