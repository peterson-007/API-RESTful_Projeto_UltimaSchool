package org.crmweb.service;

import org.crmweb.dto.OportunidadeVendaDTO;
import org.crmweb.models.Cliente;
import org.crmweb.models.OportunidadeVenda;
import org.crmweb.repositories.ClienteRepository;
import org.crmweb.repositories.OportunidadeVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.crmweb.models.OportunidadeVenda.*;

@Service
public class OportunidadeVendaServiceImpl {

    private final ClienteRepository clienteRepository;
    private final OportunidadeVendaRepository oportunidadeVendaRepository;

    @Autowired
    public OportunidadeVendaServiceImpl(ClienteRepository clienteRepository, OportunidadeVendaRepository oportunidadeVendaRepository) {
        this.clienteRepository = clienteRepository;
        this.oportunidadeVendaRepository = oportunidadeVendaRepository;
    }

    public void criarOportunidadeVenda(OportunidadeVendaDTO oportunidadeVendaDTO, EstagioOportunidade estagio){

        OportunidadeVenda oportunidadeVenda = new OportunidadeVenda();
        Cliente cliente = clienteRepository.findByNumeroCliente(oportunidadeVendaDTO.getNumeroCliente());
        oportunidadeVenda.setCliente(cliente);
        oportunidadeVenda.setEstagio(estagio);
        oportunidadeVenda.setValorEstimado(oportunidadeVendaDTO.getValorEstimado());
        oportunidadeVendaRepository.save(oportunidadeVenda);
    }

}
