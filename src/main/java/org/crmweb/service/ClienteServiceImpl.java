package org.crmweb.service;

import org.crmweb.dto.*;
import org.crmweb.models.Cliente;
import org.crmweb.models.Contato;
import org.crmweb.models.Endereco;
import org.crmweb.models.OportunidadeVenda;
import org.crmweb.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final OportunidadeVendaRepository oportunidadeVendaRepository;
    private final InteracaoClienteRepository interacaoClienteRepository;
    private final ClienteRepository clienteRepository;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public ClienteServiceImpl(OportunidadeVendaRepository oportunidadeVendaRepository,
                              InteracaoClienteRepository interacaoClienteRepository,
                              ClienteRepository clienteRepository,
                              ContatoRepository contatoRepository,
                              EnderecoRepository enderecoRepository) {
        this.oportunidadeVendaRepository = oportunidadeVendaRepository;
        this.interacaoClienteRepository = interacaoClienteRepository;
        this.clienteRepository = clienteRepository;
        this.contatoRepository = contatoRepository;
        this.enderecoRepository = enderecoRepository;
    }
    @Transactional
    public void cadastrarCliente(ClienteCadastroDTO clienteDTO) throws SQLException {

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

    @Override
    public Optional<Cliente> buscarClientePorId(Long idCliente){
        return clienteRepository.findById(idCliente);
    }

    @Override
    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    private Long gerarNumeroCliente() {
        // Gera um número aleatório entre 1 e 99999 (5 dígitos)
        return (long) (Math.random() * 90000 + 10000);
    }

    @Override
    public Cliente converterClienteDTO(ClienteCadastroDTO clienteDTO){
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

    public RelatorioDesempenhoDTO gerarRelatorioDesempenho() {
        RelatorioDesempenhoDTO relatorioDesempenhoDTO = new RelatorioDesempenhoDTO();

        relatorioDesempenhoDTO.setTotalInteracoes(interacaoClienteRepository.count());

        relatorioDesempenhoDTO.setTotalEstagioProspect(oportunidadeVendaRepository.countByEstagio(OportunidadeVenda.EstagioOportunidade.PROSPECT));
        relatorioDesempenhoDTO.setTotalEstagioQualificacao(oportunidadeVendaRepository.countByEstagio(OportunidadeVenda.EstagioOportunidade.QUALIFICACAO));
        relatorioDesempenhoDTO.setTotalEstagioProposta(oportunidadeVendaRepository.countByEstagio(OportunidadeVenda.EstagioOportunidade.PROPOSTA));
        relatorioDesempenhoDTO.setTotalEstagioFechamento(oportunidadeVendaRepository.countByEstagio(OportunidadeVenda.EstagioOportunidade.FECHAMENTO));
        relatorioDesempenhoDTO.setValorTotalOportunidades(oportunidadeVendaRepository.sumValorEstimado());

        return relatorioDesempenhoDTO;
    }


    public ClienteDTO converterClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNumeroCliente(cliente.getNumeroCliente());
        clienteDTO.setNome(cliente.getNome());

        // Convertendo lista de Contatos
        List<ContatoDTO> contatosDTO = cliente.getContatos().stream()
                .map(this::converterContatoParaDTO)
                .collect(Collectors.toList());
        clienteDTO.setContatos(contatosDTO);

        // Convertendo lista de Enderecos
        List<EnderecoDTO> enderecosDTO = cliente.getEnderecos().stream()
                .map(this::converterEnderecoParaDTO)
                .collect(Collectors.toList());
        clienteDTO.setEnderecos(enderecosDTO);

        return clienteDTO;
    }

    private ContatoDTO converterContatoParaDTO(Contato contato) {
        ContatoDTO contatoDTO = new ContatoDTO();
        contatoDTO.setTelefone(contato.getTelefone());
        contatoDTO.setEmail(contato.getEmail());
        return contatoDTO;
    }

    private EnderecoDTO converterEnderecoParaDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setCep(endereco.getCep());
        return enderecoDTO;
    }


    @Transactional
    @Override
    public void atualizarCliente(Long idCliente, ClienteCadastroDTO clienteDTO) throws Exception {
       Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);

       if(clienteOptional.isPresent()){
           Cliente cliente = clienteOptional.get();

           // Atualizar os campos relevantes com base nos dados do DTO
           cliente.setNome(clienteDTO.getNome());

           //atualizar contatos
           atualizarContatos(cliente, clienteDTO.getContatos());

           //atualizar endereços
           atualizarEnderecos(cliente, clienteDTO.getEnderecos());

           clienteRepository.save(cliente);

       } else{
           throw new Exception("Cliente não encontrado");
       }
   }

    private void atualizarContatos(Cliente cliente, List<ContatoDTO> contatosDTO) {

        List<Contato> contatos = cliente.getContatos();

        //Removendo contatos existentes do Banco de dados
        for(Contato contato: contatos){
            contatoRepository.deleteById(contato.getId());
        }

        // Remover contatos existentes
        cliente.getContatos().clear();

        // Adicionar novos contatos
        for (ContatoDTO contatoDTO : contatosDTO) {
            Contato contato = new Contato();
            contato.setTelefone(contatoDTO.getTelefone());
            contato.setEmail(contatoDTO.getEmail());
            contato.setCliente(cliente);
            cliente.getContatos().add(contato);
        }
    }

    private void atualizarEnderecos(Cliente cliente, List<EnderecoDTO> enderecosDTO) {

        List<Endereco> enderecos = cliente.getEnderecos();

        //Removendo enderecos existentes do Banco de dados
        for(Endereco endereco: enderecos){
            enderecoRepository.deleteById(endereco.getId());
        }

        // Remover endereços existentes
        cliente.getEnderecos().clear();

        // Adicionar novos endereços
        for (EnderecoDTO enderecoDTO : enderecosDTO) {
            Endereco endereco = new Endereco();
            endereco.setRua(enderecoDTO.getRua());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setCep(enderecoDTO.getCep());
            endereco.setCliente(cliente);
            cliente.getEnderecos().add(endereco);
        }
    }

    public void excluirCliente(Long clienteId) {
        try {
            clienteRepository.deleteById(clienteId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + clienteId);
        }
    }

}
