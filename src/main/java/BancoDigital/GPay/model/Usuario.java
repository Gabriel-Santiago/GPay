package BancoDigital.GPay.model;

import BancoDigital.GPay.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    protected String nome;
    protected String email;
    protected String senha;
    protected BigDecimal saldo;

    public Usuario (UsuarioDTO usuarioDTO){
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.senha = usuarioDTO.senha();
        this.saldo = usuarioDTO.saldo();
    }
}
