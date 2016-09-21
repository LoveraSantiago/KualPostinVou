package lovera.kualpostinvou.conexao;

import android.net.Uri;


import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackAutenticar;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarPessoa;
import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPessoa;
import lovera.kualpostinvou.modelos.Pessoa;
import okhttp3.ResponseBody;
import retrofit2.Call;
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

    public void cadastrarPessoa(Pessoa pessoa){
        Call<ResponseBody> call = this.endpointPessoa.cadastrarPessoa(pessoa);
        call.enqueue(new CallBackCadastrarPessoa(this.retrofit));
    }

    public void autenticar(Pessoa pessoa){
        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
        Call<ResponseBody> call = this.endpointPessoa.autenticar(mapParams);
        call.enqueue(new CallBackAutenticar(this.retrofit));
    }
}
