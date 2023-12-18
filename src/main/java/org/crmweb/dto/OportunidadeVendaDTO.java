package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OportunidadeVendaDTO {

    private int idCliente;
    @NotBlank(message = "O estágio da veda é obrigatório")
    private String estagio;
    @NotBlank(message = "O valor estimado da venda é obrigatório")
    private double valorEstimado;
}
