package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "O nome da rua é obrigatório")
    private String rua;
    @NotBlank(message = "O número da casa é obrigatório")
    private String numero;
    private String complemento;
    @NotBlank(message = "O nome do bairro é obrigatório")
    private String bairro;
    @NotBlank(message = "O nome da cidade é obrigatório")
    private String cidade;
    @NotBlank(message = "O CEP é obrigatório")
    private String cep;
}
