package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import java.util.List;

import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackCadastrarInstalacao {

    private Retrofit retrofit;

    public CallBackCadastrarInstalacao(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void procedimentoSincrono(Response<ResponseBody> response, StringBuilder location, ErrorObj error){
        location.setLength(0);

        if(response.isSuccessful()){
            location.append(response.headers().get("location"));
            Log.i("Location", location.toString());
        }
        else{
            ParserUtils parser = new ParserUtils();
            error = parser.parseError(this.retrofit, response);
        }
    }
}
