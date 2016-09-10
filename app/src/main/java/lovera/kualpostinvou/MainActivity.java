package lovera.kualpostinvou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Categoria;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.ListaEstabelecimentosActivity;
import lovera.kualpostinvou.views.services.LocalizacaoService;
import lovera.kualpostinvou.views.utils.HelperGoogleApi;

public class MainActivity extends AppCompatActivity implements MsgFromConexao {

    private HelperGoogleApi helperGoogle;

    private SeekBar seekBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.helperGoogle = new HelperGoogleApi(this);
        inicializarSeekBar();
        inicializarSpinner();
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
        if(requestCode == HelperGoogleApi.USUARIO_ESCOLHENDO_OPCAO){
            switch (resultCode){
                case MainActivity.RESULT_OK:
                    Intent intent = new Intent(this, LocalizacaoService.class);
                    this.startService(intent);
                    break;
                case MainActivity.RESULT_CANCELED:
                    setTextToLabel("Usuario não permitiu gps", R.id.infoGps);
                    break;
            }

        }
        else if(requestCode == HelperGoogleApi.DISPOSITIVO_NAO_TEM_GPS){
            setTextToLabel("Dispositivo não tem gps", R.id.infoGps);
        }
    }

    public void falhaDeLocalizacao(){
        setTextToLabel("Posicao não localizada", R.id.infoGps);
    }

    public void consumirEstabelecimentos(View view) {
        String municipio = getStringFromIptText(R.id.edtMunicipio);
        String uf = getStringFromIptText(R.id.edtUf);
        int paginas = Integer.parseInt(getStringFromIptText(R.id.edtPaginas));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.edtQtd));

        ConexaoSaude conexaoSaude = new ConexaoSaude(this);
        conexaoSaude.getEstabelecimentos(municipio, uf, null, paginas, qtdd);
    }

    public void consumirEstabelecimentosGeolocalizacao(View view){

        double latitude = Double.parseDouble(getStringFromLabelText(R.id.lblLatitude));
        double longitude = Double.parseDouble(getStringFromLabelText(R.id.lblLongitude));

        int paginas = Integer.parseInt(getStringFromIptText(R.id.edtPaginas2));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.edtQtd2));
        int raio = Integer.parseInt(getStringFromLabelText(R.id.lblSeekBar));
        String categoria = this.spinner.getSelectedItem().toString();

        ConexaoSaude conexaoSaude = new ConexaoSaude(this);
        conexaoSaude.getEstabelecimentos(latitude, longitude, raio, null, categoria, null, paginas, qtdd);

    }

    public String getStringFromLabelText(int id){
        TextView editText = (TextView) findViewById(id);
        return editText.getText().toString();
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

    public void passarLocalizacao(Localizacao localizacao){
        setTextToLabel(localizacao.getLatitude(), R.id.lblLatitude);
        setTextToLabel(localizacao.getLongitude(), R.id.lblLongitude);
    }

    public void setTextToLabel(String texto, int id){
        TextView lblCodigo = (TextView) findViewById(id);
        lblCodigo.setText(texto);
    }

    public void setTextToLabel(int texto, int id){
        setTextToLabel(String.valueOf(texto), id);
    }

    public void setTextToLabel(double texto, int id){
        setTextToLabel(String.valueOf(texto), id);
    }

    private void inicializarSeekBar(){
        this.seekBar = (SeekBar) findViewById(R.id.seek_bar);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int valor = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valor = progress;
                setTextToLabel(valor, R.id.lblSeekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void inicializarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Categoria.getTextos());
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.spinner.setAdapter(adapter);
    }
}
