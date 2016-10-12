package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackConteudoPostagem implements Callback<ResponseBody>{

    private final MsgFromConexaoModelo msg;
    private final ConteudoPostagem conteudo;

    public CallBackConteudoPostagem(MsgFromConexaoModelo msg, ConteudoPostagem conteudo) {
        this.msg = msg;
        this.conteudo = conteudo;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            int posicaoBarra = location.lastIndexOf("/") + 1;
            String codigo = location.substring(posicaoBarra, location.length());
            this.conteudo.setCodConteudo(Integer.parseInt(codigo));
            this.msg.passarConteudoPostagem(this.conteudo);
        }
        else{
            //TODO
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
