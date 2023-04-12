package jwt.validation.wowsca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    private int codigo;
    private String nome;
    private String descricao;
    private boolean ativo;
    private int codCriador;
    
}
