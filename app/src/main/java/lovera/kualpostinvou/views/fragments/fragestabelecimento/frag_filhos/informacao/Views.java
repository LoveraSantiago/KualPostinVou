package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao;

import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao.FragFilho_Descricao;

import static lovera.kualpostinvou.views.utils.Utils_View.gerarLinhaParaTabela;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_centro;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_esquerda;
import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

class Views {

    private final FragFilho_Informacao fragment;

    public Views(FragFilho_Informacao fragment) {
        this.fragment = fragment;
    }

    public void setarEstabelecimentoParaLabels(Estabelecimento estabelecimento){
        try{
            setTextToLabel(estabelecimento.getCodCnes()                   , R.id.lblCodCnes     , this.fragment.getView());
            setTextToLabel(estabelecimento.getTipoUnidade()               , R.id.lblTipoUnidade , this.fragment.getView());
            setTextToLabel(estabelecimento.getCnpj()                      , R.id.lblCnpj        , this.fragment.getView());
            setTextToLabel(estabelecimento.getCodUnidade()                , R.id.lblCodigo      , this.fragment.getView());
            setTextToLabel(estabelecimento.getOrigemGeografica()          , R.id.lblOrigGeograf , this.fragment.getView());
            setTextToLabel(estabelecimento.getNatureza()                  , R.id.lblNatureza    , this.fragment.getView());
            setTextToLabel(estabelecimento.getEsferaAdministrativa()      , R.id.lblEsferaAdm   , this.fragment.getView());
            setTextToLabel(estabelecimento.getRetencao()                  , R.id.lblRetencao    , this.fragment.getView());
            setTextToLabel(estabelecimento.getTipoUnidadeCnes()           , R.id.lblUnidCnes    , this.fragment.getView());
            setTextToLabel(estabelecimento.getCategoriaUnidade()          , R.id.lblCategUnid   , this.fragment.getView());
            setTextToLabel(estabelecimento.getVinculoSus()                , R.id.lblVincSus     , this.fragment.getView());
            setTextToLabel(estabelecimento.getFluxoClientela()            , R.id.lblFlxClientela, this.fragment.getView());
        }catch (Exception e){}
    }

    public void setListaDeProfissionaisParaTabela(List<Profissional> listaDeProfissionais){
        try{
            fecharProgressoProfissionais();
            LinearLayout linhaUnica = (LinearLayout) this.fragment.getActivity().findViewById(R.id.f6_layoutProfissionais);

            if(listaDeProfissionais.size() > 0){
                for(Profissional profissional : listaDeProfissionais){
                    LinearLayout linha = gerarLinhaParaTabela(this.fragment.getActivity());
                    linha.addView(gerarTxtViewParaTabela_esquerda(this.fragment.getActivity(), profissional.toString()));
                    linhaUnica.addView(linha);
                }
            }
            else{
                LinearLayout linha = gerarLinhaParaTabela(this.fragment.getActivity());
                linha.addView(gerarTxtViewParaTabela_centro(this.fragment.getActivity(), "Não há profissionais cadastrados."));
                linhaUnica.addView(linha);
            }
        }catch (Exception e){}
    }

    private void fecharProgressoProfissionais(){
        View progressoProfissionais = this.fragment.getActivity().findViewById(R.id.f6_progressoProfissionais);
        progressoProfissionais.setVisibility(View.GONE);
    }
}
