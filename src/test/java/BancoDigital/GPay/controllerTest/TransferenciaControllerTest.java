package BancoDigital.GPay.controllerTest;

import BancoDigital.GPay.controller.TransferenciaController;
import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.model.Transferencia;
import BancoDigital.GPay.service.TransferenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class TransferenciaControllerTest {

    @InjectMocks
    private TransferenciaController controller;

    @Mock
    private TransferenciaService service;

    @Test
    void testSave() throws Exception{
        String json = "{\"valor\": 100.0, \"remetente\": \"remetente\", \"destinatario\": \"destinatario\"}";

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/transferencia")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).salvar(any(Transferencia.class), eq(0));
    }

    @Test
    void testFindAll(){
        Transferencia transferencia1 = new Transferencia();
        Transferencia transferencia2 = new Transferencia();
        Transferencia transferencia3 = new Transferencia();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);
        transferencias.add(transferencia3);

        when(service.findAll()).thenReturn(transferencias);

        ResponseEntity<List<Transferencia>> responseEntity = controller.findAll();

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(transferencias, responseEntity.getBody());
    }

    @Test
    void testFind(){
        Transferencia transferencia = new Transferencia();
        transferencia.setId(1931);

        when(service.find(transferencia.getId())).thenReturn(transferencia);

        ResponseEntity<Transferencia> responseEntity = controller.find(transferencia.getId());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(transferencia, responseEntity.getBody());
    }

    @Test
    void testFindNaoEncontrado(){
        int id = 1931;

        when(service.find(id)).thenReturn(null);

        ResponseEntity<Transferencia> responseEntity = controller.find(id);

        assertSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testDelete(){
        int id = 192;

        controller.delete(id);

        verify(service, times(1)).delete(id);
    }

    @Test
    void testUpdate(){
        Transferencia transferencia = new Transferencia();
        transferencia.setId(74);

        controller.update(81, transferencia);

        verify(service, times(1)).update(81, transferencia);
    }

   @Test
   void testFinalizarTransferenciaLojista() throws Exception{
        Cliente remetente = new Cliente();
        Lojista destinatario = new Lojista();
        BigDecimal valor = BigDecimal.valueOf(180);

        String mensagemRetorno = "Transferência finalizada com sucesso.";

        when(service.transferir(remetente, destinatario, valor)).thenReturn(mensagemRetorno);

       MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

       mockMvc.perform(MockMvcRequestBuilders.get("/transferencia/finalizar-transferencia-lojista")
               .param("remetente", remetente.toString())
               .param("destinatario", destinatario.toString())
               .param("valor", valor.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string(mensagemRetorno));

       verify(service, times(1)).transferir(remetente, destinatario, valor);
   }

    @Test
    void testFinalizarTransferenciaCliente() throws Exception{
        Cliente remetente = new Cliente();
        Cliente destinatario = new Cliente();
        BigDecimal valor = BigDecimal.valueOf(180);

        String mensagemRetorno = "Transferência finalizada com sucesso.";

        when(service.transferir(remetente, destinatario, valor)).thenReturn(mensagemRetorno);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/transferencia/finalizar-transferencia-cliente")
                        .param("remetente", remetente.toString())
                        .param("destinatario", destinatario.toString())
                        .param("valor", valor.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mensagemRetorno));

        verify(service, times(1)).transferir(remetente, destinatario, valor);
    }

    @Test
    void testEstornoLojista() throws Exception{
        Lojista remetente = new Lojista();
        Cliente destinatario = new Cliente();
        BigDecimal valor = BigDecimal.valueOf(180);

        String mensagemRetorno = "Transferência finalizada com sucesso.";

        when(service.transferir(remetente, destinatario, valor)).thenReturn(mensagemRetorno);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/transferencia/estorno-lojista")
                        .param("remetente", remetente.toString())
                        .param("destinatario", destinatario.toString())
                        .param("valor", valor.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mensagemRetorno));

        verify(service, times(1)).transferir(remetente, destinatario, valor);
    }
}
