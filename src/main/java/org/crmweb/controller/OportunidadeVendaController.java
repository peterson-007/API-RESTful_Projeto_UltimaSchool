package org.crmweb.controller;

import org.crmweb.dto.OportunidadeVendaDTO;
import org.crmweb.models.OportunidadeVenda;
import org.crmweb.models.OportunidadeVenda.EstagioOportunidade;
import org.crmweb.service.OportunidadeVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeVendaController {

    @Autowired
    private OportunidadeVendaService oportunidadeVendaService;

    @PostMapping("/criarOportunidadeVenda")
    public ResponseEntity<Void> criarOportunidadeVenda(@RequestBody OportunidadeVendaDTO oportunidadeVendaDTO,
                                                       @RequestParam EstagioOportunidade estagio){
        oportunidadeVendaService.criarOportunidadeVenda(oportunidadeVendaDTO, estagio);
        return ResponseEntity.ok().build();
    }
}
