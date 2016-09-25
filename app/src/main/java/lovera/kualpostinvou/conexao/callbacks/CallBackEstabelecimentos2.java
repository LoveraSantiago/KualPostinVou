package lovera.kualpostinvou.conexao.callbacks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackEstabelecimentos2 implements Callback<ResponseBody>{

    private final MsgFromConexaoSaude msg;

    public CallBackEstabelecimentos2(MsgFromConexaoSaude msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ResponseBody body = response.body();
        try {
            String result = body.string().replace("long", "longi");

            Type listType = new TypeToken<List<Estabelecimento>>() {}.getType();
            Gson gson = new Gson();

            List<Estabelecimento> listaEstabelecimentos = gson.fromJson(result, listType);
            this.msg.passarListaDeEstabelecimentos(listaEstabelecimentos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}
