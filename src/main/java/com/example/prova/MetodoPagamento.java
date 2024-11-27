package com.example.prova;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MetodoPagamento {
    @JsonProperty("BOLETO")
    BOLETO,
    @JsonProperty("PIX")
    PIX,
    @JsonProperty("CARTAO_CREDITO")
    CARTAO_CREDITO,
    @JsonProperty("CARTAO_DEBITO")
    CARTAO_DEBITO;


}
