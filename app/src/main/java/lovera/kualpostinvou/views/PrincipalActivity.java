package lovera.kualpostinvou.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.components.helpers.PrincipalActivityComponents;
import lovera.kualpostinvou.views.contratos.FragmentInfo;
import lovera.kualpostinvou.views.contratos.MsgFromNavigationDrawer;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao2;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabelecimentos;
import lovera.kualpostinvou.views.fragments.FragEstabelecimento;
import lovera.kualpostinvou.views.fragments.FragListaEstabelecimentos;
import lovera.kualpostinvou.views.fragments.FragRedesSociais;
import lovera.kualpostinvou.views.fragments.FragmentMenu;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.LocalizacaoService;
import lovera.kualpostinvou.views.services.ServicesNames;

//TODO: colocar restauração dos estados das fragments principais ver => https://developer.android.com/training/basics/activity-lifecycle/recreating.html?hl=pt-br
public class PrincipalActivity extends AppCompatActivity implements MsgFromNavigationDrawer, MsgToActivity, CommonsReceiver.Receiver{

    private static String CHAVE_TOKEN = "token";
    private static String CHAVE_PESSOA = "pessoa";

    private String titulo;
    private String tituloOriginal;

    private PrincipalActivityComponents components;

    private CommonsReceiver receiverToken;
    private CommonsReceiver receiverGps;

    private Map<Integer, FragmentMenu> mapFragments;
    private FragmentManager fragmentManager;
    private FragBuscaEstabelecimentos frag1;
    private FragBuscaEstabGeoLocalizacao2 frag2;
    private FragRedesSociais frag3;
    private Fragment fragAtiva;

    private boolean lancarFragEstabelecimento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tituloOriginal = getTitle().toString();
        this.fragmentManager = getFragmentManager();

        setContentView(R.layout.activity_principal);
        inicializarComponentes();
        this.components = new PrincipalActivityComponents(this, this.mapFragments);
        selectItem(0);

