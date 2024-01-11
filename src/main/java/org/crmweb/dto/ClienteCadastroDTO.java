package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCadastroDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private List<ContatoDTO> contatos;
    private List<EnderecoDTO> enderecos;
}
