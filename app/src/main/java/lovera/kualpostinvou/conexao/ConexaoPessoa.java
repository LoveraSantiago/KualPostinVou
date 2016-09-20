package lovera.kualpostinvou.conexao;

import android.net.Uri;
import android.util.Log;


import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import lovera.kualpostinvou.conexao.utils.ErrorUtils;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConexaoPessoa {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br/mapa-da-saude/";

    private final Retrofit retrofit;
    private final EndPointsPessoa endpointPessoa;

    private MsgFromPessoa msg;

    public ConexaoPessoa(MsgFromPessoa msg) {
        this.msg = msg;

        this.retrofit = Factory.factoryRetrofit(URL_BASE);
        this.endpointPessoa = retrofit.create(EndPointsPessoa.class);
    }

    public void download(Uri uri){
        Retrofit retrofit = Factory.factoryRetrofit(uri.toString());
        EndPointsPessoa endPointPessoa_outraurlbase = retrofit.create(EndPointsPessoa.class);
        endPointPessoa_outraurlbase.downloadImageNaUrl(uri.toString())
                      .enqueue(new CallBackImgPerfil(this.msg));
    }

    public void cadastrarPessoa(Pessoa pessoa){
        Call<ResponseBody> call = this.endpointPessoa.cadastrarPessoa(pessoa);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    String location = response.headers().get("location");
                    Log.i("Location", location);
                }
                else{
                    ErrorObj errorObj = ErrorUtils.parseError(retrofit, response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

}
