package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import lovera.kualpostinvou.conexao.utils.ErrorUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackCadastrarPessoa implements Callback<ResponseBody>{

    private Retrofit retrofit;

    public CallBackCadastrarPessoa(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            Log.i("Location", location);
        }
        else{
            try{
                if(response.errorBody().string().toString().equals("Usuário já cadastrado ou desativado")){
                    Log.i("Cadastro", "Usuário já cadastrado ou desativado");
                    return;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

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
