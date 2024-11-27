# ProvaFadespPagamento

Endpoints:
  GET:
  /pagamentos/todos
    pega todos os pagamentos
  /pagamentos/busca/codigo/{codigo}
    procura pagamentos por codigo de pagamento
  /pagamentos/busca/documento/{documentoPagador}
    procura pagamentos por documentoPagador
  /pagamentos/busca/status/{statusPagamento}
    procura pagamentos por status

  POST:
  /pagamentos/novo
    adiciona pagamento novo
  
  PUT:
  /pagamentos/atualizar/status/id
    edita o status por id
  /pagamentos/atualizar/status/codigo
    edita o status por codigo
  
  DELETE:
  /pagamentos/excluir/id/{id}
    deleta logicamente por id
  /pagamentos/excluir/codigo/{codigo}
    deleta logicamente por codigo
