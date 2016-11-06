package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao;

import android.view.View;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils_View.setImageToImgView;
import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

class Views {

    public Views() {
    }

    public void setarEstabelecimento(Estabelecimento estabelecimento, View view){
        try{
            setTextToLabel(estabelecimento.getNomeFantasia()              , R.id.lblNomeFantasia, view);
            setTextToLabel(estabelecimento.getDescricaoCompleta()         , R.id.lblDescompl    , view);
            setTextToLabel(estabelecimento.getTurnoAtendimento()          , R.id.lblTurnoAtend  , view);

            setImageToImgView(setImage(estabelecimento.getTemDialise())                , R.id.imgDialise     , view);
            setImageToImgView(setImage(estabelecimento.getTemObstetra())               , R.id.imgObstetra    , view);
            setImageToImgView(setImage(estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , view);
            setImageToImgView(setImage(estabelecimento.getTemCentroCirurgico())        , R.id.imgCCirurg     , view);
            setImageToImgView(setImage(estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , view);
            setImageToImgView(setImage(estabelecimento.getTemAtendimentoUrgencia())    , R.id.imgAtendEmgc   , view);
            setImageToImgView(setImage(estabelecimento.getTemAtendimentoAmbulatorial()), R.id.imgAtendAmbulat, view);
        }
        catch(Exception e){
//            e.printStackTrace();
        }
    }

    private int setImage(String campo){
        return campo.equals("Sim") ? R.drawable.icn_check : R.drawable.icn_cancelar;
    }
}
