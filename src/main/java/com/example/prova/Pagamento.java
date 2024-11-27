package com.example.prova;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name="pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "codigo do produto nao pode ser nulo")
    private Integer codigo;
    @NotNull(message = "documento do pagador nao pode ser nulo")
    private String documentoPagador;

    private String numeroCartao;
    @NotNull(message = "valor nao pode ser nulo")
    private BigDecimal valorPagamento;

    @NotNull(message="metodo de pagamento nao pode ser nulo")
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @Column(nullable = false)
    private String statusPagamento = "Pendente de Processamento";





    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Integer getCodigo() {
        return codigo;
    }



    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }



    public String getNumeroCartao() {
        return numeroCartao;
    }


}
