package br.com.itb.pra3.champions_3c_3d_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    SQLiteDatabase banco;
    FloatingActionButton botaoExportar;
    List<Time> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        // Vinculo do botao com o botao da janela
        botaoExportar = findViewById(R.id.btn_exportar);

        // Caminho do banco > API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Abrir o banco de dados local
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null);


        // pesquisa dos dados no banco
        times = new ArrayList<>();

        try{
            // Declaração do objeto adapter
            MeuTimeRecyclerViewAdapter adapter;
            // Vínculo do objeto RecyclerView do java com o objeto da tela
            RecyclerView rvLista = findViewById(R.id.lista_times);
            // Definição do gerenciado de layout do RecyclerView
            rvLista.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            // Obter os dados do banco de dados local (SQLiteDatabase)
            // Vetor de colunas da tabela a ser pesquisada
            String[] colunas = {"_id, nome_time, pais_time, status"};
            // SELECT _id, nome_time, pais_time, status FROM Time
            Cursor cursor = banco.query("Time", colunas, null,
                    null, null, null, null);
            if(cursor != null){
                // Percorrer o cursor para capturar os dados
                while(cursor.moveToNext()){
                    times.add(new Time(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)));
                }
            } else {
                Snackbar.make(rvLista, "NÃO HÁ LINHAS NA TABELA Time", Snackbar.LENGTH_LONG).show();
            }

            // Alimentar lista com dados do banco de dados local
            adapter = new MeuTimeRecyclerViewAdapter(times,
                    ListagemActivity.this, banco);
            rvLista.setAdapter(adapter);

        }catch (Exception e){
            e.getMessage();
        }

        // Preparar botão de Exportação para receber o clique
        botaoExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vetor de colunas da tabela a ser pesquisada
                String[] colunas = {"_id, nome_time, pais_time, status"};
                // SELECT _id, nome_time, pais_time, status FROM Time
                Cursor cursor = banco.query("Time", colunas, null,
                        null, null, null, null);
                if(cursor != null){
                    // Percorrer o cursor para capturar os dados
                    while(cursor.moveToNext()){
                        times.add(new Time(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3)));
                    }

                    TimeDAO timeDAO = new TimeDAO();

                    // Exportar dados lidos do SQLiteDatabase para o SQL Server
                    for(Time timeAtual: times){
                        boolean resultado = timeDAO.inserirTime(timeAtual);
                        if(resultado != false){
                            Snackbar.make(v, "Time " + timeAtual.getNome_time() +
                                    " não EXPORTADO!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                } else {
                    Snackbar.make(v, "NÃO HÁ LINHAS NA TABELA Time", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        banco.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Caminho acima da API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Banco de dados local
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null, null);
    }
}