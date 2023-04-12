package jwt.validation.wowsca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int codigo;
    private String username;//email
    private String password;//senha
    private String token;//n tem banco
    private String permissao;// ADMIN/USER banco
    private String nome;
    private String cpf;
    private boolean ativo;
    
    public Usuario(String username, String password){
        this.username = username;
        this.password = password;
    }
    public Usuario(String username, String password, String permissao){
        this.username = username;
        this.password = password;
        this.permissao = permissao;
    }
}
