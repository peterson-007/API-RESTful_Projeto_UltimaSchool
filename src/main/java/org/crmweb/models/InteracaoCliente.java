package org.crmweb.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="interacao_cliente")
@Data
public class InteracaoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

}
