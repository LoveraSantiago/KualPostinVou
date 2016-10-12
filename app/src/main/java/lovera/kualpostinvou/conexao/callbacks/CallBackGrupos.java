package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.utils.ParserUtils;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallBackGrupos implements Callback<List<GrupoR>>{

    private final MsgFromConexaoModelo msg;
    private final Retrofit retrofit;
    private final int resultCode;

    public CallBackGrupos(MsgFromConexaoModelo msg, Retrofit retrofit, int resultCode) {
        this.msg = msg;
        this.retrofit = retrofit;
        this.resultCode = resultCode;
    }

    @Override
    public void onResponse(Call<List<GrupoR>> call, Response<List<GrupoR>> response) {
        if(response.isSuccessful()){
            List<GrupoR> listaGrupo = response.body();
            if(listaGrupo.size() > 0){
                this.msg.passarGrupo(listaGrupo.get(0), this.resultCode);
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
    public void onFailure(Call<List<GrupoR>> call, Throwable t) {
        t.printStackTrace();
    }
}
