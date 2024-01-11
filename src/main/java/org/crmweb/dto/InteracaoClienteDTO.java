package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crmweb.models.Cliente;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteracaoClienteDTO {

    private Long id;
    private Long numeroCliente;
    private String descricao;
    private LocalDateTime dataHora;
}

