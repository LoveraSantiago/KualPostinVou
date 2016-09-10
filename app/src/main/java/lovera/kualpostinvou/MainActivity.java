package lovera.kualpostinvou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.ListaEstabelecimentosActivity;
import lovera.kualpostinvou.views.utils.HelperGoogleApi;

public class MainActivity extends AppCompatActivity implements MsgFromConexao {

    private HelperGoogleApi helperGoogle;

    public static final int RESULT_FROM_HELPERGOOGLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.helperGoogle = new HelperGoogleApi(this);
    }

    @Override
    protected void onStart() {
        this.helperGoogle.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        this.helperGoogle.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_FROM_HELPERGOOGLE){
            try {
                this.helperGoogle.getLocalizacao();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void consumirEstabelecimentos(View view) {
        String municipio = getStringFromIptText(R.id.edtMunicipio);
        String uf = getStringFromIptText(R.id.edtUf);
        int paginas = Integer.parseInt(getStringFromIptText(R.id.edtPaginas));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.edtQtd));

        ConexaoSaude conexaoSaude = new ConexaoSaude(this);
        conexaoSaude.getEstabelecimentos(municipio, uf, null, paginas, qtdd);
    }

    public String getStringFromIptText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        Intent intent = new Intent(this, ListaEstabelecimentosActivity.class);
        intent.putExtra("LISTAESTABELECIMENTOS", (Serializable) listaDeEstabelecimentos);
        startActivity(intent);
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {

    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    public void setTextToLabel(String texto, int id){
        TextView lblCodigo = (TextView) findViewById(id);
        lblCodigo.setText(texto);
    }
}
