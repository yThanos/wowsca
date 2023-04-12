package jwt.validation.wowsca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jwt.validation.wowsca.model.Despesa;

public class DespesaDao {
    private String sql;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ArrayList<Despesa> getUserDespesa(int codUser){
        ArrayList<Despesa> despesas = new ArrayList<>();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM DESPESAS where CODIGO_USUARIO = ? and ATIVO = true";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, codUser);
            this.resultSet = this.preparedStatement.executeQuery();


            while(this.resultSet.next()){
                Despesa desp = new Despesa();
                desp.setCodigo(this.resultSet.getInt("CODIGO"));
                desp.setNome(this.resultSet.getString("NOME"));
                desp.setDescricao(this.resultSet.getString("DESCRICAO"));
                desp.setValor(this.resultSet.getDouble("VALOR"));
                

                despesas.add(desp);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return despesas;
    }

    public ArrayList<Despesa> getGrupDespesa(int codGrup){
        ArrayList<Despesa> despesas = new ArrayList<>();
        try (Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM DESPESAS where CODIGO_GRUPO = ? and ATIVO = true";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, codGrup);
            this.resultSet = this.preparedStatement.executeQuery();


            while(this.resultSet.next()){
                Despesa desp = new Despesa();
                

                despesas.add(desp);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return despesas;
    }


}
