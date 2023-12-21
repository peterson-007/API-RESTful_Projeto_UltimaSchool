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
    @NotBlank(message = "O número do cliente é obrigatório")
    private Long numeroCliente;
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;
}
