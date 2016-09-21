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
        procedimentoComum(response, new StringBuilder(), new ErrorObj());
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }

    public void procedimentoSincrono(Response<ResponseBody> response, StringBuilder token, ErrorObj error){
        procedimentoComum(response, token, error);
    }

    private void procedimentoComum(Response<ResponseBody> response, StringBuilder token, ErrorObj error){
        token.setLength(0);

        if(response.isSuccessful()){
            token.append(response.headers().get("apptoken"));
        }
        else{
            ErrorObj errorObj = ErrorUtils.parseError(this.retrofit, response);
            List<MsgErrorObj> mensagens = errorObj.getMensagens();
            for(MsgErrorObj errorIt : mensagens){
                Log.i("Cadastro", errorIt.getTexto());
            }
            ErrorUtils.cloneErrorObjeto(error, errorObj);
        }
    }
}
