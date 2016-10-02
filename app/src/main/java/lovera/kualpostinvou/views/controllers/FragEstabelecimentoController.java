package lovera.kualpostinvou.views.controllers;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.adapters.FragEstabAdapter;
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
    private List<Profissional> listaDeProfissionais;
    private List<Especialidade> listaDeEspecialidades;

    private final FragEstabelecimento fragment;

    private FragEstabFilho_Desc fragFilhoDescricao;
    private FragEstabFilho_Info fragFilhoInfo;
    private FragEstabFilho_Avaliacao fragFilhoAvaliacao;
    private FragEstabFilho_Endereco fragFilhoEndereco;

    private ConexaoSaude conexaoSaude;

    private MsgToActivity msgToActivity;

    public FragEstabelecimentoController(FragEstabelecimento fragment) {
        this.fragment = fragment;
        this.msgToActivity = (MsgToActivity) fragment.getActivity();

        inicializarFragFilhos();
        inicializarConexao();
    }

    private void inicializarConexao(){
        FragEstabAdapter adapter = new FragEstabAdapter(this.fragment);
        this.conexaoSaude = new ConexaoSaude(adapter);
    }

    private void inicializarFragFilhos(){
        this.fragFilhoDescricao = new FragEstabFilho_Desc();
        this.fragFilhoDescricao.setMsg(this.fragment);
        this.fragFilhoInfo = new FragEstabFilho_Info();
        this.fragFilhoInfo.setMsg(this.fragment);
        this.fragFilhoAvaliacao = new FragEstabFilho_Avaliacao();
        this.fragFilhoAvaliacao.setMsg(this.fragment);
        this.fragFilhoEndereco = new FragEstabFilho_Endereco();
    }

    public void onActivityCreated(Bundle savedInstanceState){
        this.msgToActivity = (MsgToActivity) this.fragment.getActivity();
        inicializarRestConsumers();
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
        Localizacao localizacao = new Localizacao();
        localizacao.setLatitude(this.latLng.latitude);
        localizacao.setLongitude(this.latLng.longitude);
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
            result = new Localizacao();
            result.setLatitude(this.estabelecimento.getLat());
            result.setLongitude(this.estabelecimento.getLongi());
        }
        return result;
    }

    private void inicializarRestConsumers(){
        consumirProfissionais();
        consumirEspecialidades();
    }

    private void consumirProfissionais(){
        if(this.listaDeProfissionais == null){
            this.conexaoSaude.getProfissionais(this.estabelecimento.getCodUnidade());
        }
    }

    private void consumirEspecialidades(){
        if(this.listaDeEspecialidades == null){
            this.conexaoSaude.getEspecialidades(this.estabelecimento.getCodUnidade());
        }
    }

    public List<Profissional> getListaDeProfissionais() {
        return listaDeProfissionais;
    }

    public void setListaDeProfissionais(List<Profissional> listaDeProfissionais) {
        this.listaDeProfissionais = listaDeProfissionais;
        this.fragFilhoInfo.setListaDeProfissionais(listaDeProfissionais);
    }

    public List<Especialidade> getListaDeEspecialidades() {
        return listaDeEspecialidades;
    }

    public void setListaDeEspecialidades(List<Especialidade> listaDeEspecialidades) {
        this.listaDeEspecialidades = listaDeEspecialidades;
        this.fragFilhoDescricao.setListaEspecialidades(listaDeEspecialidades);
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
    public void cadastrarTempoDeAtendimento(){
        this.fragFilhoAvaliacao.cadastrarTempoDeAtendimento();
    }
}
