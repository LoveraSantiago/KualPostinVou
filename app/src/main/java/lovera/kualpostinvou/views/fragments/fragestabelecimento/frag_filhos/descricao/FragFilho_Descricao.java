package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

public class FragFilho_Descricao extends FragmentFilho {

    public static String TITULO_FRAGMENT = "Filho Descricao";
    public static int ID_FRAGMENT = 0;
    public static int ICONE = R.drawable.icn_arquivo;

    private Views views;
    private Consumer consumer;

    public FragFilho_Descricao() {
        this.consumer = new Consumer(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_descricao, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.consumer.inicio_consumirEspecialidades();

        this.views = new Views(this);
        this.views.setarEstabelecimentoParaLabels(this.consumer.getEstabelecimento());
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.consumer.setEstabelecimento((Estabelecimento) args.get("ESTABELECIMENTO"));
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
