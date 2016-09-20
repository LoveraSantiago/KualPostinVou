package lovera.kualpostinvou.conexao;

import android.net.Uri;

import com.squareup.okhttp.ResponseBody;

import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.modelos.Pessoa;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ConexaoPessoa {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br/mapa-da-saude/";

    private final EndPointsPessoa endpointPessoa;

    private MsgFromPessoa msg;

    public ConexaoPessoa(MsgFromPessoa msg) {
        this.msg = msg;

        Retrofit retrofit = Factory.factoryRetrofit(URL_BASE);
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
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
