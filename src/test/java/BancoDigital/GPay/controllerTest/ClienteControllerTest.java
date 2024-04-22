package BancoDigital.GPay.controllerTest;

import BancoDigital.GPay.controller.ClienteController;
import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController controller;

    @Mock
    private ClienteService service;

    @Test
    void testSaveForCpf() throws Exception{
        Cliente cliente = new Cliente();
        cliente.setCpf(12346578900L);

        doNothing().when(service).salvarPeloCpf(cliente, cliente.getCpf());


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cpf")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cliente)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).salvarPeloCpf(cliente, cliente.getCpf());
    }

    @Test
    void testeSaveForEmail() throws Exception{
        Cliente cliente = new Cliente();
        cliente.setEmail("ola@gmail.com");

        doNothing().when(service).salvarPeloEmail(cliente, cliente.getEmail());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cliente)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).salvarPeloEmail(cliente, cliente.getEmail());
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindAll(){
        Cliente bahia = new Cliente();
        Cliente bbmp = new Cliente();
        Cliente bamor = new Cliente();

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(bahia);
        clientes.add(bbmp);
        clientes.add(bamor);

        when(service.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> responseEntity = controller.findAll();

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(clientes, responseEntity.getBody());
    }

    @Test
    void testFindCpf(){
        Cliente cliente = new Cliente();
        cliente.setCpf(12346578900L);

        when(service.findByCpf(cliente.getCpf())).thenReturn(cliente);

        ResponseEntity<Cliente> responseEntity = controller.findCpf(cliente.getCpf());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(cliente, responseEntity.getBody());
    }

    @Test
    void testFindCpfNaoEncontrado(){
        long cpf = 12346578900L;

        when(service.findByCpf(cpf)).thenReturn(null);

        ResponseEntity<Cliente> responseEntity = controller.findCpf(cpf);

        assertSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testFindEmail(){
        Cliente cliente = new Cliente();
        cliente.setEmail("bbmp@gmail.com");

        when(service.findByEmail(cliente.getEmail())).thenReturn(cliente);

        ResponseEntity<Cliente> responseEntity = controller.findEmail(cliente.getEmail());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(cliente, responseEntity.getBody());
    }

    @Test
    void testFindEmailNaoEncontrado(){
        String email = "ola@gmail.com";

        when(service.findByEmail(email)).thenReturn(null);

        ResponseEntity<Cliente> responseEntity = controller.findEmail(email);

        assertSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testFindNome(){
        Cliente bahia = new Cliente();
        bahia.setNome("Bahia");
        Cliente bbmp = new Cliente();
        bbmp.setNome("BBMP");
        Cliente bamor = new Cliente();
        bamor.setNome("Bamor");

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(bahia);
        clientes.add(bbmp);
        clientes.add(bamor);

        when(service.findByNome(bahia.getNome())).thenReturn(clientes);

        ResponseEntity<List<Cliente>> responseEntity = controller.findNome(bahia.getNome());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(clientes, responseEntity.getBody());
    }

    @Test
    void testDeleteByCpf(){
        long cpf = 12345678952L;

        controller.deleteByCpf(cpf);

        verify(service, times(1)).deleteByCpf(cpf);
    }

    @Test
    void testDeleteByEmail() throws Exception {
        Cliente derrota = new Cliente();
        derrota.setEmail("vicetoria@gmail.com");

        controller.deleteByEmail(derrota.getEmail());

        verify(service, times(1)).deleteByEmail(derrota.getEmail());
    }

    @Test
    void testUpdateByCpf(){
        Cliente bahia = new Cliente();
        bahia.setCpf(19315988504L);

        controller.updateByCpf(19315988505L, bahia);

        verify(service, times(1)).uptadeByCpf(bahia, 19315988505L);
    }

    @Test
    void testUpdateByEmail(){
        Cliente bahia = new Cliente();
        bahia.setEmail("esquadrao@gmail.com");

        controller.updateByEmail("esquadrãoDeAço@gmail.com", bahia);

        verify(service, times(1)).updateByEmail(bahia, "esquadrãoDeAço@gmail.com");
    }
}
