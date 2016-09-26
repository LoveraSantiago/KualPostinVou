package lovera.kualpostinvou.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

public class FragEstabelecimento extends FragmentMenu{

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimento";
    public static int ID_FRAGMENT = 5;
    public static int ICONE = R.drawable.icn2;

    private Estabelecimento estabelecimento;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estabelecimento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setarCampos();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    private void setarCampos(){
        setTextToLabel(estabelecimento.getCodCnes(), R.id.lblCodCnes, getView());
        setTextToLabel(estabelecimento.getCnpj(), R.id.lblCnpj, getView());
        setTextToLabel(estabelecimento.getCodUnidade(), R.id.lblCodigo, getView());
        setTextToLabel(estabelecimento.getNomeFantasia(), R.id.lblNomeFantasia, getView());
        setTextToLabel(estabelecimento.getNatureza(), R.id.lblNatureza, getView());
        setTextToLabel(estabelecimento.getTipoUnidade(), R.id.lblTipoUnidade, getView());
        setTextToLabel(estabelecimento.getEsferaAdministrativa(), R.id.lblEsferaAdm, getView());
        setTextToLabel(estabelecimento.getVinculoSus(), R.id.lblVincSus, getView());
        setTextToLabel(estabelecimento.getRetencao(), R.id.lblRetencao, getView());
        setTextToLabel(estabelecimento.getFluxoClientela(), R.id.lblFlxClientela, getView());
        setTextToLabel(estabelecimento.getOrigemGeografica(), R.id.lblOrigGeograf, getView());
        setTextToLabel(estabelecimento.getTemAtendimentoUrgencia(), R.id.lblAtendEmgc, getView());
        setTextToLabel(estabelecimento.getTemAtendimentoAmbulatorial(), R.id.lblAtendAmbulat, getView());
        setTextToLabel(estabelecimento.getTemCentroCirurgico(), R.id.lblCCirurg, getView());
        setTextToLabel(estabelecimento.getTemObstetra(), R.id.lblObstetra, getView());
        setTextToLabel(estabelecimento.getTemNeoNatal(), R.id.lblNeonatal, getView());
        setTextToLabel(estabelecimento.getTemDialise(), R.id.lblDialise, getView());
        setTextToLabel(estabelecimento.getDescricaoCompleta(), R.id.lblDescompl, getView());
        setTextToLabel(estabelecimento.getTipoUnidadeCnes(), R.id.lblCategUnid, getView());
        setTextToLabel(estabelecimento.getLogradouro(), R.id.lblLogradouro, getView());
        setTextToLabel(estabelecimento.getNumero(), R.id.lblNumero, getView());
        setTextToLabel(estabelecimento.getBairro(), R.id.lblBairro, getView());
        setTextToLabel(estabelecimento.getCidade(), R.id.lblCidade, getView());
        setTextToLabel(estabelecimento.getUf(), R.id.lblUf, getView());
        setTextToLabel(estabelecimento.getCep(), R.id.lblCep, getView());
        setTextToLabel(estabelecimento.getTelefone(), R.id.lblTelefone, getView());
        setTextToLabel(estabelecimento.getTurnoAtendimento(), R.id.lblTurnoAtend, getView());
        setTextToLabel(estabelecimento.getLat(), R.id.lblLatitude, getView());
        setTextToLabel(estabelecimento.getLongi(), R.id.lblLongitude, getView());
        setTextToLabel(estabelecimento.getDistancia(), R.id.lblDistancia, getView());
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
