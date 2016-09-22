package lovera.kualpostinvou.conexao;

import android.net.Uri;


import java.io.IOException;
import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackAutenticar;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarPessoa;
import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPessoa;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConexaoPessoa {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br";

    private final Retrofit retrofit;

    private final EndPointsPessoa endpointPessoa;

    private final HelperParams_EndPessoa helper;


    public ConexaoPessoa() {
        this.helper = new HelperParams_EndPessoa();

        this.retrofit = Factory.factoryRetrofit(URL_BASE);
        this.endpointPessoa = retrofit.create(EndPointsPessoa.class);
    }

    public void downloadImageNaUrl(Uri uri){
        this.endpointPessoa.downloadImageNaUrl(uri.toString())
                      .enqueue(new CallBackImgPerfil());
    }

    //TODO:Candidato a ser removido 21/09/2016
    public void cadastrarPessoa(Pessoa pessoa){
        Call<ResponseBody> call = this.endpointPessoa.cadastrarPessoa(pessoa);
        call.enqueue(new CallBackCadastrarPessoa(this.retrofit));
    }

    public void cadastrarPessoa(Pessoa pessoa, StringBuilder location, ErrorObj error){
        Call<ResponseBody> call = this.endpointPessoa.cadastrarPessoa(pessoa);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackCadastrarPessoa callBackCadastrarPessoa = new CallBackCadastrarPessoa(this.retrofit);
            callBackCadastrarPessoa.procedimentoSincrono(response, location, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO:Candidato a ser removido 21/09/2016
    public void autenticar(Pessoa pessoa){
        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
        Call<ResponseBody> call = this.endpointPessoa.autenticar(mapParams);
        call.enqueue(new CallBackAutenticar(this.retrofit));
    }

    public void autenticar(Pessoa pessoa, StringBuilder token, ErrorObj error){
        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
        Call<ResponseBody> call = this.endpointPessoa.autenticar(mapParams);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackAutenticar callBackAutenticar = new CallBackAutenticar(this.retrofit);
            callBackAutenticar.procedimentoSincrono(response, token, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
