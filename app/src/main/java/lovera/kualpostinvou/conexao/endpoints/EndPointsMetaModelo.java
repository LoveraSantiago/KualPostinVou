package lovera.kualpostinvou.conexao.endpoints;


import java.util.Map;

import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.Instalacao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface EndPointsMetaModelo {

    @GET("appCivicoRS/rest/pessoas/autenticar")
    Call<Pessoa> autenticar(@HeaderMap Map<String, String> params);

    @POST("appCivicoRS/rest/pessoas")
    Call<ResponseBody> cadastrarPessoa(@Body Pessoa pessoa);

    @POST("appCivicoRS/rest/instalacoes")
    Call<ResponseBody> cadastrarInstalacao(@Header("appToken") String appToken, @Body Instalacao instalacao);

    @GET
    Call<ResponseBody> downloadImageNaUrl(@Url String url);
}