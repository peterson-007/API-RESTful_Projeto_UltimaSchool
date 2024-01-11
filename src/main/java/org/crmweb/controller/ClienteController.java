package org.crmweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.crmweb.dto.ClienteCadastroDTO;
import org.crmweb.dto.RelatorioDesempenhoDTO;
import org.crmweb.models.Cliente;
import org.crmweb.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrarCliente")
    @Operation(summary = "Cadastrar Cliente", description = "Endpoint cadastrar um cliente.")
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteCadastroDTO clienteDTO) {
        try {
            clienteService.cadastrarCliente(clienteDTO);
            return new ResponseEntity<>("Cliente salvo com sucesso!", HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>("Erro ao cadastrar cliente! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/relatorioDeDesempenho")
    @Operation(summary = "Relatório de Desempenho", description = "Endpoint Relatório de Desempenho com:" +
            " Número total de interações registradas, quantidade de oportunidades de vendas em cada estágio" +
            " e valor total com a soma de todas as oportunidades")
    public ResponseEntity<RelatorioDesempenhoDTO> relatorioDesempenho() {
        RelatorioDesempenhoDTO relatorioDesempenhoDTO = clienteService.gerarRelatorioDesempenho();
        return ResponseEntity.ok(relatorioDesempenhoDTO);
    }

    @GetMapping("/{clienteId}")
    @Operation(summary = "Buscar Cliente por ID", description = "Endpoint para buscar um cliente pelo seu ID")
    public ResponseEntity<Object> buscarClientePorId(
            @Parameter(
                    name = "id",
                    description = "ID do cliente a ser buscado",
                    example = "1",
                    required = true
            )@PathVariable Long clienteId) {
        Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(clienteId);

        return clienteOptional
                .map(cliente -> ResponseEntity.ok().body(clienteService.converterClienteDTO(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarTodosClientes")
    @Operation(summary = "Buscar Todos os Clientes", description = "Endpoint para buscar todos os clientes")
    public ResponseEntity<List<Object>> buscarTodosClientes() {
        List<Object> clientesDTO = clienteService.buscarTodosClientes()
                .stream()
                .map(clienteService::converterClienteDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientesDTO);
    }

    @PutMapping("/{clienteId}")
    @Operation(summary = "Atualizar Cliente", description = "Endpoint para atualizar um registro de cliente")
    public ResponseEntity<String> atualizarCliente(
            @Parameter(
                    name = "id",
                    description = "ID do cliente a ser atualizado",
                    example = "1",
                    required = true
            )@PathVariable Long clienteId, @RequestBody ClienteCadastroDTO clienteDTO) {
        try {
            clienteService.atualizarCliente(clienteId, clienteDTO);
            return new ResponseEntity<>("Cliente atualizado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{clienteId}")
    @Operation(summary = "Excluir cliente", description = "Endpoint para excluir um registro de cliente")
    public ResponseEntity<String> excluirCliente(
            @Parameter(
                    name = "id",
                    description = "ID do cliente a ser excluído",
                    example = "1",
                    required = true
            )@PathVariable Long clienteId) {
        try {
            clienteService.excluirCliente(clienteId);
            return new ResponseEntity<>("Cliente excluído com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
