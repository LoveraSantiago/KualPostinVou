package lovera.kualpostinvou.views.navigationdrawer;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoPessoa;
import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.contratos.MsgToViewHolderHeader;
import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;

public class PessoaLogada implements MsgFromPessoa{

    private static int NAO_LOGADO = 0;
    private static int FACEBOOK = 1;

    private final Pessoa pessoa;
    private int statusLog;
    private ImageView espacoParaImg;

    private MsgToViewHolderHeader receptorMsg;

    public PessoaLogada() {
        this.pessoa = new Pessoa();
    }

    public void inicializarPessoa(){
        resetPessoa();
        Aplicacao.getFaceCoisas().getPessoaLogada();
        this.statusLog = FACEBOOK;

        if(this.pessoa.getNomeCompleto().equals("Não Logado")){
           this.statusLog = NAO_LOGADO;
        }
        if(this.receptorMsg != null){
            this.receptorMsg.headerAlterado();
        }
    }

    public void resetPessoa(){
        this.statusLog = NAO_LOGADO;
        this.pessoa.setNomeCompleto("Não Logado");
        this.pessoa.setIntImgPerfil(R.drawable.icon_people_128);
        this.pessoa.setEmail("temp@temp");
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void getImgPessoa(ImageView imgView){
        if(this.statusLog == NAO_LOGADO){
            imgView.setImageResource(this.pessoa.getIntImgPerfil());
            imgView.invalidate();
        }
        else if(this.statusLog == FACEBOOK){

            this.espacoParaImg = imgView;
            ConexaoPessoa conexaoPessoa = new ConexaoPessoa(this);
            conexaoPessoa.download(this.pessoa.getUriImgPerfil());
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

