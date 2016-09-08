package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CallBackEstabelecimentos implements Callback<List<Estabelecimento>> {

    private final MsgFromConexao msg;

    public CallBackEstabelecimentos(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Response<List<Estabelecimento>> response, Retrofit retrofit) {
        msg.passarListaDeEstabelecimentos(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
