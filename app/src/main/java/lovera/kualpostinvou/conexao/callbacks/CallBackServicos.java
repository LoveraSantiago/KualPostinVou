package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Servicos;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CallBackServicos implements Callback<List<Servicos>>{

    private final MsgFromConexao msg;

    public CallBackServicos(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Response<List<Servicos>> response, Retrofit retrofit) {
        this.msg.passarListaDeServicos(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
