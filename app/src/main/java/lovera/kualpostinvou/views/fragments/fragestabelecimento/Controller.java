package lovera.kualpostinvou.views.fragments.fragestabelecimento;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.FragEstabFilho_Avaliacao;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao.FragFilho_Descricao;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.FragEstabFilho_Endereco;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.FragEstabFilho_Info;

class Controller {

    private LatLng latLng;

//    private Estabelecimento estabelecimento;

    private final FragEstabelecimento fragment;

    private FragFilho_Descricao fragFilhoDescricao;
    private FragEstabFilho_Info fragFilhoInfo;
    private FragEstabFilho_Avaliacao fragFilhoAvaliacao;
    private FragEstabFilho_Endereco fragFilhoEndereco;

    private MsgToActivity msgToActivity;

    public Controller(FragEstabelecimento fragment) {
        this.fragment = fragment;
        this.msgToActivity = (MsgToActivity) fragment.getActivity();

        inicializarFragFilhos();
    }

    private void inicializarFragFilhos(){
        this.fragFilhoDescricao = new FragFilho_Descricao();
        this.fragFilhoInfo      = new FragEstabFilho_Info();
        this.fragFilhoAvaliacao = new FragEstabFilho_Avaliacao();
        this.fragFilhoEndereco  = new FragEstabFilho_Endereco();
    }

    public void onActivityCreated(Bundle savedInstanceState){
        this.msgToActivity = (MsgToActivity) this.fragment.getActivity();
        this.msgToActivity.setarTextoProgresso("Localizando fotos do estabelecimento");
    }

    public void onSaveInstanceState(Bundle outState) {
        Localizacao localizacao = FactoryModelos.geradorLocalizacao(this.latLng);
        outState.putSerializable("LOCALIZACAO", localizacao);
    }

    public void recuperarObjetosSalvos(Bundle bundle){
        if(bundle != null){
            Localizacao localizacao = (Localizacao) bundle.getSerializable("LOCALIZACAO");
            setLocalizacao(localizacao);
        }
    }

    public void setArguments(Bundle args){
//        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");

        this.fragFilhoDescricao.setArguments(args);
        this.fragFilhoInfo.setArguments(args);
        this.fragFilhoEndereco.setArguments(args);
        this.fragFilhoAvaliacao.setArguments(args);
    }

    public void setLocalizacao(Localizacao localizacao){
        this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
        this.fragment.getViews().setarPosicaoToPanorama(this.latLng);
        this.msgToActivity.fecharProgresso();
    }

    public FragmentFilho[] getFragFilhos(){
        return new FragmentFilho[]{this.fragFilhoDescricao, this.fragFilhoInfo, this.fragFilhoAvaliacao, this.fragFilhoEndereco};
    }
}
