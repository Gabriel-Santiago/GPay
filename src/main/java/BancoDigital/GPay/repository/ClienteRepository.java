package BancoDigital.GPay.repository;

import BancoDigital.GPay.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNome(String nome);

    Optional<Cliente> findByCpf(long cpf);

    Optional<Cliente> findByEmail(String email);

}
