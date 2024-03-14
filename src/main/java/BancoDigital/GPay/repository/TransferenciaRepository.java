package BancoDigital.GPay.repository;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {

    List<Transferencia> findByRemetente(Cliente remetente);
    List<Transferencia> findByDestinatarioCliente(Cliente destinatario);
    List<Transferencia> findByDestinatarioLojista(Lojista destinatario);
    String transferir(Usuario remetente, Usuario destinatario, BigDecimal valor);
}
