package BancoDigital.GPay.serviceTest;

import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.objetos.GerarDados;
import BancoDigital.GPay.repository.LojistaRepository;
import BancoDigital.GPay.service.LojistaService;
import BancoDigital.GPay.validacoes.ValidarCnpj;
import BancoDigital.GPay.validacoes.ValidarEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;

@ExtendWith(SpringExtension.class)
class LojistaServiceTest {

    @InjectMocks
    LojistaService service;

    @Mock
    LojistaRepository repository;

    @Mock
    ValidarCnpj validarCnpj;

    @Mock
    ValidarEmail validarEmail;

    @Mock
    GerarDados dados;

    private Lojista criarObjetoTeste(){
        Lojista lojista = new Lojista();
        lojista.setNome("Gabriel");
        lojista.setEmail(Objects.requireNonNull(dados).gerarEmail());
        lojista.setSenha(dados.gerarSenha());
        lojista.setCnpj(dados.geraCpfAleatorio());
        lojista.setSaldo(dados.gerarSaldo());
        return lojista;
    }

    @Test
    void testeSalvarPeloCnpj(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarCnpj).validacaoCnpj(lojista.getCnpj());

        service.salvarPeloCnpj(lojista, lojista.getCnpj());

        Mockito.verify(repository).save(lojista);
    }

    @Test
    void testeSalvarPeloCnpjInvalido(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doThrow(new IllegalArgumentException("CNPJ Inválido")).when(validarCnpj).validacaoCnpj(anyLong());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarPeloCnpj(lojista, 12345678900L));

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeSalvarPeloEmail(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(lojista.getEmail());

        service.salvarPeloEmail(lojista, lojista.getEmail());

        Mockito.verify(repository).save(lojista);
    }

    @Test
    void testeSalvarPeloEmailInvalido(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doThrow(new IllegalArgumentException("Email Inválido")).when(validarEmail).validacaoEmail(anyString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarPeloEmail(lojista, "Olá, Eu sou o goku"));

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testFindByCnpj(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarCnpj).validacaoCnpj(lojista.getCnpj());

        Mockito.when(repository.findByCnpj(lojista.getCnpj())).thenReturn(Optional.of(lojista));

        Lojista lojistaTeste = service.findByCnpj(lojista.getCnpj());

        Assertions.assertEquals(lojista, lojistaTeste);
    }

    @Test
    void testFindByCnpjNaoEncontrado(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarCnpj).validacaoCnpj(lojista.getCnpj());

        Mockito.when(repository.findByCnpj(lojista.getCnpj())).thenReturn(Optional.empty());

        Lojista lojistaTeste = service.findByCnpj(lojista.getCnpj());

        Assertions.assertNull(lojistaTeste);
    }

    @Test
    void testFindByCnpjInvalido(){
        long cnpj = 123456789000L;

        Mockito.doNothing().when(validarCnpj).validacaoCnpj(cnpj);

        Lojista lojistaTeste = service.findByCnpj(cnpj);

        Assertions.assertNull(lojistaTeste);
    }

    @Test
    void testFindByEmail(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(lojista.getEmail());

        Mockito.when(repository.findByEmail(lojista.getEmail())).thenReturn(Optional.of(lojista));

        Lojista lojistaTeste = service.findByEmail(lojista.getEmail());

        Assertions.assertEquals(lojista, lojistaTeste);
    }

    @Test
    void testFindByEmailNaoEncontrado(){
        Lojista lojista = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(lojista.getEmail());

        Mockito.when(repository.findByEmail(lojista.getEmail())).thenReturn(Optional.empty());

        Lojista lojistaTeste = service.findByEmail(lojista.getEmail());

        Assertions.assertNull(lojistaTeste);
    }

    @Test
    void testFindByEmailInvalido(){
        String email = "Isso é tudo pessoal!";

        Mockito.doNothing().when(validarEmail).validacaoEmail(email);

        Lojista lojistaTeste = service.findByEmail(email);

        Assertions.assertNull(lojistaTeste);
    }

    @Test
    void testeFindAll(){
        List<Lojista> lojistas = new ArrayList<>();
        lojistas.add(new Lojista());
        lojistas.add(new Lojista());

        Mockito.when(repository.findAll()).thenReturn(lojistas);

        List<Lojista> lojistasList = service.findAll();

        Assertions.assertEquals(lojistas, lojistasList);
    }

    @Test
    void testeDeleteByCnpj(){
        Lojista lojista = criarObjetoTeste();

        Mockito.when(repository.findByCnpj(lojista.getCnpj())).thenReturn(Optional.of(lojista));

        service.deleteByCnpj(lojista.getCnpj());

        Mockito.verify(repository).delete(lojista);
    }

    @Test
    void testeDeleteByCnpjNaoEncontrado(){
        long cnpj = 123L;

        Mockito.when(service.findByCnpj(cnpj)).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.deleteByCnpj(cnpj);
        });

        Mockito.verify(repository, never()).delete(any());
    }

    @Test
    void testeDeleteByEmail(){
        Lojista lojista = criarObjetoTeste();

        Mockito.when(repository.findByEmail(lojista.getEmail())).thenReturn(Optional.of(lojista));

        service.deleteByEmail(lojista.getEmail());

        Mockito.verify(repository).delete(lojista);
    }

    @Test
    void testeDeleteByEmailNaoEncontrado(){
        String email = "Teu sorriso é tão resplandescente";

        Mockito.when(service.findByEmail(email)).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.deleteByEmail(email);
        });

        Mockito.verify(repository, never()).delete(any());
    }

    @Test
    void testeUpdateByCnpj(){
        Lojista lojista = criarObjetoTeste();

        Lojista lojistaAtualizado = criarObjetoTeste();
        lojistaAtualizado.setCnpj(12345678900123L);

        Mockito.when(repository.findByCnpj(lojistaAtualizado.getCnpj())).thenReturn(Optional.of(lojista));

        service.updateByCnpj(lojistaAtualizado, lojistaAtualizado.getCnpj());

        Mockito.verify(repository).save(lojista);
    }

    @Test
    void testeUpdateByCnpjNaoEncontrado(){
        Lojista lojistaAtualizado = criarObjetoTeste();
        lojistaAtualizado.setCnpj(12345678900L);

        Mockito.when(service.findByCnpj(lojistaAtualizado.getCnpj())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.updateByCnpj(lojistaAtualizado, lojistaAtualizado.getCnpj());
        });

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeUpdateByEmail(){
        Lojista lojista = criarObjetoTeste();

        Lojista lojistaAtualizado = criarObjetoTeste();
        lojistaAtualizado.setEmail("goku@hotmail.com");

        Mockito.when(repository.findByEmail(lojistaAtualizado.getEmail())).thenReturn(Optional.of(lojista));

        service.updateByEmail(lojistaAtualizado, lojistaAtualizado.getEmail());

        Mockito.verify(repository).save(lojista);
    }

    @Test
    void testeUpdateByEmailNaoEncontrado(){
        Lojista lojistaAtualizado = criarObjetoTeste();
        lojistaAtualizado.setEmail("vegeta@hotmail.com");

        Mockito.when(service.findByEmail(lojistaAtualizado.getEmail())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.updateByEmail(lojistaAtualizado, lojistaAtualizado.getEmail());
        });

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeFindByNome(){
        String nomeBusca = "Gohan";

        List<Lojista> lojistasEsperados = new ArrayList<>();
        lojistasEsperados.add(new Lojista());

        Mockito.when(repository.findByNome(nomeBusca)).thenReturn(lojistasEsperados);

        List<Lojista> lojistasRetornados = service.findByNome(nomeBusca);

        Assertions.assertEquals(lojistasEsperados, lojistasRetornados);
    }
}
