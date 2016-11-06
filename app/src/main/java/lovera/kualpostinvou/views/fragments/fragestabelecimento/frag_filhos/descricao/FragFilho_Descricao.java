package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.adapters.FragEstabFilhoDescAdapter;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

import static lovera.kualpostinvou.views.utils.Utils_View.gerarLinhaParaTabela;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_centro;

public class FragFilho_Descricao extends FragmentFilho {

    public static String TITULO_FRAGMENT = "Filho Descricao";
    public static int ID_FRAGMENT = 0;
    public static int ICONE = R.drawable.icn_arquivo;

    private Estabelecimento estabelecimento;

    private List<Especialidade> listaEspecialidades;

    private Views views;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_descricao, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        consumirEspecialidades();

        this.views = new Views();
        this.views.setarEstabelecimento(this.estabelecimento, getView());
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    private void consumirEspecialidades(){
        if(this.listaEspecialidades != null){
            setListaEspecialidades(this.listaEspecialidades);
        }
        else{
            FragEstabFilhoDescAdapter adapter = new FragEstabFilhoDescAdapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getEspecialidades(this.estabelecimento.getCodUnidade());
        }
    }

    public void setListaEspecialidades(List<Especialidade> listaEspecialidades){
        this.listaEspecialidades = listaEspecialidades;
        try{
            fecharProgressoProfissionais();
            LinearLayout linhaUnica = (LinearLayout) getActivity().findViewById(R.id.f7_layoutEspecialidades);


            if(listaEspecialidades.size() > 0){
                for(Especialidade especialidade : listaEspecialidades){
                    LinearLayout linha = gerarLinhaParaTabela(getActivity());
                    linha.addView(gerarTxtViewParaTabela_centro(getActivity(), especialidade.getDescricaoHabilitacao() + " - " + especialidade.getDescricaoGrupo()));
                    linhaUnica.addView(linha);
                }
            }
            else{
                LinearLayout linha = gerarLinhaParaTabela(getActivity());
                linha.addView(gerarTxtViewParaTabela_centro(getActivity(), "Não há especialidades registradas"));
                linhaUnica.addView(linha);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void fecharProgressoProfissionais(){
        View progressoEspecialidades = getActivity().findViewById(R.id.f7_progressoEspecialidades);
        progressoEspecialidades.setVisibility(View.GONE);
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
