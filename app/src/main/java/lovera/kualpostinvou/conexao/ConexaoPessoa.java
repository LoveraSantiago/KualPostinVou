package lovera.kualpostinvou.conexao;

import android.net.Uri;
import android.util.Log;


import java.io.IOException;
import java.util.List;

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
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    String location = response.headers().get("location");
                    Log.i("Location", location);
                }
                else{
                    try{
                        if(response.errorBody().string().toString().equals("Usuário já cadastrado ou desativado")){
                            Log.i("Cadastro", "Usuário já cadastrado ou desativado");
                            return;
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    ErrorObj errorObj = ErrorUtils.parseError(retrofit, response);
                    List<MsgErrorObj> mensagens = errorObj.getMensagens();
                    for(MsgErrorObj error : mensagens){
                        Log.i("Cadastro", error.getTexto());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

}
