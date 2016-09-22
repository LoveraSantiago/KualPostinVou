package lovera.kualpostinvou.views.redes_sociais;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Arrays;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoPessoa;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.contratos.MsgToViewHolderHeader;

public class PessoaLogada{

    private String token;

    private Pessoa pessoa;
    private ImageView espacoParaImg;

    private MsgToViewHolderHeader receptorMsg;

    public PessoaLogada() {
        this.pessoa = new Pessoa();
        resetPessoa();
    }

    public void inicializarPessoa(){
        resetPessoa();

        Aplicacao.getGoogleCoisas().getPessoaLogada();
        Aplicacao.getFaceCoisas().getPessoaLogada();

        factoryTestePessoa();

        if(this.receptorMsg != null){
            this.receptorMsg.headerAlterado();
        }
    }

    public void factoryTestePessoa(){
        String numero = "1";
        pessoa.setCEP("05172-030");
        pessoa.setBiografia("biografiatemp");
        pessoa.setDataNascimento("1981-06-25");
        pessoa.setEmail("santuga@gmail" + numero);
        pessoa.setLatitude(20.20);
        pessoa.setLongitude(19.19);
        pessoa.setNomeCompleto("Santuga Lovera" + numero);
        pessoa.setNomeUsuario("Santuga" + numero);
        pessoa.setSexo("M");
        pessoa.setTokenFacebook("IDFACETESTE" + numero);
        pessoa.setTokenGoogle("IDGOOGLETESTE" + numero);
    }

    public void resetPessoa(){
        this.pessoa.setNomeCompleto("Não Logado");
        this.pessoa.setIntImgPerfil(R.drawable.icon_people_128);
        this.pessoa.setEmail("temp@temp");
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void getImgPessoa(ImageView imgView){
        if(isPessoaLogado()){
            imgView.setImageResource(this.pessoa.getIntImgPerfil());
            imgView.invalidate();
        }
        else{
            this.espacoParaImg = imgView;
            ConexaoPessoa conexaoPessoa = new ConexaoPessoa();
            conexaoPessoa.downloadImageNaUrl(this.pessoa.getUriImgPerfil());
        }
    }

    public void passarBitmapImg(Bitmap bitmap) {
        this.espacoParaImg.setImageBitmap(bitmap);
        this.espacoParaImg.invalidate();
    }

    public void setReceptorMsg(MsgToViewHolderHeader receptorMsg) {
        this.receptorMsg = receptorMsg;
    }

    public boolean isPessoaLogado(){
        return !this.pessoa.getNomeCompleto().equals("Não Logado");
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
}

