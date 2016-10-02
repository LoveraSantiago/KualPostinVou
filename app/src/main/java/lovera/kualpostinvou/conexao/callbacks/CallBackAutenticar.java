package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
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
            ErrorObj errorResult = parser.parseError(this.retrofit, response);
            ErrorObj.cloneErrorObjeto(error, errorResult);
        }
    }
}
