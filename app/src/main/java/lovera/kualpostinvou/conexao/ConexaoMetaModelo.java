package lovera.kualpostinvou.conexao;

import android.net.Uri;


import java.io.IOException;
import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackAutenticar;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarInstalacao;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarPessoa;
import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.endpoints.EndPointsMetaModelo;
import lovera.kualpostinvou.conexao.utils.FactoryConexao;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPessoa;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.Instalacao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConexaoMetaModelo {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br";

    private final Retrofit retrofit;

    private final EndPointsMetaModelo endpointMetaModelo;

    private final HelperParams_EndPessoa helper;


    public ConexaoMetaModelo() {
        this.helper = new HelperParams_EndPessoa();

        this.retrofit = FactoryConexao.factoryRetrofit(URL_BASE);
        this.endpointMetaModelo = retrofit.create(EndPointsMetaModelo.class);
    }

    public void downloadImageNaUrl(Uri uri){
        this.endpointMetaModelo.downloadImageNaUrl(uri.toString())
                      .enqueue(new CallBackImgPerfil());
    }

    //TODO:Candidato a ser removido 21/09/2016
//    public void autenticar(Pessoa pessoa){
//        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
//        Call<ResponseBody> call = this.endpointMetaModelo.autenticar(mapParams);
//        call.enqueue(new CallBackAutenticar(this.retrofit));
//    }

    public void autenticar(Pessoa pessoa, StringBuilder token, ErrorObj error){
        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
        Call<Pessoa> call = this.endpointMetaModelo.autenticar(mapParams);
        try {
            Response<Pessoa> response = call.execute();
            CallBackAutenticar callBackAutenticar = new CallBackAutenticar(this.retrofit);
            callBackAutenticar.procedimentoSincrono(response, pessoa, token, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void cadastrarInstalacao(String appToken, Instalacao instalacao, StringBuilder result, ErrorObj error){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarInstalacao(appToken, instalacao);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackCadastrarInstalacao callBackCadastrarInstalacao = new CallBackCadastrarInstalacao(this.retrofit);
            callBackCadastrarInstalacao.procedimentoSincrono(response, result, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarPessoa(Pessoa pessoa, StringBuilder location, ErrorObj error){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarPessoa(pessoa);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackCadastrarPessoa callBackCadastrarPessoa = new CallBackCadastrarPessoa(this.retrofit);
            callBackCadastrarPessoa.procedimentoSincrono(response, location, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
