package lovera.kualpostinvou.views.fragments.frag_filhos;

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

public class FragEstabFilho_Info extends FragmentFilho {

    private Estabelecimento estabelecimento;

    public static String TITULO_FRAGMENT = "Filho Info";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.icn_info;

    private List<Profissional> listaDeProfissionais;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_info, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    @Override
    public void onStart() {
        super.onStart();
        consumirProfissionais();
        setarCampos();
    }

    private void setarCampos(){
        try{
            setTextToLabel(this.estabelecimento.getCodCnes()                   , R.id.lblCodCnes     , getView());
            setTextToLabel(this.estabelecimento.getTipoUnidade()               , R.id.lblTipoUnidade , getView());
            setTextToLabel(this.estabelecimento.getCnpj()                      , R.id.lblCnpj        , getView());
            setTextToLabel(this.estabelecimento.getCodUnidade()                , R.id.lblCodigo      , getView());
            setTextToLabel(this.estabelecimento.getOrigemGeografica()          , R.id.lblOrigGeograf , getView());
            setTextToLabel(this.estabelecimento.getNatureza()                  , R.id.lblNatureza    , getView());
            setTextToLabel(this.estabelecimento.getEsferaAdministrativa()      , R.id.lblEsferaAdm   , getView());
            setTextToLabel(this.estabelecimento.getRetencao()                  , R.id.lblRetencao    , getView());
            setTextToLabel(this.estabelecimento.getTipoUnidadeCnes()           , R.id.lblUnidCnes    , getView());
            setTextToLabel(this.estabelecimento.getCategoriaUnidade()          , R.id.lblCategUnid   , getView());
            setTextToLabel(this.estabelecimento.getVinculoSus()                , R.id.lblVincSus     , getView());
            setTextToLabel(this.estabelecimento.getFluxoClientela()            , R.id.lblFlxClientela, getView());
        }catch (Exception e){}
    }

    private void consumirProfissionais(){
        if(this.listaDeProfissionais != null){
            setListaDeProfissionais(this.listaDeProfissionais);
        }
        else{
            FragEstabFilhoInfoAdapter adapter = new FragEstabFilhoInfoAdapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getProfissionais(this.estabelecimento.getCodUnidade());
        }
    }

    public void setListaDeProfissionais(List<Profissional> listaDeProfissionais){
        this.listaDeProfissionais = listaDeProfissionais;
        try{
            fecharProgressoProfissionais();
            LinearLayout linhaUnica = (LinearLayout) getActivity().findViewById(R.id.f6_layoutProfissionais);

            if(listaDeProfissionais.size() > 0){
                for(Profissional profissional : listaDeProfissionais){
                    LinearLayout linha = gerarLinhaParaTabela(getActivity());
                    linha.addView(gerarTxtViewParaTabela_esquerda(getActivity(), profissional.toString()));
                    linhaUnica.addView(linha);
                }
            }
            else{
                LinearLayout linha = gerarLinhaParaTabela(getActivity());
                linha.addView(gerarTxtViewParaTabela_centro(getActivity(), "Não há profissionais cadastrados."));
                linhaUnica.addView(linha);
            }
        }catch (Exception e){}
    }

    private void fecharProgressoProfissionais(){
        View progressoProfissionais = getActivity().findViewById(R.id.f6_progressoProfissionais);
        progressoProfissionais.setVisibility(View.GONE);
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
