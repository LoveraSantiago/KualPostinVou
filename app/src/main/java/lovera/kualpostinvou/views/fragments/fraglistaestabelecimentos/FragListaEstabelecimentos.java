package lovera.kualpostinvou.views.fragments.fraglistaestabelecimentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.fragments.FragmentMenu;

public class FragListaEstabelecimentos extends FragmentMenu{

    public static String TITULO_FRAGMENT = "Lista de Estabelecimentos";
    public static int ID_FRAGMENT = 4;
    public static int ICONE = 0;

    private Views views;
    private Consumer consumer;

    private boolean estouAtiva;

    public FragListaEstabelecimentos() {
        this.consumer = new Consumer(this);//precisa ser inicializado aqui por causa do setArguments
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listaestabelecimentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.estouAtiva = true;
        this.views = new Views(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.estouAtiva = false;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.consumer.setListaEstabelecimentos((List<Estabelecimento>) args.get("LISTAESTABELECIMENTOS"));
        this.consumer.setEstabelecimento((Estabelecimento) args.get("ESTABELECIMENTOSALVO"));
    }

    public void inicializarFragEstabelecimento(){
        this.consumer.fromActivity_consumirEstabelecimento();
    }

    public boolean isEstouAtiva() {
        return estouAtiva;
    }

    //Getter Componentes
    public Consumer getConsumer() {
        return consumer;
    }

    public Views getViews() {
        return views;
    }

    //Metodos sobrescritos herdados da classe pai FragmentMenu
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
