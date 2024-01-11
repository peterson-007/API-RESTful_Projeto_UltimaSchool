package org.crmweb.service;

import org.crmweb.dto.CadastroInteracaoClienteDTO;
import org.crmweb.dto.InteracaoClienteDTO;
import org.crmweb.models.InteracaoCliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InteracaoClienteService {

    void registrarInteracao(CadastroInteracaoClienteDTO interacaoClienteDTO);

    InteracaoCliente converterInteracao(InteracaoClienteDTO interacaoClienteDTO);

    Optional<InteracaoCliente> buscarInteracaoClientePorId(Long interacaoClienteId);
    List<InteracaoCliente> buscarTodasInteracoes();
    InteracaoCliente converterInteracao(InteracaoCliente interacaoClienteDTO);
    InteracaoClienteDTO converterParaDTO(InteracaoCliente interacaoCliente);
    void atualizarInteracaoCliente(Long idInteracaoCliente,  CadastroInteracaoClienteDTO cadastroInteracaoDTO) throws Exception;
    void excluirInteracaoCliente(Long interacaoClienteId);
}
