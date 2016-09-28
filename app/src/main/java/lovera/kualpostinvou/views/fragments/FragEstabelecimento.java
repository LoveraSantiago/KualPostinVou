package lovera.kualpostinvou.views.fragments;

import android.app.Activity;
import android.content.Context;
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

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.adapters.ViewPagerEstabAdapter;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.receivers.NomeGeoLocalizacaoReceiver;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

public class FragEstabelecimento extends FragmentMenu implements NomeGeoLocalizacaoReceiver.Receiver{

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimento";
    public static int ID_FRAGMENT = 5;
    public static int ICONE = R.drawable.icn2;

    private Estabelecimento estabelecimento;

    private ViewPager viewPager;
    private Bundle savedInstanceState;
    private StreetViewPanoramaView streetViewPanoramaView;

    private NomeGeoLocalizacaoReceiver receiver;
    private MsgToActivity msgToActivity;

    private FragEstabelecimento_Filho1 fragFilho1;
    private FragEstabelecimento_Filho2 fragFilho2;

    public FragEstabelecimento() {
        inicializarReceivers();
        inicializarFragFilhos();
    }

    private void inicializarReceivers(){
        this.receiver = new NomeGeoLocalizacaoReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    private void inicializarFragFilhos(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("ESTABELECIMENTO", estabelecimento);

        this.fragFilho1 = new FragEstabelecimento_Filho1();
        this.fragFilho1.setArguments(bundle);
        this.fragFilho2 = new FragEstabelecimento_Filho2();
        this.fragFilho2.setArguments(bundle);
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
    }

    private void inicializarComponentes(){
        inicializarViewPager();
        inicializarTabLayout();
    }

    private void inicializarViewPager(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        this.viewPager = (ViewPager) getActivity().findViewById(R.id.f5_viewpager);

        ViewPagerEstabAdapter adapter = new ViewPagerEstabAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(this.fragFilho1, "filho1");
        adapter.addFrag(this.fragFilho2, "filho2");
        this.viewPager.setAdapter(adapter);
    }

    private void inicializarTabLayout(){
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.f5_tablayout);
        tabLayout.setupWithViewPager(this.viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1){
//                    fragFilho1.fazerAlgo();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    public NomeGeoLocalizacaoReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Localizacao localizacao = getLocalizacaoDoOnReceiveResult(resultData);
        LatLng latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());

        inicializarStreetView(latLng);
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

    private void inicializarStreetView(final LatLng latLng){
        this.streetViewPanoramaView = (StreetViewPanoramaView) getActivity().findViewById(R.id.f5_streetviewpanorama);
        this.streetViewPanoramaView.onCreate(this.savedInstanceState);
        this.streetViewPanoramaView.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        msgToActivity.fecharProgresso();
                        panorama.setPosition(latLng);
                    }
                });
        this.streetViewPanoramaView.onResume();
    }
}
