package org.crmweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.crmweb.dto.CadastroInteracaoClienteDTO;
import org.crmweb.dto.CadastroOportunidadeVendaDTO;
import org.crmweb.dto.OpotunidadeVendaDTO;
import org.crmweb.models.OportunidadeVenda;
import org.crmweb.models.OportunidadeVenda.EstagioOportunidade;
import org.crmweb.service.OportunidadeVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeVendaController {

    @Autowired
    private OportunidadeVendaService oportunidadeVendaService;

    @PostMapping("/criarOportunidadeVenda")
    @Operation(summary = "Cadastrar uma Oportunidade de Venda", description = "Endpoint para cadastrar uma oportunidade de venda")
    public ResponseEntity<Void> criarOportunidadeVenda(@RequestBody CadastroOportunidadeVendaDTO oportunidadeVendaDTO,
                                                       @RequestParam EstagioOportunidade estagio){
        oportunidadeVendaService.criarOportunidadeVenda(oportunidadeVendaDTO, estagio);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{oportunidadeVendaId}")
    @Operation(summary = "Buscar Oportunidade de Venda por ID", description = "Endpoint para buscar oportunidade de venda pelo ID")
    public ResponseEntity<OpotunidadeVendaDTO> buscarOportunidadeVendaPorId(@PathVariable Long oportunidadeVendaId){
        Optional<OportunidadeVenda> oportunidadeOptional = oportunidadeVendaService.buscarOportunidadeVendaPorId(oportunidadeVendaId);

        return oportunidadeOptional
                .map(oportunidade -> ResponseEntity.ok().body(oportunidadeVendaService.converterParaDTO(oportunidade)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarTodasOportunidadesDeVenda")
    @Operation(summary = "Buscar Todas as Oportunidades de Venda", description = "Endpoint para buscar todas as oportunidades de venda")
    public ResponseEntity<List<Object>> buscarTodasOportunidadesDeVenda(){
        List<Object> oportunidadesDTO = oportunidadeVendaService.buscarTodasOportunidadesDeVenda()
                .stream()
                .map(oportunidadeVendaService::converterParaDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(oportunidadesDTO);
    }

    @PutMapping("/{oportunidadeVendaId}")
    @Operation(summary = "Atualizar Oportunidade de Venda", description = "Endpoint para atualizar uma oportunidade de venda")
    public ResponseEntity<String> atualizarOportunidadeVenda(@PathVariable Long oportunidadeVendaId, @RequestBody CadastroOportunidadeVendaDTO oportunidadeVendaDTO,@RequestParam EstagioOportunidade estagio) {
        try {
            oportunidadeVendaService.atualizarOportunidadeVenda(oportunidadeVendaId, oportunidadeVendaDTO,estagio);
            return new ResponseEntity<>("Oportunidade de venda atualizada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar a oportuniade de venda: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{oportunidadeVendaId}")
    @Operation(summary = "Excluir Oportunidade de Venda", description = "Endpoint para excluir uma oportunidade de venda")
    public ResponseEntity<String> excluirOportunidadeVenda(@PathVariable Long oportunidadeVendaId) {
        try {
            oportunidadeVendaService.excluirOportunidadeVenda(oportunidadeVendaId);
            return new ResponseEntity<>("Oportunidade de venda excluída com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
