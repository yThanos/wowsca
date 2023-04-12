package jwt.validation.wowsca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jwt.validation.wowsca.model.Categoria;

public class CategoriaDao {
    private String sql;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Categoria getCategoriaById(int id){
        Categoria categoria = new Categoria();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM CATEGORIAS where CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.resultSet = this.preparedStatement.executeQuery();
            
            while(this.resultSet.next()){
                categoria.setCodigo(this.resultSet.getInt("CODIGO"));
                categoria.setNome(this.resultSet.getString("NOME"));
                categoria.setDescricao(this.resultSet.getString("DESCRICAO"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return categoria;
    }
    
    public ArrayList<Categoria> getUserCategorias(int codUser){
        ArrayList<Categoria> categorias = new ArrayList<>();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM CATEGORIAS where ATIVO = true and CODIGO_USUARIO = ? or CODIGO_USUARIO = 0";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, codUser);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()){
                Categoria cat = new Categoria();
                cat.setCodigo(this.resultSet.getInt("CODIGO"));
                cat.setNome(this.resultSet.getString("NOME"));
                cat.setDescricao(this.resultSet.getString("DESCRICAO"));
                
                categorias.add(cat);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return categorias;
    }

    public void addCategoria(Categoria categoria){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "INSERT INTO CATEGORIAS (NOME, DESCRICAO, CODIGO_USUARIO) VALUES (?, ?, ?)";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, categoria.getNome());
            this.preparedStatement.setString(2, categoria.getDescricao());
            this.preparedStatement.setInt(3, categoria.getCodCriador());
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void updateCategoria(Categoria categoria){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE CATEGORIAS SET NOME = ?, DESCRICAO = ? WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, categoria.getNome());
            this.preparedStatement.setString(2, categoria.getDescricao());
            this.preparedStatement.setInt(3, categoria.getCodigo());
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteCategoria(int id){
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "UPDATE CATEGORIAS SET ATIVO = false WHERE CODIGO = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
