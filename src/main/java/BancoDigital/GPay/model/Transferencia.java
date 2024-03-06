package BancoDigital.GPay.model;

import BancoDigital.GPay.dto.TransferenciaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {
    private Cliente remetente;
    private Lojista destinatario;
    private BigDecimal valor;

    public Transferencia(TransferenciaDTO transferenciaDTO){
        this.remetente = transferenciaDTO.remetente();
        this.destinatario = transferenciaDTO.destinatario();
        this.valor = transferenciaDTO.valor();
    }
}
