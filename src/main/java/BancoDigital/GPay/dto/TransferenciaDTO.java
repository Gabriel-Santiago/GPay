package BancoDigital.GPay.dto;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.model.Lojista;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank
        Cliente remetente,
        @NotBlank
        Lojista destinatario,
        @NotBlank
        BigDecimal valor
) {
}
