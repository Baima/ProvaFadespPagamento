package com.example.prova;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoRepository pagamentoRepository;

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    //retorna todos os pagamentos
    @GetMapping("/todos")
    public List<Pagamento> retornarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }


    //adiciona o novo pagamento
    @PostMapping("/novo")
    public ResponseEntity<String> criarPagamento(@Valid @RequestBody Pagamento novoPagamento){


        Optional<Pagamento> pagamentoByCodigo = pagamentoRepository.findByCodigo(novoPagamento.getCodigo());
        if(pagamentoByCodigo.isPresent()){
            return ResponseEntity.badRequest().body("Erro: codigo de pagamento já existe");
        }
        if(!novoPagamento.getStatusPagamento().isEmpty() && novoPagamento.getStatusPagamento() != null){
            novoPagamento.setStatusPagamento("Pendente de Processamento");
        }
        if(novoPagamento.getMetodoPagamento().name().equals("CARTAO_CREDITO")||novoPagamento.getMetodoPagamento().name().equals("CARTAO_DEBITO")){
            if (novoPagamento.getNumeroCartao() == null || novoPagamento.getNumeroCartao().isEmpty()) {
                return ResponseEntity.badRequest().body("Erro: numeroCartao é obrigatório para métodos de pagamento do tipo CARTAO_CREDITO ou CARTAO_DEBITO.");
            }
        }
        pagamentoRepository.save(novoPagamento);
        return ResponseEntity.ok("pagamento cadastrado com sucesso");
    }

    // atualiza um pagamento passando o id  e novo status
    @PutMapping("/atualizar/status/id")
    public ResponseEntity<?> updatePagamentoById(@RequestBody Long id, @RequestBody String statusPagamento){
        Optional<Pagamento> PagamentoById = pagamentoRepository.findById(id);
        if (PagamentoById.isPresent()) {
            Pagamento pagamento = PagamentoById.get();
            pagamento.setStatusPagamento(statusPagamento);
            pagamentoRepository.save(pagamento);

            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.status(404).body("Pagamento nao encontrado com o seguinte id: " + id);
        }

    }

    // atualiza um pagamento passando o codigo  e novo status porque eu achei estranho usar o ID para atualizar o pagamento
    @PutMapping("/atualizar/status/codigo")
    public ResponseEntity<?> updatePagamentoById(@RequestBody Integer codigo, @RequestBody String statusPagamento){
        Optional<Pagamento> PagamentoById = pagamentoRepository.findByCodigo(codigo);

        if(PagamentoById.isPresent()){
            Pagamento pagamento = PagamentoById.get();

            //Pendente de Processamento
            if ( pagamento.getStatusPagamento().equals("Pendente de Processamento")) {

                if(statusPagamento.equals("Processado com Sucesso") || statusPagamento.equals("Processado com Falha")) {
                    pagamento.setStatusPagamento(statusPagamento);
                    pagamentoRepository.save(pagamento);
                    return ResponseEntity.ok(pagamento);
                }
                else{
                    return ResponseEntity.badRequest().body("Mudanca de status nao permitida");
                }
            }
            //Processado com falha
            else if(pagamento.getStatusPagamento().equals("Processado com Falha")){
                if(statusPagamento.equals("Pendente de Processamento")) {
                    pagamento.setStatusPagamento(statusPagamento);
                    pagamentoRepository.save(pagamento);
                    return ResponseEntity.ok(pagamento);
                }
                else{
                    return ResponseEntity.badRequest().body("Mudanca de status nao permitida");
                }
            }
            else{
                return ResponseEntity.badRequest().body("Mudanca de status nao permitida");
            }

        }else {
            return ResponseEntity.status(404).body("Pagamento nao encontrado com o seguinte codigo: " + codigo);
        }

    }



    //FILTROS DE PESQUISA-------------------------------------------------------------------
    @GetMapping("/busca/codigo/{codigo}")
    public ResponseEntity<Pagamento> getPagamentoByCodigo(@PathVariable Integer codigo){
        Optional<Pagamento> pagamento = pagamentoRepository.findByCodigo(codigo);
        if (pagamento.isPresent()) {
            return ResponseEntity.ok(pagamento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/busca/documento/{documentoPagador}")
    public ResponseEntity<List<Pagamento>> getPagamentoByDocumento(@PathVariable String documentoPagador){
        List<Pagamento> pagamento = pagamentoRepository.findByDocumentoPagador(documentoPagador);
        if (!pagamento.isEmpty()) {
            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/busca/status/{statusPagamento}")
    public ResponseEntity<List<Pagamento>> getPagamentoByStatus(@PathVariable String statusPagamento){
        List<Pagamento> pagamento = pagamentoRepository.findByStatusPagamento(statusPagamento);
        if (!pagamento.isEmpty()) {
            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
// FIM DOS FILTROS DE PESQUISA -------------------------------------------------------------------------------


//delete logico
    @DeleteMapping("/excluir/id/{id}")
    public ResponseEntity<String> deletarPagamentoPorId(@PathVariable Long id) {
        Optional<Pagamento> PagamentoById = pagamentoRepository.findById(id);
        if (PagamentoById.isPresent()) {
            Pagamento pagamento = PagamentoById.get();
            pagamento.setStatusPagamento("inativo");
            pagamentoRepository.save(pagamento);
            return ResponseEntity.ok("pagamento com id "+id+" excluido");
        }
        else {
            return ResponseEntity.status(404).body("Pagamento nao encontrado com o seguinte id: " + id);
        }
    }
    // novamente um endpoint que não usa o id
    @DeleteMapping("/excluir/codigo/{codigo}")
    public ResponseEntity<String> deletarPagamentoPorCodigo(@PathVariable Integer codigo) {
        Optional<Pagamento> PagamentoById = pagamentoRepository.findByCodigo(codigo);
        if (PagamentoById.isPresent()) {
            Pagamento pagamento = PagamentoById.get();
            pagamento.setStatusPagamento("inativo");
            pagamentoRepository.save(pagamento);
            return ResponseEntity.ok("pagamento com id "+codigo+" excluido");
        }
        else {
            return ResponseEntity.status(404).body("Pagamento nao encontrado com o seguinte codigo: " + codigo);
        }
    }


}
