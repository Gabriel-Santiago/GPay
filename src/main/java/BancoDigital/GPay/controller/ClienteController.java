package BancoDigital.GPay.controller;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    ClienteService service;

    @PostMapping
    public void saveForCpf(@RequestBody Cliente cliente){
        service.salvarPeloCpf(cliente, cliente.getCpf());
    }

    @PostMapping
    public void saveForEmail(@RequestBody Cliente cliente){
        service.salvarPeloEmail(cliente, cliente.getEmail());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> findCpf(@PathVariable("cpf") long cpf){
        Cliente cliente = service.findByCpf(cpf);

        if(cliente != null){
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Cliente> findEmail(@PathVariable("email") String email){
        Cliente cliente = service.findByEmail(email);

        if(cliente != null){
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<Cliente>> findNome(@PathVariable("nome") String nome){
        return new ResponseEntity<>(service.findByNome(nome), HttpStatus.OK);
    }

    @DeleteMapping("/{cpf}")
    public void deleteByCpf(@PathVariable("cpf") long cpf){
        service.deleteByCpf(cpf);
    }

    @DeleteMapping("/{email}")
    public void deleteByEmail(@PathVariable("email") String email){
        service.deleteByEmail(email);
    }

    @PutMapping("/{cpf}")
    public void updateByCpf(@PathVariable("cpf") long cpf, @RequestBody Cliente cliente){
        service.uptadeByCpf(cliente, cpf);
    }

    @PutMapping("/{email}")
    public void updateByEmail(@PathVariable("email") String email, @RequestBody Cliente cliente){
        service.updateByEmail(cliente, email);
    }
}
