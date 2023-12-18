package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDesempenhoDTO {

    @NotBlank(message = "O total de interações é obrigatório")
    private int totalInteracoes;
    @NotBlank(message = "O total de oportunidades é obrigatório")
    private int totalOportunidades;
    @NotBlank(message = "O valor total das oportunidades é obrigatório")
    private double valorTotalOportunidades;
}
