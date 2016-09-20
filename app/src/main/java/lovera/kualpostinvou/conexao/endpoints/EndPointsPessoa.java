package lovera.kualpostinvou.conexao.endpoints;


import lovera.kualpostinvou.modelos.Pessoa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface EndPointsPessoa {

    @GET
    Call<ResponseBody> downloadImageNaUrl(@Url String url);

    @POST("/rest/pessoas")
    Call<ResponseBody> cadastrarPessoa(@Body Pessoa pessoa);
}