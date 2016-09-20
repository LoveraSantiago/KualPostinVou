package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Especialidade;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackEspecialidades implements Callback<List<Especialidade>> {

    private final MsgFromConexao msg;

    public CallBackEspecialidades(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<List<Especialidade>> call, Response<List<Especialidade>> response) {
        this.msg.passarListaDeEspecialidades(response.body());
    }

    @Override
    public void onFailure(Call<List<Especialidade>> call, Throwable throwable) {
        throwable.printStackTrace();
    }
}
