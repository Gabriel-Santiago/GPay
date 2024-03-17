package BancoDigital.GPay.objetos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

public class GerarDados {

    Random random = new Random();
    public Long geraCpfAleatorio(){
        return 100_000_000L + (long) (random.nextDouble() * 900_000_000L);
    }

    public String geraCnpjAleatorio() {
        Random random = new Random();
        int[] cnpjBase = new int[12];

        for (int i = 0; i < 12; i++) {
            cnpjBase[i] = random.nextInt(10);
        }

        int digito1 = calculaDigitoVerificador(cnpjBase, 5);
        cnpjBase[12] = digito1;

        int digito2 = calculaDigitoVerificador(cnpjBase, 6);
        cnpjBase[13] = digito2;

        StringBuilder cnpj = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            cnpj.append(cnpjBase[i]);
            if (i == 1 || i == 4) {
                cnpj.append(".");
            } else if (i == 7) {
                cnpj.append("/");
            } else if (i == 11) {
                cnpj.append("-");
            }
        }
        return cnpj.toString();
    }

    private int calculaDigitoVerificador(int[] cnpjBase, int peso) {
        int soma = 0;
        int multiplicador = peso + 1;

        for (int i = 0; i < cnpjBase.length; i++) {
            soma += cnpjBase[i] * multiplicador;
            multiplicador = (multiplicador - 1) == 1 ? 9 : multiplicador - 1;
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public BigDecimal gerarSaldo(){
        double valorAleatorio = random.nextDouble() * 1_000_000_000;
        BigDecimal resultado = BigDecimal.valueOf(valorAleatorio);
        resultado = resultado.setScale(4, RoundingMode.HALF_UP);
        return resultado;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String gerarSenha(){
        StringBuilder senha = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for(int i = 0; i < 12; i++){
            int index = secureRandom.nextInt(CHARACTERS.length());
            senha.append(CHARACTERS.charAt(index));
        }
        return senha.toString();
    }

    private static final String[] DOMINIOS_VALIDOS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};

    public String gerarEmail() {
        String nomeusuario = "user" + random.nextInt(1000);
        String dominio = DOMINIOS_VALIDOS[random.nextInt(DOMINIOS_VALIDOS.length)];
        return nomeusuario + "@" + dominio;
    }
}
