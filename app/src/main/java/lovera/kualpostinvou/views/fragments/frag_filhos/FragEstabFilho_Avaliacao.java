package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import lovera.kualpostinvou.modelos.HoraMinuto;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.TipoObjeto;
import lovera.kualpostinvou.modelos.utils.Distancia;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.adapters.FragEstabFilhoAvAdapter;
import lovera.kualpostinvou.views.components.dialogs.AvTempoDialog;
import lovera.kualpostinvou.views.components.helpers.AvTempoComponents;
import lovera.kualpostinvou.views.components.helpers.FragEstabFilhoAvComponents;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.ServicesNames;

public class FragEstabFilho_Avaliacao extends FragmentFilho implements CommonsReceiver.Receiver{

    public static int RESULT_GRUPO_GET = 0;
    public static int RESULT_GRUPO_CAD = 1;


    public static String TITULO_FRAGMENT = "Filho Avaliacao";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.ic_grade_black_24dp;

    private HoraMinuto horaMinuto;
    private Estabelecimento estabelecimento;
    private GrupoR grupoTempoAtendimento;
    private Postagem postagem;
    private TipoObjeto tipoObjeto;

    private boolean jaCadastrouTempo;

    private AvTempoComponents tempoComponents;
    private FragEstabFilhoAvComponents components;

    private HelperGeolocalizacao helperGPS;

    private ConexaoMetaModelo conexaoModelo;

    private CommonsReceiver receiver;

    private AvTempoDialog dialogTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_avaliacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.tempoComponents = new AvTempoComponents(getActivity());
        this.components = new FragEstabFilhoAvComponents(this);
        this.helperGPS = Aplicacao.getHelperGps();

