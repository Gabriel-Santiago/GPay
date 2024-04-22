package BancoDigital.GPay.controller;

import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.service.LojistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lojista")
public class LojistaController {

    LojistaService service;

    @PostMapping("/cnpj")
    public void saveForCnpj(@RequestBody Lojista lojista){
        service.salvarPeloCnpj(lojista, lojista.getCnpj());
    }

    @PostMapping("/email")
    public void salvarForEmail(@RequestBody Lojista lojista){
        service.salvarPeloEmail(lojista, lojista.getEmail());
    }

    @GetMapping
    public ResponseEntity<List<Lojista>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Lojista> findCnpj(@PathVariable("cnpj") long cnpj){
        Lojista lojista = service.findByCnpj(cnpj);

        if(lojista != null){
            return new ResponseEntity<>(lojista, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Lojista> findEmail(@PathVariable("email") String email){
        Lojista lojista = service.findByEmail(email);

        if(lojista != null){
            return new ResponseEntity<>(lojista, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<Lojista>> findNome(@PathVariable("nome") String nome){
        return new ResponseEntity<>(service.findByNome(nome), HttpStatus.OK);
    }

    @DeleteMapping("/{cnpj}")
    public void deleteByCnpj(@PathVariable("cnpj") long cnpj){
        service.deleteByCnpj(cnpj);
    }

    @DeleteMapping("/{email}")
    public void deleteByEmail(@PathVariable("email") String email){
        service.deleteByEmail(email);
    }

    @PutMapping("/{cnpj}")
    public void updateByCnpj(@PathVariable("cnpj") long cnpj, @RequestBody Lojista lojista){
        service.updateByCnpj(lojista, cnpj);
    }

    @PutMapping("/{email}")
    public void updateByEmail(@PathVariable("email") String email, @RequestBody Lojista lojista){
        service.updateByEmail(lojista, email);
    }
}
