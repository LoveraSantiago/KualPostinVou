package lovera.kualpostinvou.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.components.helpers.FragEstabelecimentoComponents;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhos;
import lovera.kualpostinvou.views.controllers.FragEstabelecimentoController;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;

public class FragEstabelecimento extends FragmentMenu implements CommonsReceiver.Receiver, MsgToFragFilhos {

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimento";
    public static int ID_FRAGMENT = 5;
    public static int ICONE = 0;

    private FragEstabelecimentoComponents components;
    private FragEstabelecimentoController controller;

    private CommonsReceiver receiver;

    public FragEstabelecimento() {
        inicializarReceivers();
        this.controller = new FragEstabelecimentoController(this);
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("ciclo", "FragEstabelecimento onCreateView");
        return inflater.inflate(R.layout.fragment_estabelecimento, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("ciclo", "FragEstabelecimento onActivityCreated");
        this.components = new FragEstabelecimentoComponents(getActivity(), savedInstanceState, this.controller.getFragFilhos());
        this.controller.onActivityCreated(savedInstanceState);
        this.controller.recuperarObjetosSalvos(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ciclo", "FragEstabelecimento onResume");
        this.components.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("ciclo", "FragEstabelecimento onLowMemory");
        this.components.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("ciclo", "FragEstabelecimento onSaveInstanceState");
        this.components.onSaveInstanceState(outState);
        this.controller.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ciclo", "FragEstabelecimento onPause");
        this.components.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ciclo", "FragEstabelecimento onDestroy");
       this.components.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Log.i("ciclo", "FragEstabelecimento setArguments");
        this.controller.setArguments(args);
    }

    public FragEstabelecimentoComponents getComponents() {
        return components;
    }

    public CommonsReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        this.controller.onReceiveResult(resultCode, resultData);
    }

    @Override
    public List<Profissional> getListaDeProfissionais() {
        return this.controller.getListaDeProfissionais();
    }

    public void setListaDeProfissionais(List<Profissional> listaDeProfissionais) {
       this.controller.setListaDeProfissionais(listaDeProfissionais);
    }

    @Override
    public List<Especialidade> getListaDeEspecialidades() {
        return this.controller.getListaDeEspecialidades();
    }

    @Override
    public Fragment getPaiFragment() {
        return this;
    }

    public void setListaDeEspecialidades(List<Especialidade> listaDeEspecialidades) {
        this.controller.setListaDeEspecialidades(listaDeEspecialidades);
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

    //Metodos do fragFilhoAvaliacao
    public void showDialogCadastrarTempoDeAtendimento(){
        this.controller.showDialogCadastrarTempoDeAtendimento();
    }
}
