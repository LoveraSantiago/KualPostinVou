package lovera.kualpostinvou.conexao.callbacks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import lovera.kualpostinvou.modelos.Estabelecimento;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackEstabelecimentos3 {

    public static void procedimentoSincrono(Retrofit retrofit, Response<ResponseBody> response, List<Estabelecimento> listaEstabelecimentos){
        if(response.isSuccessful()){
            ResponseBody body = response.body();
            try {
                String result = body.string().replace("long", "longi");

                Type listType = new TypeToken<List<Estabelecimento>>() {}.getType();
                Gson gson = new Gson();

                List<Estabelecimento> listaResult = gson.fromJson(result, listType);
                listaEstabelecimentos.clear();
                listaEstabelecimentos.addAll(listaResult);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            //PARA FUTURO TRATAMENTO DE ERRO
//            ParserUtils parser = new ParserUtils();
//            ErrorObj errorResult = parser.parseError(retrofit, response);
//            ErrorObj.cloneErrorObjeto(error, errorResult);
        }
    }
}
