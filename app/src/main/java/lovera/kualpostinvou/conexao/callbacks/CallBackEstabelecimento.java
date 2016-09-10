package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CallBackEstabelecimento implements Callback<Estabelecimento>{

    private final MsgFromConexao msg;

    public CallBackEstabelecimento(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Response<Estabelecimento> response, Retrofit retrofit) {
        this.msg.passarEstabelecimento(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
