package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao;

import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils_View.gerarLinhaParaTabela;
import static lovera.kualpostinvou.views.utils.Utils_View.gerarTxtViewParaTabela_centro;
import static lovera.kualpostinvou.views.utils.Utils_View.setImageToImgView;
import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

class Views {

    private final FragFilho_Descricao fragment;

    public Views(FragFilho_Descricao fragment) {
        this.fragment = fragment;
    }

    public void setarEstabelecimentoParaLabels(Estabelecimento estabelecimento){
        try{
            setTextToLabel(estabelecimento.getNomeFantasia()              , R.id.lblNomeFantasia, this.fragment.getView());
            setTextToLabel(estabelecimento.getDescricaoCompleta()         , R.id.lblDescompl    , this.fragment.getView());
            setTextToLabel(estabelecimento.getTurnoAtendimento()          , R.id.lblTurnoAtend  , this.fragment.getView());

            setImageToImgView(setImage(estabelecimento.getTemDialise())                , R.id.imgDialise     , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemObstetra())               , R.id.imgObstetra    , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemCentroCirurgico())        , R.id.imgCCirurg     , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemAtendimentoUrgencia())    , R.id.imgAtendEmgc   , this.fragment.getView());
            setImageToImgView(setImage(estabelecimento.getTemAtendimentoAmbulatorial()), R.id.imgAtendAmbulat, this.fragment.getView());
        }
        catch(Exception e){
//            e.printStackTrace();
        }
    }

    private int setImage(String campo){
        return campo.equals("Sim") ? R.drawable.icn_check : R.drawable.icn_cancelar;
    }

    public void setListaEspecialidadesParaTabela(List<Especialidade> listaEspecialidades){
        try{
            fecharProgressoProfissionais();
            LinearLayout linhaUnica = (LinearLayout) this.fragment.getActivity().findViewById(R.id.f7_layoutEspecialidades);

            if(listaEspecialidades.size() > 0){
                for(Especialidade especialidade : listaEspecialidades){
                    LinearLayout linha = gerarLinhaParaTabela(this.fragment.getActivity());
                    linha.addView(gerarTxtViewParaTabela_centro(this.fragment.getActivity(), especialidade.getDescricaoHabilitacao() + " - " + especialidade.getDescricaoGrupo()));
                    linhaUnica.addView(linha);
                }
            }
            else{
                LinearLayout linha = gerarLinhaParaTabela(this.fragment.getActivity());
                linha.addView(gerarTxtViewParaTabela_centro(this.fragment.getActivity(), "Não há especialidades registradas"));
                linhaUnica.addView(linha);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void fecharProgressoProfissionais(){
        View progressoEspecialidades = this.fragment.getActivity().findViewById(R.id.f7_progressoEspecialidades);
        progressoEspecialidades.setVisibility(View.GONE);
    }
}
