package BancoDigital.GPay.ValidacoesTest;

import BancoDigital.GPay.exception.CpfInvalidoException;
import BancoDigital.GPay.validacoes.ValidarCpf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidarCpfTest {

    ValidarCpf validarCpf = new ValidarCpf();

    @Test
    void testCPF(){
        long cpf = 12345678900L;

        try {
            validarCpf.validacaoCpf(cpf);
        }catch (CpfInvalidoException e){
            fail("O CPF deve ter 11 dígitos.");
        }
    }

    @Test
    void testeCPFMenos11Digitos(){
        long cpf = 123456789L;

        try {
            validarCpf.validacaoCpf(cpf);
            fail("Uma exceção era esperada para um CPF inválido");
        }catch (CpfInvalidoException e){
            assertEquals("O CPF deve ter 11 dígitos.", e.getMessage());
        }
    }

    @Test
    void testeCPFMais11Digitos(){
        long cpf = 123456789753159L;

        try {
            validarCpf.validacaoCpf(cpf);
            fail("Uma exceção era esperada para um CPF inválido");
        }catch (CpfInvalidoException e){
            assertEquals("O CPF deve ter 11 dígitos.", e.getMessage());
        }
    }
}
