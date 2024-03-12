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
    private int id;
    private String remetente;
    private String destinatario;
    private BigDecimal valor;

    public Transferencia(TransferenciaDTO transferenciaDTO){
        this.id = transferenciaDTO.id();
        this.remetente = transferenciaDTO.remetente();
        this.destinatario = transferenciaDTO.destinatario();
        this.valor = transferenciaDTO.valor();
    }
}
