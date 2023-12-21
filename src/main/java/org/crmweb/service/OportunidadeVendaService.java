package org.crmweb.service;

import org.crmweb.dto.OportunidadeVendaDTO;
import org.crmweb.models.OportunidadeVenda;

public interface OportunidadeVendaService {

    void criarOportunidadeVenda(OportunidadeVendaDTO oportunidadeVendaDTO, OportunidadeVenda.EstagioOportunidade estagio);
}
