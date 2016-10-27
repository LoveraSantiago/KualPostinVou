package lovera.kualpostinvou.views.redes_sociais;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.components.helpers.ImgViewDrawerNavigComponent;
import lovera.kualpostinvou.views.contratos.MsgToViewHolderHeader;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.services.AppCivicoTokenService;

public class PessoaLogada{

    private String token;
    private boolean serviceTokenEmAndamento;

    private Pessoa pessoa;
    private Localizacao localizacao;

    private ImgViewDrawerNavigComponent imgViewComponent;
    private MsgToViewHolderHeader receptorMsg;

    public PessoaLogada() {
        this.imgViewComponent = new ImgViewDrawerNavigComponent();
        this.pessoa = new Pessoa();

        resetPessoa();
    }

    public void inicializarTokenAppCivico(Activity activit){
        if(hasToken()) return;

        this.serviceTokenEmAndamento = true;

        PrincipalActivity principalActivity = (PrincipalActivity) activit;
        principalActivity.abrirProgresso();
        principalActivity.setarTextoProgresso("Efetuando Login");

        Intent intent = new Intent(activit, AppCivicoTokenService.class);
        intent.putExtra(ReceiversNames.TOKENAPPCIVICO, principalActivity.getReceiverToken());
        activit.startService(intent);
    }

    public void resetPessoa(){
        this.pessoa.setNomeCompleto("Usuario Não Logado");
        this.pessoa.setIntImgPerfil(R.drawable.icn_pessoa_drawer);
        this.pessoa.setEmail("usuario@nao.logado");
        this.pessoa.setUriImgPerfil(null);

        setPessoa(this.pessoa);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        trocarHeaderDrawerNavigation();
    }

    private void trocarHeaderDrawerNavigation(){
        if(this.receptorMsg != null){
            this.receptorMsg.headerAlterado();
        }
    }

    public void getImgPessoa(ImageView imgView){
        if(isPessoaLogado()){
            this.imgViewComponent.setEspacoParaImg(imgView);
            ConexaoMetaModelo conexaoMetaModelo = new ConexaoMetaModelo(null);
            conexaoMetaModelo.downloadImageNaUrl(this.pessoa.getUriImgPerfil());
            this.imgViewComponent.configurarImgViewPessoaLogada(imgView);
        }
        else{
            this.imgViewComponent.configurarImgViewPessoaNLogada(imgView, this.pessoa);
        }
        imgView.invalidate();
    }


    public void passarBitmapImg(Bitmap bitmap) {
        this.imgViewComponent.passarBitmapImg(bitmap);
    }

    public void setReceptorMsg(MsgToViewHolderHeader receptorMsg) {
        this.receptorMsg = receptorMsg;
    }

    public boolean isPessoaLogado(){
        return !this.pessoa.getNomeCompleto().equals("Usuario Não Logado");
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean hasToken(){
        return (this.token == null || this.token.equals("")) ? false : true;
    }

    public String getToken() {
        return token;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public boolean isServiceTokenEmAndamento() {
        return serviceTokenEmAndamento;
    }

    public void setServiceTokenEmAndamento(boolean serviceTokenEmAndamento) {
        this.serviceTokenEmAndamento = serviceTokenEmAndamento;
    }
}

