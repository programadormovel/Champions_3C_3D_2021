package br.com.itb.pra3.champions_3c_3d_2021;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeDAO {
    public boolean inserirTime(Time time){
        boolean retorno = false;
        PreparedStatement pst;

        try{
            // Definindo a declaração de entrada de dados
            String inserir = "Insert Into Time (codigo, nome_time, pais_time, status) " +
                    "values (?, ?, ?, ?)";
            // Preparando declaração para ser executada
            pst = Conexao.conectar().prepareStatement(inserir);
            // Enviando dados para a declaração de entrada
            pst.setInt(1, time.get_id());
            pst.setString(2, time.getNome_time());
            pst.setString(3, time.getPais_time());
            pst.setInt(4, time.getStatus());
            // Chamar o método de execução do insert
            retorno = pst.execute();

        }catch (SQLException e){
            e.getMessage();
        }

        return retorno;
    }
}
