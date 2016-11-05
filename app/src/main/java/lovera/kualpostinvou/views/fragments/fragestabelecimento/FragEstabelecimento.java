package lovera.kualpostinvou.views.fragments.fragestabelecimento;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.fragments.FragmentMenu;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;

public class FragEstabelecimento extends FragmentMenu{

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimento";
    public static int ID_FRAGMENT = 5;
    public static int ICONE = 0;

    private Views views;
    private Controller controller;
    private Receiver receiver2;

    public FragEstabelecimento() {
        this.controller = new Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estabelecimento, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.views = new Views(getActivity(), savedInstanceState, this.controller.getFragFilhos());
        this.controller.onActivityCreated(savedInstanceState);
        this.controller.recuperarObjetosSalvos(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.views.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.views.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.views.onSaveInstanceState(outState);
        this.controller.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.views.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       this.views.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.controller.setArguments(args);
    }

    public Views getViews() {
        return views;
    }

    public Controller getController() {
        return controller;
    }

    public Receiver getReceiver2() {
        return receiver2;
    }

    public CommonsReceiver getCommonsReceiver(){
        return  this.receiver2.getCommonsReceiver();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        this.controller.onReceiveResult(resultCode, resultData);
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
