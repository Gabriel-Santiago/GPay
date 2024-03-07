package BancoDigital.GPay.repository;

import BancoDigital.GPay.model.Lojista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LojistaRepository extends JpaRepository<Lojista, Integer> {

    List<Lojista> findByNome(String nome);

    Optional<Lojista> findByCnpj(long cnpj);

    Optional<Lojista> findByEmail(String email);

}
