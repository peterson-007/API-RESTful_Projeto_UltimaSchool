package org.crmweb.service;

import org.crmweb.dto.CadastroInteracaoClienteDTO;
import org.crmweb.dto.CadastroOportunidadeVendaDTO;
import org.crmweb.dto.OpotunidadeVendaDTO;
import org.crmweb.models.Cliente;
import org.crmweb.models.InteracaoCliente;
import org.crmweb.models.OportunidadeVenda;
import org.crmweb.repositories.ClienteRepository;
import org.crmweb.repositories.OportunidadeVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.crmweb.models.OportunidadeVenda.*;

@Service
public class OportunidadeVendaServiceImpl implements OportunidadeVendaService {

    private final ClienteRepository clienteRepository;
    private final OportunidadeVendaRepository oportunidadeVendaRepository;

    @Autowired
    public OportunidadeVendaServiceImpl(ClienteRepository clienteRepository, OportunidadeVendaRepository oportunidadeVendaRepository) {
        this.clienteRepository = clienteRepository;
        this.oportunidadeVendaRepository = oportunidadeVendaRepository;
    }

    @Override
    public void criarOportunidadeVenda(CadastroOportunidadeVendaDTO oportunidadeVendaDTO, EstagioOportunidade estagio){

        OportunidadeVenda oportunidadeVenda = new OportunidadeVenda();
        Cliente cliente = clienteRepository.findByNumeroCliente(oportunidadeVendaDTO.getNumeroCliente());
        oportunidadeVenda.setCliente(cliente);
        oportunidadeVenda.setEstagio(estagio);
        oportunidadeVenda.setValorEstimado(oportunidadeVendaDTO.getValorEstimado());
        oportunidadeVendaRepository.save(oportunidadeVenda);
    }

    @Override
    public Optional<OportunidadeVenda> buscarOportunidadeVendaPorId(Long oportunidadeVendaId){
        return oportunidadeVendaRepository.findById(oportunidadeVendaId);
    }

    @Override
    public List<OportunidadeVenda> buscarTodasOportunidadesDeVenda(){
        return oportunidadeVendaRepository.findAll();
    }

    @Override
    public OpotunidadeVendaDTO converterParaDTO(OportunidadeVenda oportunidadeVenda){

        OpotunidadeVendaDTO oportunidadeVendaDTO = new OpotunidadeVendaDTO();

        oportunidadeVendaDTO.setNomeCliente(oportunidadeVenda.getCliente().getNome());
        //converter estagio para String
        String estagioString = oportunidadeVenda.getEstagio().name();
        oportunidadeVendaDTO.setEstagio(estagioString);
        oportunidadeVendaDTO.setValorEstimado(oportunidadeVenda.getValorEstimado());

        return oportunidadeVendaDTO;
    }

    @Transactional
    @Override
    public void atualizarOportunidadeVenda(Long idOportunidadeVenda, CadastroOportunidadeVendaDTO cadastroOportunidadeVendaDTO,EstagioOportunidade estagio) throws Exception {
        Optional<OportunidadeVenda> oportunidadeVendaOptional = oportunidadeVendaRepository.findById(idOportunidadeVenda);

        if(oportunidadeVendaOptional.isPresent()){
            OportunidadeVenda oportunidadeVenda = oportunidadeVendaOptional.get();

            // Atualizar os campos relevantes com base nos dados do DTO
            Cliente cliente = clienteRepository.findByNumeroCliente(cadastroOportunidadeVendaDTO.getNumeroCliente());
            oportunidadeVenda.setCliente(cliente);
            oportunidadeVenda.setEstagio(estagio);
            oportunidadeVenda.setValorEstimado(cadastroOportunidadeVendaDTO.getValorEstimado());

            oportunidadeVendaRepository.save(oportunidadeVenda);

        } else{
            throw new Exception("Oportunidade de venda não encontrada");
        }
    }

    @Override
    public void excluirOportunidadeVenda(Long oportunidadeVendaId) {
        try {
            oportunidadeVendaRepository.deleteById(oportunidadeVendaId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Oportunidade de venda não encontrada!");
        }
    }

}
