package lovera.kualpostinvou.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.components.navigationdrawer.ActionBarDrawerToggleImpl;
import lovera.kualpostinvou.views.components.navigationdrawer.RecyclerViewAdapterImpl;
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

    private Toolbar toolbar;

    private CommonsReceiver receiver;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Map<Integer, FragmentMenu> mapFragments;
    private FragmentManager fragmentManager;
    private FragBuscaEstabelecimentos frag1;
    private FragBuscaEstabGeoLocalizacao2 frag2;
    private FragRedesSociais frag3;
    private Fragment fragAtiva;

    //Componentes progresso
    private TextView lblStatus;
    private View uiStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tituloOriginal = getTitle().toString();
        this.fragmentManager = getFragmentManager();

        setContentView(R.layout.activity_principal);
        inicializarComponentes();
        selectItem(0);

        recuperarObjetosSalvos(savedInstanceState);
    }

    private void inicializarComponentes(){
        inicializarToolbar();
        inicializarFragmentMap();
        inicializarNavigationDrawer();
        inicializarComponentesProgresso();
        inicializarReceivers();
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    private void inicializarToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    private void inicializarNavigationDrawer(){
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mAdapter = new RecyclerViewAdapterImpl(this.mapFragments, this);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggleImpl(this, this.mDrawerLayout, this.toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);

        this.mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    private void inicializarComponentesProgresso(){
        this.uiStatus    = findViewById(R.id.a1_ui_status);
        this.lblStatus   = (TextView) findViewById(R.id.a1_lblstatus);
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
        this.mDrawerLayout.openDrawer(this.mRecyclerView);
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

        this.mDrawerLayout.closeDrawer(this.mRecyclerView);

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
        return this.uiStatus.getVisibility() == View.VISIBLE;
    }

    //Comunicacao entre Activity e Fragments
    @Override
    public void abrirProgresso(){
        this.uiStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void fecharProgresso(){
        this.uiStatus.setVisibility(View.GONE);
    }

    @Override
    public void setarTextoProgresso(final String texto){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblStatus.setText(texto);
            }
        });
    }

    @Override
    public void setarFragment(Fragment fragment) {
        remocaoFragEstabelecimento();

        this.fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, ((FragmentInfo) fragment).getFragmentTitulo())
                .addToBackStack(null)
                .commit();
        this.fragAtiva = fragment;
    }

    private void remocaoFragEstabelecimento(){
        if(this.fragAtiva instanceof FragEstabelecimento){
            this.fragmentManager.beginTransaction()
                    .remove(this.fragAtiva)
                    .commit();
//            this.fragmentManager.popBackStack();

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
            remocaoFragEstabelecimento();
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
