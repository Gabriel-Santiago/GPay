package BancoDigital.GPay.validacoes;

import BancoDigital.GPay.exception.CnpjInvalidoException;

public class ValidarCnpj {

    public void validacaoCnpj(long cnpj){
        String newValue = String.valueOf(cnpj);
        if(newValue.length() != 14){
            throw  new CnpjInvalidoException("O CNPJ deve ter 14 dígitos");
        }
    }
}
