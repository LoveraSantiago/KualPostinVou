package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackEstabelecimentos implements Callback<List<Estabelecimento>> {

    private final MsgFromConexao msg;

    public CallBackEstabelecimentos(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<List<Estabelecimento>> call, Response<List<Estabelecimento>> response) {
        this.msg.passarListaDeEstabelecimentos(response.body());
    }

    @Override
    public void onFailure(Call<List<Estabelecimento>> call, Throwable t) {
        t.printStackTrace();
    }
}
