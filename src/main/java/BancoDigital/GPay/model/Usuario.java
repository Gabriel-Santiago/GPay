package BancoDigital.GPay.model;

import BancoDigital.GPay.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    protected String nome;
    protected String email;
    protected String senha;

    public Usuario (UsuarioDTO usuarioDTO){
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.senha = usuarioDTO.senha();
    }
}
