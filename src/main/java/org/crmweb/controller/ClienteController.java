package org.crmweb.controller;

import org.crmweb.dto.ClienteDTO;
import org.crmweb.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrarCliente")
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteDTO clienteDTO){

        try{
            clienteService.cadastrarCliente(clienteDTO);
            return new ResponseEntity<>("Cliente salvo com sucesso!", HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>("Erro ao cadastrar cliente!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
