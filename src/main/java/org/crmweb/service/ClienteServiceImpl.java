package org.crmweb.service;

import org.crmweb.dto.ClienteDTO;
import org.crmweb.models.Cliente;
import org.crmweb.models.Contato;
import org.crmweb.models.Endereco;
import org.crmweb.repositories.ClienteRepository;
import org.crmweb.repositories.ContatoRepository;
import org.crmweb.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;
    private ContatoRepository contatoRepository;
    private EnderecoRepository enderecoRepository;
    private ClienteService clienteService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ContatoRepository contatoRepository, EnderecoRepository enderecoRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.contatoRepository = contatoRepository;
        this.enderecoRepository = enderecoRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public void cadastrarCliente(ClienteDTO clienteDTO) throws SQLException {

        Cliente cliente = converterClienteDTO(clienteDTO);
        //setar o número gerado
        cliente.setNumeroCliente(gerarNumeroCliente());

        clienteRepository.save(cliente);

        for(Contato contato: cliente.getContatos()){
            contatoRepository.save(contato);
        }

        for(Endereco endereco : cliente.getEnderecos()){
            enderecoRepository.save(endereco);
        }

    }

    private Long gerarNumeroCliente() {
        // Gera um número aleatório entre 1 e 99999 (5 dígitos)
        return (long) (Math.random() * 90000 + 10000);
    }


    private Cliente converterClienteDTO(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());

        // Criação da lista de Contatos
        List<Contato> contatos = clienteDTO.getContatos().stream()
                .map(contatoDTO -> {
                    Contato contato = new Contato();
                    contato.setTelefone(contatoDTO.getTelefone());
                    contato.setEmail(contatoDTO.getEmail());
                    contato.setCliente(cliente); // Associação inversa ao Cliente
                    return contato;
                })
                .collect(Collectors.toList());

        cliente.setContatos(contatos);

        // Criação da lista de Enderecos
        List<Endereco> enderecos = clienteDTO.getEnderecos().stream()
                .map(enderecoDTO -> {
                    Endereco endereco = new Endereco();
                    endereco.setRua(enderecoDTO.getRua());
                    endereco.setNumero(enderecoDTO.getNumero());
                    endereco.setComplemento(enderecoDTO.getComplemento());
                    endereco.setBairro(enderecoDTO.getBairro());
                    endereco.setCidade(enderecoDTO.getCidade());
                    endereco.setCep(enderecoDTO.getCep());
                    endereco.setCliente(cliente); // Associação inversa ao Cliente
                    return endereco;
                })
                .collect(Collectors.toList());

        cliente.setEnderecos(enderecos);

        return cliente;
    }
}
