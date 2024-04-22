package BancoDigital.GPay.controllerTest;

import BancoDigital.GPay.controller.LojistaController;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.service.LojistaService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class LojistaControllerTest {

    @InjectMocks
    private LojistaController controller;

    @Mock
    private LojistaService service;

    @Test
    void testSaveForCnpj() throws Exception{
        Lojista lojista = new Lojista();
        lojista.setCnpj(15975345685200L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/lojista/cnpj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lojista)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).salvarPeloCnpj(lojista, lojista.getCnpj());
    }

    @Test
    void testSaveForEmail() throws Exception{
        Lojista lojista = new Lojista();
        lojista.setEmail("lojista@gmail.com");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/lojista/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lojista)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).salvarPeloEmail(lojista, lojista.getEmail());
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
        Lojista tricolor = new Lojista();
        Lojista superHomem = new Lojista();
        Lojista lindona = new Lojista();

        List<Lojista> lojistas = new ArrayList<>();
        lojistas.add(tricolor);
        lojistas.add(superHomem);
        lojistas.add(lindona);

        when(service.findAll()).thenReturn(lojistas);

        ResponseEntity<List<Lojista>> responseEntity = controller.findAll();

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(lojistas, responseEntity.getBody());
    }

    @Test
    void testFindCnpj(){
        Lojista lojista = new Lojista();
        lojista.setCnpj(75315985245691L);

        when(service.findByCnpj(lojista.getCnpj())).thenReturn(lojista);

        ResponseEntity<Lojista> responseEntity = controller.findCnpj(lojista.getCnpj());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(lojista, responseEntity.getBody());
    }

    @Test
    void testFindCnpjNãoEncontrado(){
        long cnpj = 75315985245691L;

        when(service.findByCnpj(cnpj)).thenReturn(null);

        ResponseEntity<Lojista> responseEntity = controller.findCnpj(cnpj);

        assertSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testFindEmail(){
        Lojista lojista = new Lojista();
        lojista.setEmail("lojista@gmail.com");

        when(service.findByEmail(lojista.getEmail())).thenReturn(lojista);

        ResponseEntity<Lojista> responseEntity = controller.findEmail(lojista.getEmail());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(lojista, responseEntity.getBody());
    }

    @Test
    void testFindEmailNãoEncontrado(){
        String email = "ola@gmail.com";

        when(service.findByEmail(email)).thenReturn(null);

        ResponseEntity<Lojista> responseEntity = controller.findEmail(email);

        assertSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testFindNome(){
        Lojista tricolor = new Lojista();
        tricolor.setNome("Tricolor");
        Lojista superHomem = new Lojista();
        Lojista lindona = new Lojista();

        List<Lojista> lojistas = new ArrayList<>();
        lojistas.add(tricolor);
        lojistas.add(superHomem);
        lojistas.add(lindona);

        when(service.findByNome(tricolor.getNome())).thenReturn(lojistas);

        ResponseEntity<List<Lojista>> responseEntity = controller.findNome(tricolor.getNome());

        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(lojistas, responseEntity.getBody());
    }

    @Test
    void testDeleteByCnpj(){
        long cnpj = 75315985245691L;

        controller.deleteByCnpj(cnpj);

        verify(service, times(1)).deleteByCnpj(cnpj);
    }

    @Test
    void testDeleteByEmail() throws Exception{
        Lojista lojista = new Lojista();
        lojista.setEmail("tesste@gmail.com");

        controller.deleteByEmail(lojista.getEmail());

        verify(service, times(1)).deleteByEmail(lojista.getEmail());
    }

    @Test
    void testUpdateByCnpj(){
        Lojista lojista = new Lojista();
        lojista.setCnpj(45678912345600L);

        controller.updateByCnpj(74185296319370L, lojista);

        verify(service, times(1)).updateByCnpj(lojista, 74185296319370L);
    }

    @Test
    void testUpdateByEmail(){
        Lojista lojista = new Lojista();
        lojista.setEmail("ola@gmail.com");

        controller.updateByEmail("hello@gmail.com", lojista);

        verify(service, times(1)).updateByEmail(lojista, "hello@gmail.com");
    }
}
