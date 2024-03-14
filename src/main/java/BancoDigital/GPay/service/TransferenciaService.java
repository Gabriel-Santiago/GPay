package BancoDigital.GPay.service;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.model.Usuario;
import BancoDigital.GPay.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    NotificacaoService notificacaoService;

    TransferenciaRepository transferenciaRepository;

    public void salvar(Transferencia entity, int id){
        if(id != 0){
            entity.setId(id);
        }
        transferenciaRepository.save(entity);
    }

    public Transferencia find(int id){
        if(id <= 0){
            return null;
        }
        Optional<Transferencia> transferencia = transferenciaRepository.findById(id);

        return transferencia.orElse(null);
    }

    public List<Transferencia> findAll(){
        return transferenciaRepository.findAll();
    }

    public void delete(int id){
        Transferencia transferencia = find(id);
        transferenciaRepository.delete(transferencia);
    }

    public void update(int id, Transferencia transferencia){
        transferencia.setId(id);
        transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> findByRemetente(Cliente remetente){
        return transferenciaRepository.findByRemetente(remetente);
    }

    public List<Transferencia> findByDestinatarioCliente(Cliente destinatario){
        return transferenciaRepository.findByDestinatarioCliente(destinatario);
    }

    public List<Transferencia> findByDestinatarioLojista(Lojista destinatario){
        return transferenciaRepository.findByDestinatarioLojista(destinatario);
    }

    public String transferir(Usuario remetente, Usuario destinatario, BigDecimal valor){
        try {
            boolean saldo = verificaSaldo(remetente, valor);

            if(saldo){
                transferirValor(remetente, destinatario, valor);
                notificacaoService.consultaServicoAutorizador();

                return "Transferência finalizada com sucesso.\n" + notificacaoService.enviarNotificacao(destinatario);
            }else{
                return "Transferência finalizada sem sucesso. Valor solicitado maior que o saldo";
            }
        }catch (IOException e){
            return "Erro ao finalizar a transferência: " + e.getMessage();
        }
    }

    public boolean verificaSaldo(Usuario remetente, BigDecimal valor){
        return remetente.getSaldo().compareTo(valor) >= 0;
    }

    public void transferirValor(Usuario remetente, Usuario destinatario, BigDecimal valor){
        remetente.setSaldo(remetente.getSaldo().subtract(valor));
        destinatario.setSaldo(destinatario.getSaldo().add(valor));
    }
}
