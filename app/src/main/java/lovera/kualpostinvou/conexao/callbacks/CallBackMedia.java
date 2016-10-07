package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.Media;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackMedia implements Callback<Media>{

    private final MsgFromConexaoModelo msg;

    public CallBackMedia(MsgFromConexaoModelo msg) {
        this.msg = msg;
    }


    @Override
    public void onResponse(Call<Media> call, Response<Media> response) {
        Media media = response.body();
        this.msg.passarMedia(media);
    }

    @Override
    public void onFailure(Call<Media> call, Throwable t) {
        t.printStackTrace();
    }
}
