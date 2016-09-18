package lovera.kualpostinvou.conexao.endpoints;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

public interface EndPointsPessoa {

    @GET
    public Call<ResponseBody> downloadImgFace(@Url String url);

}