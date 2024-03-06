package BancoDigital.GPay.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank
        long remetente,
        @NotBlank
        long destinatario,
        @NotBlank
        BigDecimal valor
) {
}
