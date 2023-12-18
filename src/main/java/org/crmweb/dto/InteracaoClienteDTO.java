package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteracaoClienteDTO {

    private int idCliente;
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;
    @NotBlank(message = "Obrigatório inserir data e hora")
    private LocalDateTime dataHora;
}
