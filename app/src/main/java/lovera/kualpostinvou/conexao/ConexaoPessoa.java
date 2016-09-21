package lovera.kualpostinvou.conexao;

import android.net.Uri;
import android.util.Log;


import java.io.IOException;
import java.util.List;

import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarPessoa;
import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import lovera.kualpostinvou.conexao.utils.ErrorUtils;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConexaoPessoa {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br";

    private final Retrofit retrofit;
    private final EndPointsPessoa endpointPessoa;

    private MsgFromPessoa msg;

    public ConexaoPessoa(MsgFromPessoa msg) {
        this.msg = msg;

        this.retrofit = Factory.factoryRetrofit(URL_BASE);
        this.endpointPessoa = retrofit.create(EndPointsPessoa.class);
    }

    public void downloadImageNaUrl(Uri uri){
        this.endpointPessoa.downloadImageNaUrl(uri.toString())
                      .enqueue(new CallBackImgPerfil(this.msg));
    }

    public void cadastrarPessoa(Pessoa pessoa){
        Call<ResponseBody> call = this.endpointPessoa.cadastrarPessoa(pessoa);
        call.enqueue(new CallBackCadastrarPessoa(this.retrofit));
    }

}
