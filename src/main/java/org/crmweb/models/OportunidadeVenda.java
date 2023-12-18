package org.crmweb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "oportunidade_venda")
@Data
public class OportunidadeVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    private EstagioOportunidade estagio;
    @Column(name = "valor_estimado")
    private double valorEstimado;

    public enum EstagioOportunidade {
        PROSPECT,
        QUALIFICACAO,
        PROPOSTA,
        FECHAMENTO
    }
}
