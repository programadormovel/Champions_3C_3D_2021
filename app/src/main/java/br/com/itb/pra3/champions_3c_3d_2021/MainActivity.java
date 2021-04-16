package br.com.itb.pra3.champions_3c_3d_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    AppCompatButton botao, botaoPesquisar;
    AppCompatEditText edtNome, edtPais;
    SQLiteDatabase banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Estabelecer vínculos de objetos
        edtNome = findViewById(R.id.nome_time);
        edtPais = findViewById(R.id.pais_time);
        botao = findViewById(R.id.btn_salvar);  // Vínculo objeto java com objeto da janela
        botaoPesquisar = findViewById(R.id.btn_pesquisar);

        // Caminho acima da API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Banco de dados local
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null, null);

        // Criar tabela no banco de dados local
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS [Time] (");
        query.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append("nome_time VARCHAR(256) NOT NULL, ");
        query.append("pais_time VARCHAR(256) NOT NULL, ");
        query.append("status INTEGER NOT NULL);");

        banco.execSQL(query.toString());

        // Preparar botão para salvar dados
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegar os dados da janela e enviar para o banco local
                ContentValues valores = new ContentValues();
                valores.put("nome_time", edtNome.getText().toString());
                valores.put("pais_time", edtPais.getText().toString());
                valores.put("status", 1); // 1 - Ativo  |  0 - Inativo

                long resultado = banco.insert("Time", "_id", valores);

                if(resultado > 0)
                    Snackbar.make(v, "SUCESSO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO NO PROGRAMA", Snackbar.LENGTH_LONG).show();
            }
        });

        // Acionar o botão pesquisar para trocar de janela
        botaoPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Troca de janela
                Intent it = new Intent(MainActivity.this, ListagemActivity.class);
                startActivity(it);
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