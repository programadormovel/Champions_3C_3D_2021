package br.com.itb.pra3.champions_3c_3d_2021;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Método de conexão com o banco de dados externo
    public static Connection conn;

    public static Connection conectar(){
        try{
            // Solicita permissão de criar um canal para conexão
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            // Verificar se o driver de conexão esta importado
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // Realizar conexão com o banco dados SQL Server
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.167;" +
                    "databaseName=CHAMPIONS;user=sa;password=123456;");
        }catch(SQLException | ClassNotFoundException exception)
        {
            exception.getMessage();
        }
        return conn;
    }

}
