package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Profissional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackProfissionais implements Callback<List<Profissional>> {

    private final MsgFromConexao msg;

    public CallBackProfissionais(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<List<Profissional>> call, Response<List<Profissional>> response) {
        this.msg.passarListaDeProfissionais(response.body());
    }

    @Override
    public void onFailure(Call<List<Profissional>> call, Throwable throwable) {
        throwable.printStackTrace();
    }
}
