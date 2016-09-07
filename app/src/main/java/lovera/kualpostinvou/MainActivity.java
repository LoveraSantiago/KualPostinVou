package lovera.kualpostinvou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.conexao.Conexao;
import lovera.kualpostinvou.conexao.MsgFromConexao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.ListaEstabelecimentosActivity;

public class MainActivity extends AppCompatActivity implements MsgFromConexao{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void consumirEstabelecimentos(View view){
        String municipio = getStringFromIptText(R.id.edtMunicipio);
        String uf = getStringFromIptText(R.id.edtUf);
        int paginas = Integer.parseInt(getStringFromIptText(R.id.edtPaginas));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.edtQtd));

        Conexao conexao = new Conexao(this);
        conexao.getEstabelecimentos(municipio, uf, null, paginas, qtdd);
    }

    public String getStringFromIptText(int id){
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        Intent intent = new Intent(this, ListaEstabelecimentosActivity.class);
        intent.putExtra("LISTAESTABELECIMENTOS",(Serializable) listaDeEstabelecimentos);
        startActivity(intent);
    }
}
