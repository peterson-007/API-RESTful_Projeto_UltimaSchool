package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpotunidadeVendaDTO {

    private String nomeCliente;
    private String estagio;
    private double valorEstimado;
}
