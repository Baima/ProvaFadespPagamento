package com.example.prova;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByCodigo(Integer codigo);
    List<Pagamento> findByDocumentoPagador(String documentoPagador);
    List<Pagamento> findByStatusPagamento(String statusPagamento);
}