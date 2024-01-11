package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroOportunidadeVendaDTO {
    @NotBlank(message = "O número do cliente é obrigatório")
    private Long numeroCliente;
    @NotBlank(message = "O estágio da venda é obrigatório")
    private String estagio;
    @NotBlank(message = "O valor estimado da venda é obrigatório")
    private double valorEstimado;
}
