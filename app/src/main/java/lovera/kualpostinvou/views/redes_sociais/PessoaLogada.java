package lovera.kualpostinvou.views.redes_sociais;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoPessoa;
import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.contratos.MsgToViewHolderHeader;

public class PessoaLogada implements MsgFromPessoa{

    private final Pessoa pessoa;
    private ImageView espacoParaImg;

    private MsgToViewHolderHeader receptorMsg;

    public PessoaLogada() {
        this.pessoa = new Pessoa();
    }

    public void inicializarPessoa(){
        resetPessoa();

        Aplicacao.getGoogleCoisas().getPessoaLogada();
        Aplicacao.getFaceCoisas().getPessoaLogada();

        if(this.receptorMsg != null){
            this.receptorMsg.headerAlterado();
        }
    }

    public void resetPessoa(){
        this.pessoa.setNomeCompleto("Não Logado");
        this.pessoa.setIntImgPerfil(R.drawable.icon_people_128);
        this.pessoa.setEmail("temp@temp");
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void getImgPessoa(ImageView imgView){
        if(this.pessoa.getNomeCompleto() == "Não Logado"){
            imgView.setImageResource(this.pessoa.getIntImgPerfil());
            imgView.invalidate();
        }
        else{
            this.espacoParaImg = imgView;
            ConexaoPessoa conexaoPessoa = new ConexaoPessoa(this);
            conexaoPessoa.downloadImageNaUrl(this.pessoa.getUriImgPerfil());
        }
    }

    @Override
    public void passarBitmapImg(Bitmap bitmap) {
        this.espacoParaImg.setImageBitmap(bitmap);
        this.espacoParaImg.invalidate();
    }

    public void setReceptorMsg(MsgToViewHolderHeader receptorMsg) {
        this.receptorMsg = receptorMsg;
    }
}