        recuperarObjetosSalvos(savedInstanceState);
    }

    private void inicializarComponentes(){
        inicializarFragmentMap();
        inicializarReceivers();
        inicializarHelperGps();
    }

    private void inicializarReceivers(){
        this.receiverToken = new CommonsReceiver(new Handler());
        this.receiverToken.setReceiver(this);
    }

    private void inicializarFragmentMap(){
        this.frag1 = new FragBuscaEstabelecimentos();
        this.frag2 = new FragBuscaEstabGeoLocalizacao2();
        this.frag3 = new FragRedesSociais();

        this.mapFragments = new HashMap<>();
        this.mapFragments.put(FragRedesSociais.ID_FRAGMENT, this.frag3);
        this.mapFragments.put(FragBuscaEstabelecimentos.ID_FRAGMENT, this.frag1);
        this.mapFragments.put(FragBuscaEstabGeoLocalizacao2.ID_FRAGMENT, this.frag2);
    }

    private void inicializarHelperGps(){
        HelperGeolocalizacao helper = new HelperGeolocalizacao(this);
        Aplicacao.setHelperGps(helper);
    }

    private void recuperarObjetosSalvos(Bundle bundle){
        if(bundle != null){
            Aplicacao.getPessoaLogada().setToken(bundle.getString(CHAVE_TOKEN));
            Aplicacao.getPessoaLogada().setPessoa((Pessoa) bundle.getSerializable(CHAVE_PESSOA));
        }
    }

    @Override
    protected void onStart() {
        Aplicacao.getGoogleCoisas().connect(this);
        Aplicacao.getFaceCoisas().onStart(this);
        this.components.abrirDrawer();
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CHAVE_TOKEN, Aplicacao.getPessoaLogada().getToken());
        outState.putSerializable(CHAVE_PESSOA, Aplicacao.getPessoaLogada().getPessoa());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Aplicacao.getGoogleCoisas().disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Aplicacao.getFaceCoisas().onDestroy();
        super.onDestroy();
    }

    @Override
    public void selectItem(int position) {
        if(position == 0) return;

        this.components.fecharDrawer();

        FragmentMenu fragment = this.mapFragments.get(position);
        setarFragment(fragment);
        setTitle(fragment.getFragmentTitulo());
    }

    @Override
    public void setTitle(CharSequence title) {
        this.titulo = title.toString();
        getSupportActionBar().setTitle(this.titulo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == HelperGeolocalizacao.USUARIO_ESCOLHENDO_OPCAO){
            onActivityResultDoHelperGeolocalizacao(resultCode);
        }
        else{
            this.fragAtiva.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onActivityResultDoHelperGeolocalizacao(int resultCode){
        if(resultCode == PrincipalActivity.RESULT_OK){
            Intent intent = new Intent(this, LocalizacaoService.class);
            intent.putExtra(ReceiversNames.GPSLOCALIZACAO, this.receiverGps);
            startService(intent);
        }
        else if(resultCode == PrincipalActivity.RESULT_CANCELED){
            fecharProgresso();
            this.receiverGps.send(ServicesNames.GPS_SERVICE, null);
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.TOKEN_APPCIVICO){
            if(resultData.getBoolean("RESULTADO")){
                fecharProgresso();
                Aplicacao.getPessoaLogada().setServiceTokenEmAndamento(false);
            }
        }
    }

    @Override
    public boolean isAbertoProgresso() {
        return this.components.isAbertoProgresso();
    }

    //Comunicacao entre Activity e Fragments
    @Override
    public void abrirProgresso(){
        this.components.abrirProgresso();
    }

    @Override
    public void fecharProgresso(){
        this.components.fecharProgresso();
    }

    @Override
    public void setarTextoProgresso(final String texto){
        this.components.setarTextoProgresso(this, texto);
    }

    @Override
    public void setarFragment(Fragment fragment) {
        if(fragment instanceof FragEstabelecimento && this.fragAtiva instanceof FragEstabelecimento){
            this.lancarFragEstabelecimento = false;
        }
        else{
            this.lancarFragEstabelecimento = true;
        }

        remocaoFragEstabelecimento(true);

        this.fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, ((FragmentInfo) fragment).getFragmentTitulo())
                .addToBackStack(null)
                .commit();
        this.fragAtiva = fragment;
    }

    private void remocaoFragEstabelecimento(boolean popUp){
        if(this.fragAtiva instanceof FragEstabelecimento){
            this.fragmentManager.beginTransaction()
                    .remove(this.fragAtiva)
                    .commit();

            if(popUp){
                this.fragmentManager.popBackStack();
            }

            Fragment fragLista = this.fragmentManager.findFragmentByTag(FragListaEstabelecimentos.TITULO_FRAGMENT);
            this.fragAtiva = fragLista;
        }
    }

    public CommonsReceiver getReceiverToken() {
        return receiverToken;
    }

    public CommonsReceiver getReceiverGps() {
        return receiverGps;
    }

    public void setReceiverGps(CommonsReceiver receiverGps) {
        this.receiverGps = receiverGps;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragAtiva instanceof FragEstabelecimento){
            remocaoFragEstabelecimento(false);
        }
        else{
            FragListaEstabelecimentos fragLista = (FragListaEstabelecimentos) this.fragmentManager.findFragmentByTag(FragListaEstabelecimentos.TITULO_FRAGMENT);
            if(fragLista != null && fragLista.isEstouAtiva()){
                fragLista.inicializarFragEstabelecimento();
            }
        }
    }

    public void consumirEstabelecimentos(View view) {
        this.frag1.consumirEstabelecimentos();
    }

    public void cadastrarPerfilTeste(View view){
        this.frag3.cadastrarPerfilTeste();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    //ACOES DO FRAGMENT 2
    public void consumirEstabelecimentosGeolocalizacao(View view){
        if(isAbertoProgresso()) return;
        this.frag2.consumirEstabelecimentosGeolocalizacao(view.getTag().toString());
    }

    public Fragment getFragAtiva() {
        return fragAtiva;
    }

    public void setFragAtiva(Fragment fragment){
        this.fragAtiva = fragment;
    }

    public boolean isLancarFragEstabelecimento() {
        return lancarFragEstabelecimento;
    }
}
