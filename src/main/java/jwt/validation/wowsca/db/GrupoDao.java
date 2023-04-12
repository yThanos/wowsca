package jwt.validation.wowsca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jwt.validation.wowsca.model.Grupo;

public class GrupoDao {
    private String sql;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Grupo getGrupoById(int codGrupo){
        Grupo grupo = new Grupo();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM GRUPOS where CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, codGrupo);
            this.resultSet = this.preparedStatement.executeQuery();
            
            while(this.resultSet.next()){
                grupo.setCodigo(this.resultSet.getInt("CODIGO"));
                grupo.setNome(this.resultSet.getString("NOME"));
                grupo.setDescricao(this.resultSet.getString("DESCRICAO"));
                grupo.setUsuario(this.resultSet.getInt("CODIGO_USUARIO"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return grupo;
    }

    public ArrayList<Grupo> getGrupos(){
        ArrayList<Grupo> grupos = new ArrayList<>();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM GRUPOS";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.resultSet = this.preparedStatement.executeQuery();
            
            while(this.resultSet.next()){
                Grupo grupo = new Grupo();
                grupo.setCodigo(this.resultSet.getInt("CODIGO"));
                grupo.setNome(this.resultSet.getString("NOME"));
                grupo.setDescricao(this.resultSet.getString("DESCRICAO"));
                grupo.setUsuario(this.resultSet.getInt("CODIGO_USUARIO"));
                grupo.setAtivo(this.resultSet.getBoolean("ATIVO"));
                grupos.add(grupo);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return grupos;
    }

    public void addGrupo(Grupo grupo){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "INSERT INTO GRUPOS (NOME, DESCRICAO, CODIGO_USUARIO) VALUES (?, ?, ?)";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, grupo.getNome());
            this.preparedStatement.setString(2, grupo.getDescricao());
            this.preparedStatement.setInt(3, grupo.getUsuario());
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateGrupo(Grupo grupo){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE GRUPOS SET NOME = ?, DESCRICAO = ?, CODIGO_USUARIO = ? WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, grupo.getNome());
            this.preparedStatement.setString(2, grupo.getDescricao());
            this.preparedStatement.setInt(3, grupo.getUsuario());
            this.preparedStatement.setInt(4, grupo.getCodigo());
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteGrupo(int codGrupo){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE GRUPOS SET ATIVO = false WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, codGrupo);
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
