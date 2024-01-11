package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDesempenhoDTO {
    private long totalInteracoes;
    private long totalEstagioProspect;
    private long totalEstagioQualificacao;
    private long totalEstagioProposta;
    private long totalEstagioFechamento;
    private double valorTotalOportunidades;
}
