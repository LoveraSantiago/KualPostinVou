package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackConteudoPostagem implements Callback<ConteudoPostagem>{

    private final MsgFromConexaoModelo msg;

    public CallBackConteudoPostagem(MsgFromConexaoModelo msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<ConteudoPostagem> call, Response<ConteudoPostagem> response) {
        this.msg.passarConteudoDaPostagem(response.body());
    }

    @Override
    public void onFailure(Call<ConteudoPostagem> call, Throwable t) {

    }
}
