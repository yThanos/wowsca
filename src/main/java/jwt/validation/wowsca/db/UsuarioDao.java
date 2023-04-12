package jwt.validation.wowsca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Usuario getUserById(int id){
        Usuario user = new Usuario();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM USUARIOS where CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()){
                user.setCodigo(this.resultSet.getInt("CODIGO"));
                user.setNome(this.resultSet.getString("NOME"));
                user.setUsername(this.resultSet.getString("EMAIL"));
                user.setCpf(this.resultSet.getString("CPF"));
                user.setAtivo(this.resultSet.getBoolean("ATIVO"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public Usuario getUserByEmail(String email){
        Usuario user = new Usuario();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM USUARIOS where EMAIL = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, email);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()){
                user.setCodigo(this.resultSet.getInt("CODIGO"));
                user.setNome(this.resultSet.getString("NOME"));
                user.setUsername(this.resultSet.getString("EMAIL"));
                user.setCpf(this.resultSet.getString("CPF"));
                user.setAtivo(this.resultSet.getBoolean("ATIVO"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<Usuario> getUsers(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM USUARIOS where ATIVO = true";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()){
                Usuario user = new Usuario();
                user.setCodigo(this.resultSet.getInt("CODIGO"));
                user.setNome(this.resultSet.getString("NOME"));
                user.setUsername(this.resultSet.getString("EMAIL"));
                user.setCpf(this.resultSet.getString("CPF"));
                user.setAtivo(this.resultSet.getBoolean("ATIVO"));

                usuarios.add(user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return usuarios;
    }

    public void addUser(Usuario user){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "INSERT INTO USUARIOS (NOME, EMAIL, CPF, SENHA, PERMISSAO, ATIVO) VALUES (?, ?, ?, ?, ?, ?)";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, user.getNome());
            this.preparedStatement.setString(2, user.getUsername());
            this.preparedStatement.setString(3, user.getCpf());
            this.preparedStatement.setString(4, user.getPassword());
            this.preparedStatement.setString(5, user.getPermissao());
            this.preparedStatement.setBoolean(6, user.isAtivo());

            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(Usuario user, int id){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE USUARIOS SET NOME = ?, EMAIL = ?, CPF = ?, SENHA = ?, WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, user.getNome());
            this.preparedStatement.setString(2, user.getUsername());
            this.preparedStatement.setString(3, user.getCpf());
            this.preparedStatement.setString(4, user.getPassword());
            this.preparedStatement.setInt(5, id);

            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(int id){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE USUARIOS SET ATIVO = false WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);

            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
