package lovera.kualpostinvou.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

public class FragEstabelecimento_Filho1 extends Fragment {

    private Estabelecimento estabelecimento;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_1, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setarCampos();
    }

    private void setarCampos(){
        setTextToLabel(this.estabelecimento.getCodCnes()                   , R.id.lblCodCnes     , getView());
        setTextToLabel(this.estabelecimento.getCnpj()                      , R.id.lblCnpj        , getView());
        setTextToLabel(this.estabelecimento.getCodUnidade()                , R.id.lblCodigo      , getView());
        setTextToLabel(this.estabelecimento.getNomeFantasia()              , R.id.lblNomeFantasia, getView());
        setTextToLabel(this.estabelecimento.getNatureza()                  , R.id.lblNatureza    , getView());
        setTextToLabel(this.estabelecimento.getTipoUnidade()               , R.id.lblTipoUnidade , getView());
        setTextToLabel(this.estabelecimento.getEsferaAdministrativa()      , R.id.lblEsferaAdm   , getView());
        setTextToLabel(this.estabelecimento.getVinculoSus()                , R.id.lblVincSus     , getView());
        setTextToLabel(this.estabelecimento.getRetencao()                  , R.id.lblRetencao    , getView());
        setTextToLabel(this.estabelecimento.getFluxoClientela()            , R.id.lblFlxClientela, getView());
        setTextToLabel(this.estabelecimento.getOrigemGeografica()          , R.id.lblOrigGeograf , getView());
        setTextToLabel(this.estabelecimento.getTemAtendimentoUrgencia()    , R.id.lblAtendEmgc   , getView());
        setTextToLabel(this.estabelecimento.getTemAtendimentoAmbulatorial(), R.id.lblAtendAmbulat, getView());
        setTextToLabel(this.estabelecimento.getTemCentroCirurgico()        , R.id.lblCCirurg     , getView());
        setTextToLabel(this.estabelecimento.getTemObstetra()               , R.id.lblObstetra    , getView());
        setTextToLabel(this.estabelecimento.getTemNeoNatal()               , R.id.lblNeonatal    , getView());
        setTextToLabel(this.estabelecimento.getTemDialise()                , R.id.lblDialise     , getView());
        setTextToLabel(this.estabelecimento.getDescricaoCompleta()         , R.id.lblDescompl    , getView());
        setTextToLabel(this.estabelecimento.getTipoUnidadeCnes()           , R.id.lblCategUnid   , getView());
        setTextToLabel(this.estabelecimento.getTurnoAtendimento()          , R.id.lblTurnoAtend  , getView());
    }
}
