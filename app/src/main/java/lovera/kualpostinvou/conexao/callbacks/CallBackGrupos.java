package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackGrupos implements Callback<List<Grupo>>{

    private final MsgFromConexaoModelo msg;
    private final Retrofit retrofit;

    public CallBackGrupos(MsgFromConexaoModelo msg, Retrofit retrofit) {
        this.msg = msg;
        this.retrofit = retrofit;
    }

    @Override
    public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
        if(response.isSuccessful()){
            List<Grupo> listaGrupo = response.body();
            if(listaGrupo.size() > 0){
                this.msg.passarGrupo(listaGrupo.get(0));
            }
            else{
                ErrorObj error = new ErrorObj();
                error.setReasonPhrase("Grupo não criado");
                this.msg.passarErrorObjeto(error, this.msg.COD_GRUPO_INEXISTENTE);
            }
        }
        else{
            ParserUtils parser = new ParserUtils();
            ErrorObj errorObj = parser.parseError(this.retrofit, response);
            this.msg.passarErrorObjeto(errorObj, this.msg.COD_ERRO_GRUPOS);
        }
    }

    @Override
    public void onFailure(Call<List<Grupo>> call, Throwable t) {
        t.printStackTrace();
    }
}
