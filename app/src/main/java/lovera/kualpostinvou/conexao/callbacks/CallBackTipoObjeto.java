package lovera.kualpostinvou.conexao.callbacks;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.TipoObjeto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackTipoObjeto implements Callback<ResponseBody>{

    private final MsgFromConexaoModelo msg;
    private final TipoObjeto tipoObjeto;

    public CallBackTipoObjeto(MsgFromConexaoModelo msg, TipoObjeto tipoObjeto) {
        this.msg = msg;
        this.tipoObjeto = tipoObjeto;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()){
            String location = response.headers().get("location");
            int posicaoBarra = location.lastIndexOf("/") + 1;
            String codigo = location.substring(posicaoBarra, location.length());
            this.tipoObjeto.setCodTipoObjeto(Integer.parseInt(codigo));
            this.msg.passarTipoObjeto(this.tipoObjeto);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
