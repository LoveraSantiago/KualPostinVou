package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.Grupo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackGrupo implements Callback<ResponseBody>{

    private final MsgFromConexaoModelo msg;
    private final int resultCode;
    private final Grupo grupo;

    public CallBackGrupo(MsgFromConexaoModelo msg, Grupo grupo, int resultCode) {
        this.msg = msg;
        this.grupo = grupo;
        this.resultCode = resultCode;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            int posicaoBarra = location.lastIndexOf("/") + 1;
            String codigo = location.substring(posicaoBarra, location.length());
            this.grupo.setCodGrupo(Integer.parseInt(codigo));
            this.msg.passarGrupo(this.grupo, this.resultCode);
        }
        else{
            //TODO error
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
