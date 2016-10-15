package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackEditConteudoPostagem implements Callback<ResponseBody> {

    private final MsgFromConexaoModelo msg;
    private final ConteudoPostagem conteudoPostagem;

    public CallBackEditConteudoPostagem(MsgFromConexaoModelo msg, ConteudoPostagem conteudoPostagem) {
        this.msg = msg;
        this.conteudoPostagem = conteudoPostagem;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.i("callback", "calback chamado");
        System.out.println("oi");
        this.msg.conteudoPostagemEditado(this.conteudoPostagem);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}