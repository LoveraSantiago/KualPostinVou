package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackCadastrarPessoa {

    private Retrofit retrofit;

    public CallBackCadastrarPessoa(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void procedimentoSincrono(Response<ResponseBody> response, StringBuilder location, ErrorObj error){
        location.setLength(0);

        if(response.isSuccessful()){
            location.append(response.headers().get("location"));
            Log.i("Location", location.toString());
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

            ParserUtils parser = new ParserUtils();
            error = parser.parseError(this.retrofit, response);
        }
    }
}
