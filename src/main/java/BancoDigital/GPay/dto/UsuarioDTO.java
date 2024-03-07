package BancoDigital.GPay.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UsuarioDTO(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        @NotBlank
        long cpf,
        @NotBlank
        long cnpj,
        @NotBlank
        BigDecimal saldo
) {
}
