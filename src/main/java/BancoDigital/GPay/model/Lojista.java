package BancoDigital.GPay.model;

import BancoDigital.GPay.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lojista extends Usuario{
    private long cnpj;

    public Lojista(UsuarioDTO usuarioDTO){
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.senha = usuarioDTO.senha();
        this.cnpj = usuarioDTO.cnpj();
        this.saldo = usuarioDTO.saldo();
    }
}
