package lovera.kualpostinvou.views.controllers;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.FragEstabelecimento;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Avaliacao;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Desc;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Endereco;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Info;
import lovera.kualpostinvou.views.services.ServicesNames;

public class FragEstabelecimentoController {

    private LatLng latLng;

    private Estabelecimento estabelecimento;

    private final FragEstabelecimento fragment;

    private FragEstabFilho_Desc fragFilhoDescricao;
    private FragEstabFilho_Info fragFilhoInfo;
    private FragEstabFilho_Avaliacao fragFilhoAvaliacao;
    private FragEstabFilho_Endereco fragFilhoEndereco;

    private MsgToActivity msgToActivity;

    public FragEstabelecimentoController(FragEstabelecimento fragment) {
        this.fragment = fragment;
        this.msgToActivity = (MsgToActivity) fragment.getActivity();

        inicializarFragFilhos();
    }

    private void inicializarFragFilhos(){
        this.fragFilhoDescricao = new FragEstabFilho_Desc();
        this.fragFilhoInfo      = new FragEstabFilho_Info();
        this.fragFilhoAvaliacao = new FragEstabFilho_Avaliacao();
        this.fragFilhoEndereco  = new FragEstabFilho_Endereco();
    }

    public void onActivityCreated(Bundle savedInstanceState){
        this.msgToActivity = (MsgToActivity) this.fragment.getActivity();
        this.msgToActivity.setarTextoProgresso("Localizando fotos do estabelecimento");
    }

    public void onReceiveResult(int resultCode, Bundle resultData){
        if(resultCode == ServicesNames.NOME_GEOLOCALIZACAO){
            Localizacao localizacao = getLocalizacaoDoOnReceiveResult(resultData);
            this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
            this.fragment.getComponents().setarPosicaoToPanorama(this.latLng);
            this.msgToActivity.fecharProgresso();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        Localizacao localizacao = FactoryModelos.geradorLocalizacao(this.latLng);
        outState.putSerializable("LOCALIZACAO", localizacao);
    }

    public void recuperarObjetosSalvos(Bundle bundle){
        if(bundle != null){
            Localizacao localizacao = (Localizacao) bundle.getSerializable("LOCALIZACAO");
            this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
            this.fragment.getComponents().setarPosicaoToPanorama(this.latLng);
            this.msgToActivity.fecharProgresso();
        }
    }

    private Localizacao getLocalizacaoDoOnReceiveResult(Bundle bundle){
        Localizacao result = (Localizacao) bundle.getSerializable("LOCALIZACAO");

        if(result == null){
            result = FactoryModelos.geradorLocalizacao(this.estabelecimento);
        }
        return result;
    }

    public void setArguments(Bundle args){
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");

        this.fragFilhoDescricao.setArguments(args);
        this.fragFilhoInfo.setArguments(args);
        this.fragFilhoEndereco.setArguments(args);
    }

    public FragmentFilho[] getFragFilhos(){
        return new FragmentFilho[]{this.fragFilhoDescricao, this.fragFilhoInfo, this.fragFilhoAvaliacao, this.fragFilhoEndereco};
    }

    //Metodos do fragFilhoAvaliacao
    public void showDialogCadastrarTempoDeAtendimento(){
        this.fragFilhoAvaliacao.showDialogCadastrarTempoDeAtendimento();
    }
}
