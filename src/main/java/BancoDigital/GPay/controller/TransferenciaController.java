package BancoDigital.GPay.controller;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.model.Usuario;
import BancoDigital.GPay.service.TransferenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    TransferenciaService service;

    @PostMapping
    public void save(@RequestBody @Valid Transferencia transferencia){
        service.salvar(transferencia, 0);
    }

    @GetMapping
    public ResponseEntity<List<Transferencia>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> find(@PathVariable("id") int id){
        Transferencia transferencia = service.find(id);

        if(transferencia != null){
            return new ResponseEntity<>(transferencia, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{remetente}")
    public ResponseEntity<List<Transferencia>> findRemetente(@PathVariable("remetente") Cliente remetente){
        return new ResponseEntity<>(service.findByRemetente(remetente), HttpStatus.OK);
    }

    @GetMapping("/destinatario/{cliente}")
    public ResponseEntity<List<Transferencia>> findDestinatarioCliente(@PathVariable("cliente") Cliente destinatario){
        return new ResponseEntity<>(service.findByDestinatarioCliente(destinatario), HttpStatus.OK);
    }

    @GetMapping("/destinatario/{lojista}")
    public ResponseEntity<List<Transferencia>> findDestinatarioLojista(@PathVariable("lojista") Lojista destinatario){
        return new ResponseEntity<>(service.findByDestinatarioLojista(destinatario), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody @Valid Transferencia transferencia){
        service.update(id, transferencia);
    }

    @GetMapping("/finalizar-transferencia-lojista")
    public String finalizarTransferenciaLojista(Cliente remetente, Lojista destinatario, BigDecimal valor){
        return service.transferir(remetente, destinatario, valor);
    }

    @GetMapping("/finalizar-transferencia-cliente")
    public String finalizarTransferenciaCliente(Cliente remetente, Cliente destinatario, BigDecimal valor){
        return service.transferir(remetente, destinatario, valor);
    }

    @GetMapping("/estorno-lojista")
    public String estornoLojista(Lojista remetente, Cliente destinatario, BigDecimal valor){
        return service.transferir(remetente, destinatario, valor);
    }
}
