package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackPostagem implements Callback<ResponseBody>{

    private final MsgFromConexaoModelo msg;
    private final PostagemR postagem;

    public CallBackPostagem(MsgFromConexaoModelo msg, Postagem postagem) {
        this.msg = msg;
        this.postagem = FactoryModelos.geradorDePostagemR(postagem);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            int posicaoBarra = location.lastIndexOf("/") + 1;
            String codigo = location.substring(posicaoBarra, location.length());
            this.postagem.setCodPostagem(Integer.parseInt(codigo));
            this.msg.passarPostagem(this.postagem);
        }
        else{
            //TODO
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}
