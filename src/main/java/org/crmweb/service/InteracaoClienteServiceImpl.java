package org.crmweb.service;

import org.crmweb.dto.InteracaoClienteDTO;
import org.crmweb.models.Cliente;
import org.crmweb.models.InteracaoCliente;
import org.crmweb.repositories.ClienteRepository;
import org.crmweb.repositories.InteracaoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class InteracaoClienteServiceImpl {

    private final ClienteRepository clienteRepository;
    private final InteracaoClienteRepository interacaoClienteRepository;

    @Autowired
    public InteracaoClienteServiceImpl(ClienteRepository clienteRepository, InteracaoClienteRepository interacaoClienteRepository) {
        this.clienteRepository = clienteRepository;
        this.interacaoClienteRepository = interacaoClienteRepository;
    }

    public void registrarInteracao(InteracaoClienteDTO interacaoClienteDTO){

        InteracaoCliente interacaoCliente = converterInteracao(interacaoClienteDTO);
        Cliente cliente = clienteRepository.findByNumeroCliente(interacaoClienteDTO.getNumeroCliente());
        interacaoCliente.setCliente(cliente);
        interacaoClienteRepository.save(interacaoCliente);
    }


    private InteracaoCliente converterInteracao(InteracaoClienteDTO interacaoClienteDTO){

        InteracaoCliente interacaoCliente = new InteracaoCliente();
        interacaoCliente.setDescricao(interacaoClienteDTO.getDescricao());
        interacaoCliente.setDataHora(obterDataAtual());

        return interacaoCliente;
    }

    private LocalDateTime obterDataAtual(){

        // Obtém a data e hora atuais
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        // Define um formato desejado para a data e hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Formata a data e hora
        String dataHoraFormatada = dataHoraAtual.format(formatter);
        // Converte a string formatada de volta para LocalDateTime
        LocalDateTime dataHoraFormatadaObjeto = LocalDateTime.parse(dataHoraFormatada, formatter);
        return  dataHoraFormatadaObjeto;
    }
}
