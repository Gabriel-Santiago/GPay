package BancoDigital.GPay.model;

import BancoDigital.GPay.dto.UsuarioDTO;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"cpf", "email"})})
public class Cliente extends Usuario{
    private long cpf;

    public Cliente (UsuarioDTO usuarioDTO){
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.senha = usuarioDTO.senha();
        this.cpf = usuarioDTO.cpf();
        this.saldo = usuarioDTO.saldo();
    }
}
