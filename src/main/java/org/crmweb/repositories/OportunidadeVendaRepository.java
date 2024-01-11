package org.crmweb.repositories;


import org.crmweb.models.OportunidadeVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static org.crmweb.models.OportunidadeVenda.*;

@Repository
public interface OportunidadeVendaRepository extends JpaRepository<OportunidadeVenda, Long> {
    long countByEstagio(EstagioOportunidade estagio);

    @Query("SELECT COALESCE(SUM(ov.valorEstimado), 0) FROM OportunidadeVenda ov")
    double sumValorEstimado();
}
