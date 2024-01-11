package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO {

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;
    @NotBlank(message = "o email é obrigatório")
    private String email;
}
