package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Categoria;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.ListaEstabelecimentosActivity;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.LocalizacaoService;

public class FragBuscaEstabGeoLocalizacao extends FragmentMenu implements MsgFromConexao {

    public static String TITULO_FRAGMENT = "Estabelecimentos por GPS";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.icn2;

    private SeekBar seekBar;
    private Spinner spinner;

    private HelperGeolocalizacao helperGps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscaestabelecimento_geolocalizacao, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.helperGps = new HelperGeolocalizacao(this);

        inicializarSeekBar();
        inicializarSpinner();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.helperGps.verLocalizacao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == HelperGeolocalizacao.USUARIO_ESCOLHENDO_OPCAO){
            if(resultCode == getActivity().RESULT_OK){
                Intent intent = new Intent(getActivity(), LocalizacaoService.class);
                getActivity().startService(intent);
            }
            else if(resultCode == getActivity().RESULT_CANCELED){
                setTextToLabel("Usuario não permitiu gps", R.id.f2_infogps);
            }
        }
    }

    private void inicializarSeekBar(){
        this.seekBar = (SeekBar) getView().findViewById(R.id.f2_seekbar);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int valor = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valor = progress;
                setTextToLabel(valor, R.id.f2_lblseekbar);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categoria.getTextos());
        this.spinner = (Spinner) getView().findViewById(R.id.f2_spinner);
        this.spinner.setAdapter(adapter);
    }

    public void setTextToLabel(String texto, int id){
        TextView lblCodigo = (TextView) getView().findViewById(id);
        lblCodigo.setText(texto);
    }

    public void setTextToLabel(double texto, int id){
        setTextToLabel(String.valueOf(texto), id);
    }

    public void consumirEstabelecimentosGeolocalizacao(){
        double latitude = Double.parseDouble(getStringFromIptText(R.id.f2_lblLatitude));
        double longitude = Double.parseDouble(getStringFromIptText(R.id.f2_lblLongitude));

        int paginas = Integer.parseInt(getStringFromIptText(R.id.f2_edtPaginas));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.f2_edtQtd));
        int raio = Integer.parseInt(getStringFromIptText(R.id.f2_lblseekbar));
        String categoria = this.spinner.getSelectedItem().toString();

        ConexaoSaude conexaoSaude = new ConexaoSaude(this);
        conexaoSaude.getEstabelecimentos(latitude, longitude, raio, null, categoria, null, paginas, qtdd);
    }

    public String getStringFromIptText(int id) {
        EditText editText = (EditText) getView().findViewById(id);
        return editText.getText().toString();
    }

    public void passarLocalizacao(Localizacao localizacao){
        setTextToLabel(localizacao.getLatitude(), R.id.f2_lblLatitude);
        setTextToLabel(localizacao.getLongitude(), R.id.f2_lblLongitude);
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        Intent intent = new Intent(getActivity(), ListaEstabelecimentosActivity.class);
        intent.putExtra("LISTAESTABELECIMENTOS", (Serializable) listaDeEstabelecimentos);
        startActivity(intent);
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {

    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {

    }

    @Override
    public int getFragmentId() {
        return ID_FRAGMENT;
    }

    @Override
    public String getFragmentTitulo() {
        return TITULO_FRAGMENT;
    }

    @Override
    public int getIcone() {
        return ICONE;
    }
}
