package br.com.itb.pra3.champions_3c_3d_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class AlterarActivity extends AppCompatActivity {

    SQLiteDatabase banco;
    AppCompatEditText edtNomeTime;
    AppCompatEditText edtPaisTime;
    AppCompatEditText edtStatusTime;
    AppCompatButton botaoAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        // Caminho acima da API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Banco de dados local
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null, null);

        // Receber dados da janela de listagem
        Intent intent = getIntent();
        final int id = intent.getIntExtra("_id", 0);
        String nomeTime = intent.getStringExtra("nome_time");
        String paisTime = intent.getStringExtra("pais_time");
        int status = intent.getIntExtra("status", -1);

        // Implementar alteração de dados do banco de dados local
        edtNomeTime = findViewById(R.id.nome_time_alterar);
        edtPaisTime = findViewById(R.id.pais_time_alterar);
        edtStatusTime = findViewById(R.id.status_time_alterar);
        botaoAlterar = findViewById(R.id.btn_alterar);

        edtNomeTime.setText(nomeTime);
        edtPaisTime.setText(paisTime);
        edtStatusTime.setText(String.valueOf(status));

        botaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Carregar cursor com valores
                ContentValues valores = new ContentValues();
                valores.put("nome_time", edtNomeTime.getText().toString());
                valores.put("pais_time", edtPaisTime.getText().toString());
                valores.put("status", edtStatusTime.getText().toString());

                String[] args = {String.valueOf(id)};

                long resultado = banco.update("Time", valores, "_id = ?",
                        args);

                if(resultado > 0)
                    Snackbar.make(v, "SUCESSO NA ALTERAÇÃO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO NA ALTERAÇÃO", Snackbar.LENGTH_LONG).show();
            }
        });




    }
}