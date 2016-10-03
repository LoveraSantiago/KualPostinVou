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
import lovera.kualpostinvou.views.services.ServicesNames;

//TODO: colocar restauração dos estados das fragments principais ver => https://developer.android.com/training/basics/activity-lifecycle/recreating.html?hl=pt-br
public class PrincipalActivity extends AppCompatActivity implements MsgFromNavigationDrawer, MsgToActivity, CommonsReceiver.Receiver{

    private static String CHAVE_TOKEN = "token";
    private static String CHAVE_PESSOA = "pessoa";

    private String titulo;
    private String tituloOriginal;

    private PrincipalActivityComponents components;

    private CommonsReceiver receiver;

    private Map<Integer, FragmentMenu> mapFragments;
    private FragmentManager fragmentManager;
    private FragBuscaEstabelecimentos frag1;
    private FragBuscaEstabGeoLocalizacao2 frag2;
    private FragRedesSociais frag3;
    private Fragment fragAtiva;

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
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
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

    private void recuperarObjetosSalvos(Bundle bundle){
        if(bundle != null){
            Aplicacao.getPessoaLogada().setToken(bundle.getString(CHAVE_TOKEN));
            Aplicacao.getPessoaLogada().setPessoa((Pessoa) bundle.getSerializable(CHAVE_PESSOA));
        }
    }

    @Override
    protected void onStart() {
        Aplicacao.getGoogleCoisas().connect();
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
        this.fragAtiva.onActivityResult(requestCode, resultCode, data);
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
//            setarFragment(fragLista);
        }
    }

    public CommonsReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragAtiva instanceof FragEstabelecimento){
            remocaoFragEstabelecimento(false);
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
    public void incrementarDistancia(View view){
        this.frag2.incrementarDistancia();
    }

    public void decrementarDistancia(View view){ this.frag2.decrementarDistancia(); }

    public void consumirEstabelecimentosGeolocalizacao(View view){
        if(isAbertoProgresso()) return;
        this.frag2.consumirEstabelecimentosGeolocalizacao(view.getTag().toString());
    }

    //ACOES DO FRAGMENT ESTABELECIMENTO
    public void cadastrarTempoDeAtendimento(View view){
        if(this.fragAtiva instanceof FragEstabelecimento){
            ((FragEstabelecimento)this.fragAtiva).cadastrarTempoDeAtendimento();
        }
    }

    public Fragment getFragAtiva() {
        return fragAtiva;
    }
}
