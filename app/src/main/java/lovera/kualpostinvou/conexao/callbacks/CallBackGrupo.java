package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.modelos.Grupo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackGrupo implements Callback<ResponseBody>{

    private final Grupo grupo;

    public CallBackGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            int posicaoBarra = location.lastIndexOf("/");
            String codigo = location.substring(posicaoBarra, location.length());
            this.grupo.setCodGrupo(Integer.parseInt(codigo));

        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
