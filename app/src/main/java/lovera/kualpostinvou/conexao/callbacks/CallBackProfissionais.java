package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Profissional;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CallBackProfissionais implements Callback<List<Profissional>>{

    private final MsgFromConexao msg;

    public CallBackProfissionais(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Response<List<Profissional>> response, Retrofit retrofit) {
        this.msg.passarListaDeProfissionais(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
