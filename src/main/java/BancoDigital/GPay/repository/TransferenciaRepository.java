package BancoDigital.GPay.repository;

import BancoDigital.GPay.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {

    List<Transferencia> findByRemetente(String remetente);
    List<Transferencia> findByDestinatario(String destinatario);
}
