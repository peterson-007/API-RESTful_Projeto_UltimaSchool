package org.crmweb.repositories;

import org.crmweb.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNumeroCliente(Long numeroCliente);
}
