package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import java.util.List;

import lovera.kualpostinvou.conexao.utils.ErrorUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackAutenticar implements Callback<ResponseBody> {

    private Retrofit retrofit;

    public CallBackAutenticar(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String token = response.headers().get("apptoken");

        }
        else{
            ErrorObj errorObj = ErrorUtils.parseError(retrofit, response);
            List<MsgErrorObj> mensagens = errorObj.getMensagens();
            for(MsgErrorObj error : mensagens){
                Log.i("Cadastro", error.getTexto());
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}
