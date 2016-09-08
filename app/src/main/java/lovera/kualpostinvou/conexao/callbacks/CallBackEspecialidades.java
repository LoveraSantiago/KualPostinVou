package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.modelos.Especialidade;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by santi_000 on 07/09/2016.
 */
public class CallBackEspecialidades implements Callback<List<Especialidade>>{

    private final MsgFromConexao msg;

    public CallBackEspecialidades(MsgFromConexao msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Response<List<Especialidade>> response, Retrofit retrofit) {
        this.msg.passarListaDeEspecialidades(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
