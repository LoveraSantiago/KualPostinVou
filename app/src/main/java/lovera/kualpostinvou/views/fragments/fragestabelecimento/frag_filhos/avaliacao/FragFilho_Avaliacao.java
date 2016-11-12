package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.avaliacao;

import android.os.Bundle;
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
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;

public class FragFilho_Avaliacao extends FragmentFilho{

    public static int RESULT_GRUPO_GET = 0;
    public static int RESULT_GRUPO_CAD = 1;

    public static String TITULO_FRAGMENT = "Filho Avaliacao";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.icn_estrela;

    private HoraMinuto horaMinuto;
    private Estabelecimento estabelecimento;
    private GrupoR grupoTempoAtendimento;
    private Postagem postagem;
    private TipoObjeto tipoObjeto;

    private boolean jaCadastrouTempo;

    private HelperGeolocalizacao helperGPS;

    private ConexaoMetaModelo conexaoModelo;

    private Controller controller;
    private Views views;
    private Receiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_avaliacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarComponentes();

        inicializarConexao();
    }

    private void inicializarComponentes(){
        this.receiver = new Receiver(this);
        this.views = new Views(this);
        this.controller = new Controller();
        this.helperGPS = Aplicacao.getHelperGps();
    }

    private void inicializarConexao(){
        FragEstabFilhoAvAdapter adapter = new FragEstabFilhoAvAdapter(this);
        this.conexaoModelo = new ConexaoMetaModelo(adapter);
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
                this.views.getTempoComponent().setTempo(this.horaMinuto);
            }
            else{
                this.grupoTempoAtendimento = FactoryModelos.geradorDeGrupo(this.estabelecimento.getCodUnidade());
                this.conexaoModelo.getGrupo(this.grupoTempoAtendimento, this.RESULT_GRUPO_GET);
            }
        }
        else{
            this.views.getTempoComponent().realizarAcao(AvTempoComponent.NECESSARIO_LOGAR);
        }
    }

    public void consumirAvTempoAtend_receberGrupo(GrupoR grupo, int resultCode){
        this.grupoTempoAtendimento = grupo;
        if(resultCode == RESULT_GRUPO_GET){
            this.conexaoModelo.getPostagens(Aplicacao.getPessoaLogada().getToken(), this.grupoTempoAtendimento.getCodGrupo());
        }
        else{
            this.views.getTempoComponent().realizarAcao(AvTempoComponent.SEM_DADOS_CADASTRADOS);
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
        this.views.getTempoComponent().setTempo(this.horaMinuto);
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

    public void editarTempoAtend(){
        int minutos = this.views.getAvTempoDialog().getMinutos();
        this.views.getAvTempoDialog().dismiss();
        if(minutos == 0){
            this.views.getDialogs().showDialogTempoZerado();
        }
        else{
            PostagemR postagemRTemp = (PostagemR) this.postagem;
            ConteudoPostagem conteudoPost = FactoryModelos.geradorConteudoPostagem(postagemRTemp, minutos);
            this.conexaoModelo.editConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagemRTemp.getCodPostagem(), postagemRTemp.getConteudos().get(0).getCodConteudoPostagem(), conteudoPost);
        }
    }

    private void cadastrarTempoAtend_cadastrarPostagem(){
        Postagem postagem = FactoryModelos.geradorDePostagem(this.grupoTempoAtendimento, this.tipoObjeto);
        conexaoModelo.cadastrarPostagem(Aplicacao.COD_APLICACAO , Aplicacao.getPessoaLogada().getToken(), postagem);
    }

    public void cadastrarTempoAtend_cadastrarConteudo(PostagemR postagem){
        this.postagem = postagem;

        int minutos = this.views.getAvTempoDialog().getMinutos();
        this.views.getAvTempoDialog().dismiss();
        if(minutos == 0){
            this.views.getDialogs().showDialogTempoZerado();
        }
        else{
            ConteudoPostagem conteudoPost = FactoryModelos.geradorConteudoPostagem(postagem, minutos);
            this.conexaoModelo.cadastrarConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagem.getCodPostagem(), conteudoPost);
        }
    }

    public void cadastrarTempoAtend_passarCodConteudoPostagem(ConteudoPostagem conteudo) {
        if(conteudo != null){
            ((PostagemR) this.postagem).setConteudos(FactoryModelos.geradorDeListaDeConteudos(FactoryModelos.geradorDeConteudo(conteudo)));
        }
        this.conexaoModelo.getMedia(this.postagem.getTipo().getCodTipoPostagem(), this.postagem.getCodTipoObjetoDestino(), this.tipoObjeto.getCodTipoObjeto());
    }

    public void passarConteudoPostagemParaDialogTimer(ConteudoPostagem conteudoPostagem){
        HoraMinuto horaMinuto = FactoryModelos.geradorHoraMinuto(conteudoPostagem.getValor());
        this.views.getAvTempoDialog().setTempoCadastrado(horaMinuto);
    }

    public void ligarGps(){
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        principalActivity.abrirProgresso();
        principalActivity.setarTextoProgresso("Buscando localização.");

        this.helperGPS.popupLigarGps(this.receiver.getCommonsReceiver());
    }

    public Controller getController() {
        return controller;
    }

    public Views getViews() {
        return views;
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
}
