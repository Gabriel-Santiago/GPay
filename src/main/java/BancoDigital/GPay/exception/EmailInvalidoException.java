package BancoDigital.GPay.exception;

public class EmailInvalidoException extends RuntimeException{
    public EmailInvalidoException(String mensagem){
        super(mensagem);
    }
}
