package BancoDigital.GPay.serviceTest;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.objetos.GerarDados;
import BancoDigital.GPay.repository.ClienteRepository;
import BancoDigital.GPay.service.ClienteService;
import BancoDigital.GPay.validacoes.ValidarCpf;
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
class ClienteServiceTest {

    @InjectMocks
    ClienteService service;

    @Mock
    ClienteRepository repository;

    @Mock
    ValidarCpf validarCpf;

    @Mock
    ValidarEmail validarEmail;

    @Mock
    GerarDados dados;

    private Cliente criarObjetoTeste(){
        Cliente cliente = new Cliente();
        cliente.setNome("Gabriel");
        cliente.setEmail(Objects.requireNonNull(dados).gerarEmail());
        cliente.setSenha(dados.gerarSenha());
        cliente.setCpf(dados.geraCpfAleatorio());
        cliente.setSaldo(dados.gerarSaldo());
        return cliente;
    }

    @Test
    void testeSalvarPeloCpf(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarCpf).validacaoCpf(cliente.getCpf());

        service.salvarPeloCpf(cliente, cliente.getCpf());

        Mockito.verify(repository).save(cliente);
    }

    @Test
    void testeSalvarPeloCpfInvalido(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doThrow(new IllegalArgumentException("CPF Inválido")).when(validarCpf).validacaoCpf(anyLong());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarPeloCpf(cliente, 12345678900L));

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeSalvarPeloEmail(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(cliente.getEmail());

        service.salvarPeloEmail(cliente, cliente.getEmail());

        Mockito.verify(repository).save(cliente);
    }

    @Test
    void testeSalvarPeloEmailInvalido(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doThrow(new IllegalArgumentException("Email Inválido")).when(validarEmail).validacaoEmail(anyString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarPeloEmail(cliente, "Olá, Eu sou o goku"));

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testFindByCpf(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarCpf).validacaoCpf(cliente.getCpf());

        Mockito.when(repository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        Cliente clienteTeste = service.findByCpf(cliente.getCpf());

        Assertions.assertEquals(cliente, clienteTeste);
    }

    @Test
    void testFindByCpfNaoEncontrado(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarCpf).validacaoCpf(cliente.getCpf());

        Mockito.when(repository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());

        Cliente clienteTeste = service.findByCpf(cliente.getCpf());

        Assertions.assertNull(clienteTeste);
    }

    @Test
    void testFindByCpfInvalido(){
        long cpf = 123456789000L;

        Mockito.doNothing().when(validarCpf).validacaoCpf(cpf);

        Cliente clienteTeste = service.findByCpf(cpf);

        Assertions.assertNull(clienteTeste);
    }

    @Test
    void testFindByEmail(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(cliente.getEmail());

        Mockito.when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        Cliente clienteTeste = service.findByEmail(cliente.getEmail());

        Assertions.assertEquals(cliente, clienteTeste);
    }

    @Test
    void testFindByEmailNaoEncontrado(){
        Cliente cliente = criarObjetoTeste();

        Mockito.doNothing().when(validarEmail).validacaoEmail(cliente.getEmail());

        Mockito.when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.empty());

        Cliente clienteTeste = service.findByEmail(cliente.getEmail());

        Assertions.assertNull(clienteTeste);
    }

    @Test
    void testFindByEmailInvalido(){
        String email = "Isso é tudo pessoal!";

        Mockito.doNothing().when(validarEmail).validacaoEmail(email);

        Cliente clienteTeste = service.findByEmail(email);

        Assertions.assertNull(clienteTeste);
    }

    @Test
    void testeFindAll(){
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());
        clientes.add(new Cliente());

        Mockito.when(repository.findAll()).thenReturn(clientes);

        List<Cliente> clienteList = service.findAll();

        Assertions.assertEquals(clientes, clienteList);
    }

    @Test
    void testeDeleteByCpf(){
        Cliente cliente = criarObjetoTeste();

        Mockito.when(repository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        service.deleteByCpf(cliente.getCpf());

        Mockito.verify(repository).delete(cliente);
    }

    @Test
    void testeDeleteByCpfNaoEncontrado(){
        long cpf = 123L;

        Mockito.when(service.findByCpf(cpf)).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.deleteByCpf(cpf);
        });

        Mockito.verify(repository, never()).delete(any());
    }

    @Test
    void testeDeleteByEmail(){
        Cliente cliente = criarObjetoTeste();

        Mockito.when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        service.deleteByEmail(cliente.getEmail());

        Mockito.verify(repository).delete(cliente);
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
    void testeUpdateByCpf(){
        Cliente cliente = criarObjetoTeste();

        Cliente clienteAtualizado = criarObjetoTeste();
        clienteAtualizado.setCpf(12345678900L);

        Mockito.when(repository.findByCpf(clienteAtualizado.getCpf())).thenReturn(Optional.of(cliente));

        service.uptadeByCpf(clienteAtualizado, clienteAtualizado.getCpf());

        Mockito.verify(repository).save(cliente);
    }

    @Test
    void testeUpdateByCpfNaoEncontrado(){
        Cliente clienteAtualizado = criarObjetoTeste();
        clienteAtualizado.setCpf(12345678900L);

        Mockito.when(service.findByCpf(clienteAtualizado.getCpf())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.uptadeByCpf(clienteAtualizado, clienteAtualizado.getCpf());
        });

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeUpdateByEmail(){
        Cliente cliente = criarObjetoTeste();

        Cliente clienteAtualizado = criarObjetoTeste();
        clienteAtualizado.setEmail("goku@hotmail.com");

        Mockito.when(repository.findByEmail(clienteAtualizado.getEmail())).thenReturn(Optional.of(cliente));

        service.updateByEmail(clienteAtualizado, clienteAtualizado.getEmail());

        Mockito.verify(repository).save(cliente);
    }

    @Test
    void testeUpdateByEmailNaoEncontrado(){
        Cliente clienteAtualizado = criarObjetoTeste();
        clienteAtualizado.setEmail("goku@hotmail.com");

        Mockito.when(service.findByEmail(clienteAtualizado.getEmail())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            service.updateByEmail(clienteAtualizado, clienteAtualizado.getEmail());
        });

        Mockito.verify(repository, never()).save(any());
    }

    @Test
    void testeFindByNome(){
        String nomeBusca = "Gohan";

        List<Cliente> clientesEsperados = new ArrayList<>();
        clientesEsperados.add(new Cliente());

        Mockito.when(repository.findByNome(nomeBusca)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = service.findByNome(nomeBusca);

        Assertions.assertEquals(clientesEsperados, clientesRetornados);
    }
}
