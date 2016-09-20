package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackEstabelecimento implements Callback<Estabelecimento> {

    private final MsgFromConexao msg;

    public CallBackEstabelecimento(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<Estabelecimento> call, Response<Estabelecimento> response) {
        this.msg.passarEstabelecimento(response.body());
    }

    @Override
    public void onFailure(Call<Estabelecimento> call, Throwable t) {
        t.printStackTrace();
    }
}
