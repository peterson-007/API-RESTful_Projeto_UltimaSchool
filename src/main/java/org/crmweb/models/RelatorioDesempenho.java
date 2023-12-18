package org.crmweb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "relatorio_desempenho")
@Data
public class RelatorioDesempenho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total_interacoes")
    private int totalInteracoes;
    @Column(name = "total_oportunidades")
    private int totalOportunidades;
    @Column(name = "valor_total_oportunidades")
    private double valorTotalOportunidades;
}
