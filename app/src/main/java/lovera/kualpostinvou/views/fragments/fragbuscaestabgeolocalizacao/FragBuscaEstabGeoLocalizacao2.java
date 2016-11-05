package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.fragments.FragmentMenu;

public class FragBuscaEstabGeoLocalizacao2 extends FragmentMenu {

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimentos proximos";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.localizacao;

    //Componentes
    private Consumer consumer;
    private Receiver receiver;
    private Views views;
    private Dialogs dialogs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscaestabelecimento_geolocalizacao2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        this.views = new Views(this);
        this.consumer = new Consumer(this);
        this.receiver = new Receiver(this);
        this.dialogs = new Dialogs(getActivity());
    }

    public void consumirEstabelecimentosGeolocalizacao(String categoria) {
        this.consumer.inicio_ConsumirEstabelecimentos(categoria);
    }

    //getters Componentes
    public Consumer getConsumer() {
        return consumer;
    }

    public Dialogs getDialogs() {
        return dialogs;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Views getViews() {
        return views;
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
}
