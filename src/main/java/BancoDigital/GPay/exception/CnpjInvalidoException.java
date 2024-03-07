package BancoDigital.GPay.exception;

public class CnpjInvalidoException extends RuntimeException{

    public CnpjInvalidoException(String mensagem){
        super(mensagem);
    }
}
