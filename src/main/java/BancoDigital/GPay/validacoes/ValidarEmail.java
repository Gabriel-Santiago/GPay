package BancoDigital.GPay.validacoes;

import BancoDigital.GPay.exception.EmailInvalidoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarEmail {

    private static final String REGEX_EMAIL =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public void validacaoEmail(String email) throws EmailInvalidoException {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new EmailInvalidoException("Email inválido!");
        }
    }
}
