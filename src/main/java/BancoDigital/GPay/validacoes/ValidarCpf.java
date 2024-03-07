package BancoDigital.GPay.validacoes;

import BancoDigital.GPay.exception.CpfInvalidoException;

public class ValidarCpf {

    public void validacaoCpf(long value){
        String newValue = String.valueOf(value);
        if(newValue.length() != 11){
            throw new CpfInvalidoException("O CPF deve ter 11 dígitos.");
        }
    }
}
