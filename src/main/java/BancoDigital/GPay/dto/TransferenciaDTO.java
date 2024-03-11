package BancoDigital.GPay.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank
        String remetente,
        @NotBlank
        String destinatario,
        @NotBlank
        BigDecimal valor
) {
}