        inicializarConexao();
        inicializarReceivers();
        inicializarDialogAvTempo();
    }

    private void inicializarConexao(){
        FragEstabFilhoAvAdapter adapter = new FragEstabFilhoAvAdapter(this);
        this.conexaoModelo = new ConexaoMetaModelo(adapter);
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    private void inicializarDialogAvTempo() {
        this.dialogTimer = new AvTempoDialog(this);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    @Override
    public void onStart() {
        super.onStart();
        consumirAvTempoAtend();
    }

    private void consumirAvTempoAtend(){
        if(Aplicacao.getPessoaLogada().hasToken()){
            if(this.horaMinuto != null){
                this.tempoComponents.setTempo(this.horaMinuto);
            }
            else{
                this.grupoTempoAtendimento = FactoryModelos.geradorDeGrupo(this.estabelecimento.getCodUnidade());
                this.conexaoModelo.getGrupo(this.grupoTempoAtendimento, this.RESULT_GRUPO_GET);
            }
        }
        else{
            this.tempoComponents.realizarAcao(AvTempoComponents.NECESSARIO_LOGAR);
        }
    }

    public void consumirAvTempoAtend_receberGrupo(GrupoR grupo, int resultCode){
        this.grupoTempoAtendimento = grupo;
        if(resultCode == RESULT_GRUPO_GET){
            this.conexaoModelo.getPostagens(Aplicacao.getPessoaLogada().getToken(), this.grupoTempoAtendimento.getCodGrupo());
        }
        else{
            this.tempoComponents.realizarAcao(AvTempoComponents.SEM_DADOS_CADASTRADOS);
            TipoObjeto tipoObjeto = FactoryModelos.geradorTipoObjeto(grupo);
            this.conexaoModelo.cadastrarTipoObjeto(Aplicacao.getPessoaLogada().getToken(), tipoObjeto);
        }
    }

    public void passarPostagensParaMedia(PostagemR postagem, boolean usuarioPostou) {
        this.postagem = postagem;
        this.tipoObjeto = new TipoObjeto();
        this.tipoObjeto.setCodTipoObjeto(postagem.getCodObjetoDestino());
        this.jaCadastrouTempo = usuarioPostou;
        cadastrarTempoAtend_passarCodConteudoPostagem(null);
    }

    public void consumirAvTempoAtend_receberTipoDeObjeto(TipoObjeto tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public void passarMedia(Media media) {
        this.horaMinuto = FactoryModelos.geradorHoraMinuto((int) media.getMedia());
        this.tempoComponents.setTempo(this.horaMinuto);
        this.jaCadastrouTempo = true;
    }

    private void cadastrarGrupo(GrupoR grupo){
        this.conexaoModelo.cadastrarGrupo(Aplicacao.getPessoaLogada().getToken(), FactoryModelos.geradorDeGrupo(grupo), this.RESULT_GRUPO_CAD);
    }

    public void tratarErrorObjeto(ErrorObj error, int codigoErro){
        if(codigoErro == MsgFromConexaoModelo.COD_GRUPO_INEXISTENTE){
            cadastrarGrupo(this.grupoTempoAtendimento);
        }
    }

    public void cadastrarTempoAtend(){
        cadastrarTempoAtend_cadastrarPostagem();
    }

    private void cadastrarTempoAtend_cadastrarPostagem(){
        Postagem postagem = FactoryModelos.geradorDePostagem(this.grupoTempoAtendimento, this.tipoObjeto);
        conexaoModelo.cadastrarPostagem(Aplicacao.COD_APLICACAO , Aplicacao.getPessoaLogada().getToken(), postagem);
    }

    public void cadastrarTempoAtend_cadastrarConteudo(PostagemR postagem){
        this.postagem = postagem;

        Localizacao localizacaoAtualizada = this.helperGPS.getLocalizacaoAtualizada();
        this.helperGPS.desligarLocationUpdate();

        Distancia distancia = new Distancia();
        double distanciaLocal = distancia.calcularKmDistancia(localizacaoAtualizada, this.estabelecimento);
        if(distanciaLocal > 5){
            this.dialogTimer.dismiss();
            this.components.showDialogDistanteEstabelecimento();
        }
        else{
            int minutos = this.dialogTimer.getMinutos();
            this.dialogTimer.dismiss();
            if(minutos == 0){
                this.components.showDialogTempoZerado();
            }
            else{
                ConteudoPostagem conteudoPost = FactoryModelos.geradorConteudoPostagem(postagem, minutos);
                this.conexaoModelo.cadastrarConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagem.getCodPostagem(), conteudoPost);
            }
        }
    }

    public void cadastrarTempoAtend_passarCodConteudoPostagem(ConteudoPostagem conteudo) {
        if(conteudo != null){
            ((PostagemR) this.postagem).setConteudos(FactoryModelos.geradorDeListaDeConteudos(FactoryModelos.geradorDeConteudo(conteudo)));
        }
        this.conexaoModelo.getMedia(this.postagem.getTipo().getCodTipoPostagem(), this.postagem.getCodTipoObjetoDestino(), this.tipoObjeto.getCodTipoObjeto());
    }

    private boolean validarPermissoesCadastroAtendimento(){
        boolean temToken = Aplicacao.getPessoaLogada().hasToken();
        boolean temLocalizacao = this.helperGPS.temLastLocation();

        if(temToken && temLocalizacao){
            this.helperGPS.ligarLocationUpdate();
            return true;
        }
        else{
            if(Aplicacao.getPessoaLogada().isServiceTokenEmAndamento()){
                this.components.showDialogAguardeLogin();
                return false;
            }
            else{
                this.components.showDialogPermissoes(temToken, temLocalizacao);
                return false;
            }
        }
    }

    public void showDialogCadastrarTempoDeAtendimento(){
        if(validarPermissoesCadastroAtendimento()){
            this.dialogTimer.show(this.jaCadastrouTempo);
            if(this.jaCadastrouTempo){
                PostagemR postagemTemp = (PostagemR) this.postagem;
                this.conexaoModelo.getConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagemTemp.getCodPostagem(), postagemTemp.getConteudos().get(0).getCodConteudoPostagem());
            }
        }
    }

    public void passarConteudoPostagemParaDialogTimer(ConteudoPostagem conteudoPostagem){
        HoraMinuto horaMinuto = FactoryModelos.geradorHoraMinuto(conteudoPostagem.getValor());
        this.dialogTimer.setTempoCadastrado(horaMinuto);
    }

    public void ligarGps(){
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        principalActivity.abrirProgresso();
        principalActivity.setarTextoProgresso("Buscando localização.");

        this.helperGPS.popupLigarGps(this.receiver);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.GPS_SERVICE && resultData != null){
            ((PrincipalActivity) getActivity()).fecharProgresso();
        }
        else{
            this.components.showDialogGpsCancelado();
        }
    }
}
