package lovera.kualpostinvou.views.fragments;

import android.app.Fragment;
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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;
import lovera.kualpostinvou.views.services.LocalizacaoService;
import lovera.kualpostinvou.views.utils.HelperGoogleApi;

public class TempFragment extends Fragment implements MsgFromConexao{

    private HelperGoogleApi helperGoogle;

    private SeekBar seekBar;
    private Spinner spinner;

    private LoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.temp, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.helperGoogle = new HelperGoogleApi(this);
        inicializarSeekBar();
        inicializarSpinner();
        inicializarBtnFacebook();

    }

    @Override
    public void onStart() {
        this.helperGoogle.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        this.helperGoogle.disconnect();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == HelperGoogleApi.USUARIO_ESCOLHENDO_OPCAO){
            if(resultCode == getActivity().RESULT_OK){
                Intent intent = new Intent(getActivity(), LocalizacaoService.class);
                getActivity().startService(intent);
            }
            else if(resultCode == getActivity().RESULT_CANCELED){
                setTextToLabel("Usuario não permitiu gps", R.id.infoGps);
            }

        }
        Facebook_Coisas.getFaceCoisasUnicaInstancia().getCallbackManager().onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {

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

    private void inicializarBtnFacebook(){
        if(AccessToken.getCurrentAccessToken() == null){
            setTextToLabel("Não", R.id.lblStatusFaceLogado);
        }
        else{
            setTextToLabel("Sim", R.id.lblStatusFaceLogado);
        }

        this.loginButton = (LoginButton) findViewById(R.id.faceLoginButton);
        this.loginButton.registerCallback(Facebook_Coisas.getFaceCoisasUnicaInstancia().getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setTextToLabel("Acabou de logar", R.id.lblStatusFaceLogado);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(TempActivity.this, "Facebook cancelado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(TempActivity.this, "Facebook erro", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logoutFacebook(View view){
        LoginManager.getInstance().logOut();
    }
}
