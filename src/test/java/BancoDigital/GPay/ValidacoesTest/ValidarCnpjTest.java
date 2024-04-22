package BancoDigital.GPay.ValidacoesTest;

import BancoDigital.GPay.exception.CnpjInvalidoException;
import BancoDigital.GPay.validacoes.ValidarCnpj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidarCnpjTest {

    ValidarCnpj validarCnpj = new ValidarCnpj();

    @Test
    void testCNPJ(){
        long cnpj = 12345678912300L;

        try {
            validarCnpj.validacaoCnpj(cnpj);
        }catch (CnpjInvalidoException e){
            fail("O CNPJ deve ter 14 dígitos");
        }
    }

    @Test
    void testeCNPJComMenos14Digitos(){
        long cnpj = 123456789L;

        try {
            validarCnpj.validacaoCnpj(cnpj);
            fail("Uma exceção era esperada para um CNPJ inválido");
        }catch (CnpjInvalidoException e){
            assertEquals("O CNPJ deve ter 14 dígitos", e.getMessage());
        }
    }

    @Test
    void testeCNPJComMais14Digitos(){
        long cnpj = 123456789987654321L;

        try {
            validarCnpj.validacaoCnpj(cnpj);
            fail("Uma exceção era esperada para um CNPJ inválido");
        }catch (CnpjInvalidoException e){
            assertEquals("O CNPJ deve ter 14 dígitos", e.getMessage());
        }
    }
}
