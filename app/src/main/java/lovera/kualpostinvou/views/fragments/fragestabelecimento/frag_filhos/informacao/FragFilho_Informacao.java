package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.adapters.FragEstabFilhoInfoAdapter;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

import static lovera.kualpostinvou.views.utils.Utils_View.gerarLinhaParaTabela;
import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_centro;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_esquerda;

public class FragFilho_Informacao extends FragmentFilho {


    public static String TITULO_FRAGMENT = "Filho Info";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.icn_info;

    private List<Profissional> listaDeProfissionais;

    private Consumer consumer;
    private Views views;

    public FragFilho_Informacao() {
        this.consumer = new Consumer(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_info, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.consumer.setEstabelecimento((Estabelecimento) args.get("ESTABELECIMENTO"));
    }

    @Override
    public void onStart() {
        super.onStart();
        consumirProfissionais();
        this.views = new Views(this);
        this.views.setarEstabelecimentoParaLabels(this.consumer.getEstabelecimento());
    }

    private void consumirProfissionais(){
        if(this.listaDeProfissionais != null){
            this.views.setListaDeProfissionaisParaTabela(this.listaDeProfissionais);
        }
        else{
            FragEstabFilhoInfoAdapter adapter = new FragEstabFilhoInfoAdapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getProfissionais(this.estabelecimento.getCodUnidade());
        }
    }

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
