package lovera.kualpostinvou.conexao.callbacks;

import android.util.Log;

import java.util.List;

import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import lovera.kualpostinvou.modelos.utils.Utils_Modelo;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackAutenticar{

    private Retrofit retrofit;

    public CallBackAutenticar(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void procedimentoSincrono(Response<Pessoa> response, Pessoa pessoa, StringBuilder token, ErrorObj error){
        token.setLength(0);

        if(response.isSuccessful()){
            token.append(response.headers().get("apptoken"));

            Pessoa pessoaTemp = response.body();
            pessoa.setCod(pessoaTemp.getCod());
        }
        else{
            ParserUtils parser = new ParserUtils();
            ErrorObj errorObj = parser.parseError(this.retrofit, response);
            List<MsgErrorObj> mensagens = errorObj.getMensagens();
            for(MsgErrorObj errorIt : mensagens){
                Log.i("Cadastro", errorIt.getTexto());
            }
            Utils_Modelo.cloneErrorObjeto(error, errorObj);
        }
    }
}
