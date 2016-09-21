package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Servicos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackServicos implements Callback<List<Servicos>> {

    private final MsgFromConexaoSaude msg;

    public CallBackServicos(MsgFromConexaoSaude msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<List<Servicos>> call, Response<List<Servicos>> response) {
        this.msg.passarListaDeServicos(response.body());
    }

    @Override
    public void onFailure(Call<List<Servicos>> call, Throwable throwable) {
        throwable.printStackTrace();
    }
}
