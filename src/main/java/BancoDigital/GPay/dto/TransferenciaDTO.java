package BancoDigital.GPay.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        int id,
        @NotBlank
        String remetente,
        @NotBlank
        String destinatario,
        @NotBlank
        BigDecimal valor
) {
}
