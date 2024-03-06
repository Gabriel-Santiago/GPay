package BancoDigital.GPay.dto;

import jakarta.validation.constraints.NotBlank;

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
        long cnpj
) {
}
