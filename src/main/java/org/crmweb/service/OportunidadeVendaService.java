package org.crmweb.service;

import org.crmweb.dto.CadastroOportunidadeVendaDTO;
import org.crmweb.dto.OpotunidadeVendaDTO;
import org.crmweb.models.OportunidadeVenda;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OportunidadeVendaService {

    void criarOportunidadeVenda(CadastroOportunidadeVendaDTO oportunidadeVendaDTO, OportunidadeVenda.EstagioOportunidade estagio);
    Optional<OportunidadeVenda> buscarOportunidadeVendaPorId(Long oportunidadeVendaId);
    List<OportunidadeVenda> buscarTodasOportunidadesDeVenda();
    OpotunidadeVendaDTO converterParaDTO(OportunidadeVenda oportunidadeVenda);
    void atualizarOportunidadeVenda(Long idOportunidadeVenda, CadastroOportunidadeVendaDTO cadastroOportunidadeVendaDTO, OportunidadeVenda.EstagioOportunidade estagio) throws Exception;
    void excluirOportunidadeVenda(Long oportunidadeVendaId);
}
