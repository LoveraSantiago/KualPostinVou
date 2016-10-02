package lovera.kualpostinvou.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.adapters.FragEstabAdapter;
import lovera.kualpostinvou.views.adapters.ViewPagerEstabAdapter;
import lovera.kualpostinvou.views.components.OnTabSelectedListenerImpl;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhos;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Avaliacao;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Desc;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Endereco;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Info;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.services.ServicesNames;

public class FragEstabelecimento extends FragmentMenu implements CommonsReceiver.Receiver, MsgToFragFilhos {

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimento";
    public static int ID_FRAGMENT = 5;
    public static int ICONE = 0;

    private Estabelecimento estabelecimento;

    private ViewPager viewPager;
    private Bundle savedInstanceState;

    private LatLng latLng;
    private StreetViewPanoramaView streetViewPanoramaView;
    StreetViewPanorama panorama;

    private CommonsReceiver receiver;
    private MsgToActivity msgToActivity;

    //Objetos para as FragFilhas
    private List<Profissional> listaDeProfissionais;
    private List<Especialidade> listaDeEspecialidades;
    private FragEstabAdapter adapter;
    private ConexaoSaude conexaoSaude;

    private FragEstabFilho_Desc fragFilhoDescricao;
    private FragEstabFilho_Info fragFilhoInfo;
    private FragEstabFilho_Avaliacao fragFilhoAvaliacao;
    private FragEstabFilho_Endereco fragFilhoEndereco;

    public FragEstabelecimento() {
        inicializarReceivers();
        inicializarFragFilhos();
        inicializarConexao();
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    private void inicializarFragFilhos(){
        this.fragFilhoDescricao = new FragEstabFilho_Desc();
        this.fragFilhoDescricao.setMsg(this);
        this.fragFilhoInfo = new FragEstabFilho_Info();
        this.fragFilhoInfo.setMsg(this);
        this.fragFilhoAvaliacao = new FragEstabFilho_Avaliacao();
        this.fragFilhoAvaliacao.setMsg(this);
        this.fragFilhoEndereco = new FragEstabFilho_Endereco();
    }

    private void inicializarConexao(){
        this.adapter = new FragEstabAdapter(this);
        this.conexaoSaude = new ConexaoSaude(this.adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estabelecimento, container, false);
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.msgToActivity = (MsgToActivity) getActivity();
        this.savedInstanceState = savedInstanceState;

        inicializarComponentes();

        this.msgToActivity.setarTextoProgresso("Localizando fotos do estabelecimento");
        recuperarObjetosSalvos(savedInstanceState);
    }

    private void inicializarComponentes(){
        inicializarViewPager();
        inicializarTabLayout();
        inicializarRestConsumers();
        inicializarStreetView();
    }

    private void inicializarViewPager(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        this.viewPager = (ViewPager) getActivity().findViewById(R.id.f5_viewpager);

        ViewPagerEstabAdapter adapter = new ViewPagerEstabAdapter(activity.getSupportFragmentManager());
        adapter.addFrags(this.fragFilhoDescricao, this.fragFilhoInfo, this.fragFilhoAvaliacao, this.fragFilhoEndereco);
        this.viewPager.setAdapter(adapter);
    }

    private void inicializarTabLayout(){
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.f5_tablayout);
        tabLayout.setupWithViewPager(this.viewPager);
        tabLayout.getTabAt(this.fragFilhoDescricao.getFragmentId()).setIcon(this.fragFilhoDescricao.getIcone());
        tabLayout.getTabAt(this.fragFilhoInfo.getFragmentId()).setIcon(this.fragFilhoInfo.getIcone());
        tabLayout.getTabAt(this.fragFilhoAvaliacao.getFragmentId()).setIcon(this.fragFilhoAvaliacao.getIcone());
        tabLayout.getTabAt(this.fragFilhoEndereco.getFragmentId()).setIcon(this.fragFilhoEndereco.getIcone());
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListenerImpl(this.viewPager));
    }

    private void inicializarRestConsumers(){
        consumirProfissionais();
        consumirEspecialidades();
    }

    private void consumirProfissionais(){
        if(this.listaDeProfissionais == null){
            this.conexaoSaude.getProfissionais(this.estabelecimento.getCodUnidade());
        }
    }

    private void consumirEspecialidades(){
        if(this.listaDeEspecialidades == null){
            this.conexaoSaude.getEspecialidades(this.estabelecimento.getCodUnidade());
        }
    }

    private void recuperarObjetosSalvos(Bundle bundle){
        if(bundle != null){
            Localizacao localizacao = (Localizacao) bundle.getSerializable("LOCALIZACAO");
            this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
            setarPosicaoToPanorama();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onSaveInstanceState(outState);

        Localizacao localizacao = new Localizacao();
        localizacao.setLatitude(this.latLng.latitude);
        localizacao.setLongitude(this.latLng.longitude);
        outState.putSerializable("LOCALIZACAO", localizacao);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onDestroy();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.fragFilhoDescricao.setArguments(args);
        this.fragFilhoInfo.setArguments(args);
        this.fragFilhoEndereco.setArguments(args);

        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    //Metodos sobrescritos herdados da classe pai FragMenu
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

    public CommonsReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.NOME_GEOLOCALIZACAO){
            Localizacao localizacao = getLocalizacaoDoOnReceiveResult(resultData);
            this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
            setarPosicaoToPanorama();
        }
    }

    private Localizacao getLocalizacaoDoOnReceiveResult(Bundle bundle){
        Localizacao result = (Localizacao) bundle.getSerializable("LOCALIZACAO");

        if(result == null){
            result = new Localizacao();
            result.setLatitude(this.estabelecimento.getLat());
            result.setLongitude(this.estabelecimento.getLongi());
        }
        return result;
    }

    private void inicializarStreetView(){
        try{
            this.streetViewPanoramaView = (StreetViewPanoramaView) getActivity().findViewById(R.id.f5_streetviewpanorama);
            this.streetViewPanoramaView.onCreate(this.savedInstanceState);
            this.streetViewPanoramaView.getStreetViewPanoramaAsync(
                    new OnStreetViewPanoramaReadyCallback() {
                        @Override
                        public void onStreetViewPanoramaReady(StreetViewPanorama panoramaCallBack) {
                            panorama = panoramaCallBack;
                        }
                    });
            this.streetViewPanoramaView.onResume();
        }catch (Exception e){}
    }

    private void setarPosicaoToPanorama(){
        this.msgToActivity.fecharProgresso();
        this.panorama.setPosition(this.latLng);
    }

    @Override
    public List<Profissional> getListaDeProfissionais() {
        return listaDeProfissionais;
    }

    public void setListaDeProfissionais(List<Profissional> listaDeProfissionais) {
        this.listaDeProfissionais = listaDeProfissionais;
        this.fragFilhoInfo.setListaDeProfissionais(listaDeProfissionais);
    }

    @Override
    public List<Especialidade> getListaDeEspecialidades() {
        return listaDeEspecialidades;
    }

    @Override
    public Fragment getPaiFragment() {
        return this;
    }

    public void setListaDeEspecialidades(List<Especialidade> listaDeEspecialidades) {
        this.listaDeEspecialidades = listaDeEspecialidades;
        this.fragFilhoDescricao.setListaEspecialidades(listaDeEspecialidades);
    }

    //Metodos do fragFilhoAvaliacao
    public void cadastrarTempoDeAtendimento(){
        this.fragFilhoAvaliacao.cadastrarTempoDeAtendimento();
    }
}
