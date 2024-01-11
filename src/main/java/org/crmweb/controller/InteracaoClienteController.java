package org.crmweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.crmweb.dto.CadastroInteracaoClienteDTO;
import org.crmweb.models.InteracaoCliente;
import org.crmweb.service.InteracaoClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interacoes")
public class InteracaoClienteController {

    private final InteracaoClienteService interacaoClienteService;

    public InteracaoClienteController(InteracaoClienteService interacaoClienteService) {
        this.interacaoClienteService = interacaoClienteService;
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar Interação com o Cliente", description = "Endpoint para registrar uma interação com o cliente")
    public ResponseEntity<Void> registrarInteracao(@RequestBody CadastroInteracaoClienteDTO interacaoClienteDTO){
        interacaoClienteService.registrarInteracao(interacaoClienteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{interacaoClienteId}")
    @Operation(summary = "Buscar Interação com Cliente por ID", description = "Endpoint para buscar uma interação com o cliente pelo ID")
    public ResponseEntity<InteracaoCliente> buscarInteracaoPorId(
            @Parameter(
                    name = "id",
                    description = "ID da interação com cliente a ser buscada",
                    example = "1",
                    required = true
            )@PathVariable Long interacaoClienteId) {
        Optional<InteracaoCliente> interacaoOptional = interacaoClienteService.buscarInteracaoClientePorId(interacaoClienteId);

        return interacaoOptional
                .map(interacao -> ResponseEntity.ok().body(interacaoClienteService.converterInteracao(interacao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarTodasInteracoes")
    @Operation(summary = "Buscar Todas as Interações com clientes", description = "Endpoint para buscar todas as interações com clientes")
    public ResponseEntity<List<Object>> buscarTodasInteracoes(){
        List<Object> interacoesDTO = interacaoClienteService.buscarTodasInteracoes()
                .stream()
                .map(interacaoClienteService::converterParaDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(interacoesDTO);
    }

    @PutMapping("/{interacaoClienteId}")
    @Operation(summary = "Atualizar Interação com Cliente", description = "Endpoint para atualizar interação com cliente por ID")
    public ResponseEntity<String> atualizarInteracaoCliente(
            @Parameter(
                    name = "id",
                    description = "ID da interação com cliente a ser atualizada",
                    example = "1",
                    required = true
            )@PathVariable Long interacaoClienteId, @RequestBody CadastroInteracaoClienteDTO cadastroInteracaoDTO) {
        try {
            interacaoClienteService.atualizarInteracaoCliente(interacaoClienteId, cadastroInteracaoDTO);
            return new ResponseEntity<>("Interação com cliente atualizada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar interação: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{interacaoClienteId}")
    @Operation(summary = "Excluir Interação com Cliente", description = "Endpoint para excluir interação com cliente")
    public ResponseEntity<String> excluirCliente(
            @Parameter(
                    name = "id",
                    description = "ID da interação com cliente a ser excluida",
                    example = "1",
                    required = true
            )@PathVariable Long interacaoClienteId) {
        try {
            interacaoClienteService.excluirInteracaoCliente(interacaoClienteId);
            return new ResponseEntity<>("Interação com cliente excluída com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
