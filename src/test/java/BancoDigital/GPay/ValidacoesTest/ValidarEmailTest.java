package BancoDigital.GPay.ValidacoesTest;

import BancoDigital.GPay.exception.EmailInvalidoException;
import BancoDigital.GPay.validacoes.ValidarEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidarEmailTest {

    ValidarEmail validarEmail = new ValidarEmail();

    @Test
    void testValidarEmail(){
        String email = "usuario@exemplo.com";

        try {
            validarEmail.validacaoEmail(email);
        }catch (EmailInvalidoException e){
            fail("Nenhuma exceção será lançada");
        }
    }

    @Test
    void testValidarEmailInvalido(){
        String email = "usuario@exemplo";

        try {
            validarEmail.validacaoEmail(email);
            fail("Uma exceção será lançada");
        }catch (EmailInvalidoException e){
            assertEquals("Email inválido!", e.getMessage());
        }
    }
}
