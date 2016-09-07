package lovera.kualpostinvou.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.adapters.EstabelecimentoAdapter;

public class ListaEstabelecimentosActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.estabelecimentos_lista);

        Intent intent = getIntent();
        List<Estabelecimento> listaDeEstabelecimentos = (List<Estabelecimento>) intent.getSerializableExtra("LISTAESTABELECIMENTOS");

        ListView lv = (ListView) findViewById(R.id.listaEstabelecimentos);
        lv.setAdapter(new EstabelecimentoAdapter(this, listaDeEstabelecimentos));
    }

    public void fechar(View view){
        finish();
    }
}
