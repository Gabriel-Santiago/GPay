package BancoDigital.GPay.serviceTest;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.model.Usuario;
import BancoDigital.GPay.repository.TransferenciaRepository;
import BancoDigital.GPay.service.NotificacaoService;
import BancoDigital.GPay.service.TransferenciaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TransferenciaServiceTest {

    @InjectMocks
    TransferenciaService service;

    @Mock
    NotificacaoService notificacaoService;

    @Mock
    TransferenciaRepository repository;

    Transferencia transferencia = new Transferencia();

    @Test
    void testSalvar(){
        int id = 1;

        service.salvar(transferencia, id);

        assertEquals(id, transferencia.getId());
        Mockito.verify(repository).save(transferencia);
    }

    @Test
    void testeFind(){
        int id = 1;
        transferencia.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(transferencia));

        Transferencia transferenciaEncontrada = service.find(id);

        Assertions.assertNotNull(transferenciaEncontrada);
        assertEquals(id, transferenciaEncontrada.getId());
    }

    @Test
    void testeNotFind(){
        int id = -1;

        Transferencia transferenciaNaoEncontrada = service.find(id);

        Assertions.assertNull(transferenciaNaoEncontrada);

        Mockito.verifyNoInteractions(repository);
    }

    @Test
    void testeFindAll(){
        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia());
        transferencias.add(new Transferencia());

        when(repository.findAll()).thenReturn(transferencias);

        List<Transferencia> transferenciasEncontradas = service.findAll();

        assertEquals(transferencias.size(), transferenciasEncontradas.size());
        assertTrue(transferenciasEncontradas.containsAll(transferencias));
    }

    @Test
    void testeDelete(){
        int id = 1;
        transferencia.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(transferencia));

        service.delete(id);

        verify(repository).delete(transferencia);
    }

    @Test
    void testeUpdate(){
        int id = 1;
        transferencia.setId(id);

        service.update(id, transferencia);

        verify(repository).save(transferencia);
        assertEquals(id, transferencia.getId());
    }

    @Test
    void testeFindByRemetente(){
        Cliente remetente = new Cliente();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia());
        transferencias.add(new Transferencia());

        when(repository.findByRemetente(remetente)).thenReturn(transferencias);

        List<Transferencia> transferenciaList = service.findByRemetente(remetente);

        assertEquals(transferencias.size(), transferenciaList.size());
        assertTrue(transferenciaList.containsAll(transferencias));
    }

    @Test
    void testeFindByDestinatarioCliente(){
        Cliente destinatario = new Cliente();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia());
        transferencias.add(new Transferencia());

        when(repository.findByDestinatarioCliente(destinatario)).thenReturn(transferencias);

        List<Transferencia> transferenciaList = service.findByDestinatarioCliente(destinatario);

        assertEquals(transferencias.size(), transferenciaList.size());
        assertTrue(transferenciaList.containsAll(transferencias));
    }

    @Test
    void testeFindByDestinatarioLojista(){
        Lojista destinatario = new Lojista();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia());
        transferencias.add(new Transferencia());

        when(repository.findByDestinatarioLojista(destinatario)).thenReturn(transferencias);

        List<Transferencia> transferenciaList = service.findByDestinatarioLojista(destinatario);

        assertEquals(transferencias.size(), transferenciaList.size());
        assertTrue(transferenciaList.containsAll(transferencias));
    }

    @Test
    void testeVerificaSaldo(){
        Usuario remetente = new Usuario();
        remetente.setSaldo(BigDecimal.valueOf(999));

        BigDecimal valor = BigDecimal.valueOf(603);

        assertTrue(service.verificaSaldo(remetente, valor));
    }

    @Test
    void testeVerificaSaldoInsuficiente(){
        Usuario remetente = new Usuario();
        remetente.setSaldo(BigDecimal.valueOf(999));

        BigDecimal valor = BigDecimal.valueOf(1008);

        assertFalse(service.verificaSaldo(remetente, valor));
    }

    @Test
    void transferirValor(){
        Usuario remetente = new Usuario();
        remetente.setSaldo(BigDecimal.valueOf(999));

        Usuario destinatario = new Usuario();
        destinatario.setSaldo(BigDecimal.valueOf(909));

        BigDecimal valor = BigDecimal.valueOf(99);

        service.transferirValor(remetente, destinatario, valor);

        assertEquals(BigDecimal.valueOf(900), remetente.getSaldo());
        assertEquals(BigDecimal.valueOf(1008), destinatario.getSaldo());
    }

    @Test
    void testTransferir(){
        Usuario remetente = new Usuario();
        remetente.setSaldo(BigDecimal.valueOf(100));

        Usuario destinatario = new Usuario();
        destinatario.setSaldo(BigDecimal.valueOf(50));

        String resultado = service.transferir(remetente, destinatario, BigDecimal.valueOf(50));
        NotificacaoService notificacaoServiceMock = mock(NotificacaoService.class);

        assertEquals("Transferência finalizada com sucesso.\n" + notificacaoServiceMock.enviarNotificacao(destinatario), resultado);
        assertEquals(BigDecimal.valueOf(50), remetente.getSaldo());
        assertEquals(BigDecimal.valueOf(100), destinatario.getSaldo());
    }

    @Test
    void testTransferirSemSaldo(){
        Usuario remetente = new Usuario();
        remetente.setSaldo(BigDecimal.valueOf(100));

        Usuario destinatario = new Usuario();
        destinatario.setSaldo(BigDecimal.valueOf(50));

        String resultado = service.transferir(remetente, destinatario, BigDecimal.valueOf(150));

        assertEquals("Transferência finalizada sem sucesso. Valor solicitado maior que o saldo", resultado);
        assertEquals(BigDecimal.valueOf(100), remetente.getSaldo());
        assertEquals(BigDecimal.valueOf(50), destinatario.getSaldo());
    }
}
