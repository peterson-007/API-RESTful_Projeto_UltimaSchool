package org.crmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private Long numeroCliente;
    private String nome;
    private List<ContatoDTO> contatos;
    private List<EnderecoDTO> enderecos;
}
