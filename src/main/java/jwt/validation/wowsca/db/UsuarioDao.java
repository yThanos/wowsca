package jwt.validation.wowsca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jwt.validation.wowsca.model.Usuario;

public class UsuarioDao {
    private String sql;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Usuario getUser(String username){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM USUARIOS WHERE EMAIL = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();

            while (this.resultSet.next()){
                Usuario user = new Usuario();
                user.setUsername(this.resultSet.getString("email"));
                user.setPassword(new BCryptPasswordEncoder().encode(this.resultSet.getString("senha")));
                user.setPermissao(this.resultSet.getString("permissao"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(Usuario user){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "INSERT INTO USUARIOS (NOME, EMAIL, CPF, SENHA, PERMISSAO, ATIVO) VALUES (?, ?, ?, ?, ?, ?)";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(2, user.getUsername());
            this.preparedStatement.setString(4, user.getPassword());
            this.preparedStatement.setString(5, user.getPermissao());
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
