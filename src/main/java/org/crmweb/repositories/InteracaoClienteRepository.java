package org.crmweb.repositories;

import org.crmweb.models.InteracaoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteracaoClienteRepository extends JpaRepository<InteracaoCliente, Long> {
    long count();//Método fornecido pelo JpaRepository que conta os registros no banco de dados
}
