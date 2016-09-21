package lovera.kualpostinvou.conexao.endpoints;


import java.util.Map;

import lovera.kualpostinvou.modelos.Pessoa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface EndPointsPessoa {

    @GET
    Call<ResponseBody> downloadImageNaUrl(@Url String url);

    @POST("appCivicoRS/rest/pessoas")
    Call<ResponseBody> cadastrarPessoa(@Body Pessoa pessoa);

    @GET("appCivicoRS/rest/pessoas/autenticar")
    Call<ResponseBody> autenticar(@QueryMap Map<String, String> params);
}