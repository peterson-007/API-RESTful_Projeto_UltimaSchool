package org.crmweb.service;

import org.crmweb.dto.CadastroInteracaoClienteDTO;
import org.crmweb.dto.ClienteCadastroDTO;
import org.crmweb.dto.InteracaoClienteDTO;
import org.crmweb.models.Cliente;
import org.crmweb.models.InteracaoCliente;
import org.crmweb.repositories.ClienteRepository;
import org.crmweb.repositories.InteracaoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class InteracaoClienteServiceImpl implements InteracaoClienteService {

    private final ClienteRepository clienteRepository;
    private final InteracaoClienteRepository interacaoClienteRepository;

    @Autowired
    public InteracaoClienteServiceImpl(ClienteRepository clienteRepository, InteracaoClienteRepository interacaoClienteRepository) {
        this.clienteRepository = clienteRepository;
        this.interacaoClienteRepository = interacaoClienteRepository;
    }

    @Transactional
    @Override
    public void registrarInteracao(CadastroInteracaoClienteDTO interacaoClienteDTO){

        InteracaoCliente interacaoCliente = converterCadastroInteracao(interacaoClienteDTO);
        Cliente cliente = clienteRepository.findByNumeroCliente(interacaoClienteDTO.getNumeroCliente());
        interacaoCliente.setCliente(cliente);
        interacaoClienteRepository.save(interacaoCliente);
    }

    @Override
    public InteracaoCliente converterInteracao(InteracaoClienteDTO interacaoClienteDTO) {

        InteracaoCliente interacaoCliente = new InteracaoCliente();

        Cliente cliente = clienteRepository.findByNumeroCliente(interacaoClienteDTO.getNumeroCliente());
        interacaoCliente.setCliente(cliente);
        interacaoCliente.setDescricao(interacaoClienteDTO.getDescricao());
        interacaoCliente.setDataHora(interacaoClienteDTO.getDataHora());

        return interacaoCliente;
    }

    @Override
    public InteracaoClienteDTO converterParaDTO(InteracaoCliente interacaoCliente) {
        InteracaoClienteDTO interacaoClienteDTO = new InteracaoClienteDTO();

        interacaoClienteDTO.setId(interacaoCliente.getId());
        interacaoClienteDTO.setNumeroCliente(interacaoCliente.getCliente().getNumeroCliente());
        interacaoClienteDTO.setDescricao(interacaoCliente.getDescricao());
        interacaoClienteDTO.setDataHora(interacaoCliente.getDataHora());

        return interacaoClienteDTO;
    }


    private InteracaoCliente converterCadastroInteracao(CadastroInteracaoClienteDTO interacaoClienteDTO){

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

    @Override
    public Optional<InteracaoCliente> buscarInteracaoClientePorId(Long interacaoClienteId){
        return interacaoClienteRepository.findById(interacaoClienteId);
    }

    @Override
    public List<InteracaoCliente> buscarTodasInteracoes(){
        return interacaoClienteRepository.findAll();
    }

    @Override
    public InteracaoCliente converterInteracao(InteracaoCliente interacaoClienteDTO) {
        return null;
    }

    @Transactional
    @Override
    public void atualizarInteracaoCliente(Long idInteracaoCliente, CadastroInteracaoClienteDTO cadastroInteracaoDTO) throws Exception {
        Optional<InteracaoCliente> interacaoClienteOptional = interacaoClienteRepository.findById(idInteracaoCliente);

        if(interacaoClienteOptional.isPresent()){
            InteracaoCliente interacaoCliente = interacaoClienteOptional.get();

            // Atualizar os campos relevantes com base nos dados do DTO
            Cliente cliente = clienteRepository.findByNumeroCliente(cadastroInteracaoDTO.getNumeroCliente());
            interacaoCliente.setCliente(cliente);
            interacaoCliente.setDescricao(cadastroInteracaoDTO.getDescricao());

            interacaoClienteRepository.save(interacaoCliente);

        } else{
            throw new Exception("Interação não encontrada");
        }
    }

    @Override
    public void excluirInteracaoCliente(Long interacaoClienteId) {
        try {
            interacaoClienteRepository.deleteById(interacaoClienteId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Interação com cliente não encontrada!");
        }
    }

}
