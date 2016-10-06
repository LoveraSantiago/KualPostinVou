package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.utils.Distancia;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.adapters.FragEstabFilhoAvAdapter;
import lovera.kualpostinvou.views.components.dialogs.AvAtendPermissoesDialog;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.components.dialogs.AvTempoDialog;
import lovera.kualpostinvou.views.controllers.AvTempoController;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.ServicesNames;
import lovera.kualpostinvou.views.utils.FactoryViews;

import static lovera.kualpostinvou.views.utils.FactoryViews.factoryDismissDialog;

public class FragEstabFilho_Avaliacao extends FragmentFilho implements CommonsReceiver.Receiver{

    public static String TITULO_FRAGMENT = "Filho Avaliacao";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.ic_grade_black_24dp;

    private Estabelecimento estabelecimento;
    private Grupo grupoTempoAtendimento;

    private AvTempoController tempoController;

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
        this.tempoController = new AvTempoController(getActivity());
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
    public void onStart() {
        super.onStart();
        consumirAvTempoAtend();
    }

    private void consumirAvTempoAtend(){
        if(Aplicacao.getPessoaLogada().hasToken()){
            if(algumacoisapostagem != null){

            }
            else{
                this.grupoTempoAtendimento = FactoryModelos.geradorDeGrupo(this.estabelecimento.getCodUnidade());
                this.conexaoModelo.getGrupo(this.grupoTempoAtendimento);
            }
        }
        else{
            this.tempoController.realizarAcao(AvTempoController.NECESSARIO_LOGAR);
        }
    }

    public void consumirAvTempoAtend_receberGrupo(Grupo grupo){

    }

    private void cadastrarGrupo(Grupo grupo){
        this.conexaoModelo.cadastrarGrupo(Aplicacao.getPessoaLogada().getToken(), grupo);
    }

    public void tratarErrorObjeto(ErrorObj error, int codigoErro){
        if(codigoErro == MsgFromConexaoModelo.COD_GRUPO_INEXISTENTE){
            cadastrarGrupo(this.grupoTempoAtendimento);
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    public void showDialogCadastrarTempoDeAtendimento(){
        if(validarPermissoesCadastroAtendimento()){
            dialogTimer.show();
        }
    }

    public void cadastrarTempoAtend(){
        cadastrarTempoAtend_cadastrarPostagem();
    }

    private void cadastrarTempoAtend_cadastrarPostagem(){
        Postagem postagem = FactoryModelos.geradorDePostagem(this.grupoTempoAtendimento);
        conexaoModelo.cadastrarPostagem(Aplicacao.COD_APLICACAO , Aplicacao.getPessoaLogada().getToken(), postagem);
    }

    public void cadastrarTempoAtend_cadastrarConteudo(Postagem postagem){

        Localizacao localizacaoAtualizada = this.helperGPS.getLocalizacaoAtualizada();
        this.helperGPS.desligarLocationUpdate();

        Distancia distancia = new Distancia();
        double distanciaLocal = distancia.calcularKmDistancia(localizacaoAtualizada, this.estabelecimento);
        if(distanciaLocal > 5){
            showDialogDistanteEstabelecimento();
        }
        else{
            int minutos = this.dialogTimer.getMinutos();
            if(minutos == 0){
                showDialogTempoZerado();
            }
            else{
                ConteudoPostagem conteudoPost = FactoryModelos.gerarConteudoPostagem(postagem, minutos);
                this.conexaoModelo.cadastrarConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagem.getCodPostagem(), conteudoPost);
            }
        }
    }

    public void passarConteudoPostagem(ConteudoPostagem conteudo) {
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
                showDialogAguardeLogin();
                return false;
            }
            else{
                showDialogPermissoes(temToken, temLocalizacao);
                return false;
            }
        }
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
            showDialogGpsCancelado();
        }
    }

    private void showDialogGpsCancelado(){
        DismissDialog dialog = factoryDismissDialog(getActivity(), "Localização Requerida",
                "Para realizar a avaliação de tempo do estabelecimento é necessario autorizar o gps");
        dialog.show();
    }

    private void showDialogAguardeLogin(){
        DismissDialog dialog = factoryDismissDialog(getActivity(), "Login em Andamento",
                "Aguarde um instante, login em andamento");
        dialog.show();
    }

    private void showDialogDistanteEstabelecimento(){
        DismissDialog dialog = factoryDismissDialog(getActivity(),"Distante do Estabelecimento",
                "Só é permitido registrar avaliação sobre um estabelecimento estando proximo dele.");
        dialog.show();
    }

    private void showDialogTempoZerado(){
        DismissDialog dialog = factoryDismissDialog(getActivity(),"Tempo inválido",
                "O registro de tempo deve ser diferente de zero.");
        dialog.show();
    }

    private void showDialogPermissoes(boolean temToken, boolean temLocalizacao){
        AvAtendPermissoesDialog dialogAtend = new AvAtendPermissoesDialog(this);
        if(!temToken){
            dialogAtend.configurarLinhaLogado(true, false);
        }
        else{
            dialogAtend.configurarLinhaLogado(true, true);
        }
        if(!temLocalizacao){
            dialogAtend.configurarLinhaGps(true, false);
        }
        else{
            dialogAtend.configurarLinhaGps(true, true);
        }
        dialogAtend.show();
    }

    public void realizarAcao(int codigo){
        this.tempoController.realizarAcao(codigo);
    }
}
