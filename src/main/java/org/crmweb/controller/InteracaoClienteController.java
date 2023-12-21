package org.crmweb.controller;

import org.crmweb.dto.InteracaoClienteDTO;
import org.crmweb.service.InteracaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interacoes")
public class InteracaoClienteController {

    @Autowired
    private InteracaoClienteService interacaoClienteService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarInteracao(@RequestBody InteracaoClienteDTO interacaoClienteDTO){
        interacaoClienteService.registrarInteracao(interacaoClienteDTO);
        return ResponseEntity.ok().build();
    }

}
