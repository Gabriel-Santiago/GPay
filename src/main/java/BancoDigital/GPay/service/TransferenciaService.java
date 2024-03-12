package BancoDigital.GPay.service;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    TransferenciaRepository transferenciaRepository;

    void salvar(Transferencia entity, int id){
        if(id != 0){
            entity.setId(id);
        }
        transferenciaRepository.save(entity);
    }

    Transferencia find(int id){
        if(id <= 0){
            return null;
        }
        Optional<Transferencia> transferencia = transferenciaRepository.findById(id);

        return transferencia.orElse(null);
    }

    List<Transferencia> findAll(){
        return transferenciaRepository.findAll();
    }

    void delete(int id){
        Transferencia transferencia = find(id);
        transferenciaRepository.delete(transferencia);
    }

    void update(int id, Transferencia transferencia){
        transferencia.setId(id);
        transferenciaRepository.save(transferencia);
    }

    List<Transferencia> findByRemetente(Cliente remetente){
        return transferenciaRepository.findByRemetente(remetente);
    }

    List<Transferencia> findByDestinatarioCliente(Cliente destinatario){
        return transferenciaRepository.findByDestinatarioCliente(destinatario);
    }

    List<Transferencia> findByDestinatarioLojista(Lojista destinatario){
        return transferenciaRepository.findByDestinatarioLojista(destinatario);
    }
}
