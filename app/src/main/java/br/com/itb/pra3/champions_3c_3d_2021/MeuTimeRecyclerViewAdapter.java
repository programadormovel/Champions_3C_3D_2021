package br.com.itb.pra3.champions_3c_3d_2021;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.zip.Inflater;

public class MeuTimeRecyclerViewAdapter extends RecyclerView.Adapter<MeuTimeRecyclerViewAdapter.ViewHolder> {
    public List<Time> mValores;
    public Context mContexto;
    public SQLiteDatabase mBanco;

    public MeuTimeRecyclerViewAdapter(List<Time> times, Context contexto, SQLiteDatabase banco) {

        mValores = times;
        mContexto = contexto;
        mBanco = banco;
    }

    @NonNull
    @Override
    public MeuTimeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listagem_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeuTimeRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mTime = mValores.get(position);
        holder.mNomeView.setText(mValores.get(position).getNome_time());
        holder.mPaisView.setText(mValores.get(position).getPais_time());
        holder.mStatusView.setText(String.valueOf(mValores.get(position).getStatus()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, mValores.get(position).getNome_time() + " foi clicado",
                        Snackbar.LENGTH_LONG).show();
                // Abrir a tela de alteração
                Intent it = new Intent(mContexto, AlterarActivity.class);
                // Passar os dados clicados
                it.putExtra("_id", mValores.get(position).get_id());
                it.putExtra("nome_time", mValores.get(position).getNome_time());
                it.putExtra("pais_time", mValores.get(position).getPais_time());
                it.putExtra("status", mValores.get(position).getStatus());
                mContexto.startActivity(it);
            }
        });

        // Tornar o botão clicável
        holder.mBotaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Programar a exclusão
                Snackbar.make(v, "BOTÃO EXCLUIR ACIONADO", Snackbar.LENGTH_LONG).show();

                // Preparar o argumento de critério da exclusão
                String[] args = {String.valueOf(mValores.get(position).get_id())};

                // Solicita a exclusão no banco de dados local
                long resultado = mBanco.delete("Time", "_id = ?", args);

                if(resultado > 0)
                    Snackbar.make(v, "SUCESSO NA EXCLUSÃO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO NA EXCLUSÃO", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final AppCompatTextView mNomeView;
        public final AppCompatTextView mPaisView;
        public final AppCompatTextView mStatusView;
        public final FloatingActionButton mBotaoExcluir;
        public Time mTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            mNomeView = itemView.findViewById(R.id.item_nome_time);
            mPaisView = itemView.findViewById(R.id.item_pais_time);
            mStatusView = itemView.findViewById(R.id.item_status_time);
            mBotaoExcluir = itemView.findViewById(R.id.btn_excluir);

        }
    }
}
