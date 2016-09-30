package lovera.kualpostinvou.conexao.endpoints;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface EndPointsSaude {

    @GET("rest/estabelecimentos")
    Call<ResponseBody> getEstabelecimentos(@QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/latitude/{infLatitude}/longitude/{infLongitude}/raio/{infRaio}")
    Call<ResponseBody> getEstabelecimentos(@Path("infLatitude") String infLatitude,
                                           @Path("infLongitude")   String infLongitude,
                                           @Path("infRaio")        String infRaio,
                                           @QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/unidade/{codUnidade}")
    Call<ResponseBody> getEstabelecimento(@Path("codUnidade") String codUnidade);

    @GET("rest/especialidades/unidade/{codUnidade}")
    Call<List<Especialidade>> getEspecialidades(@Path("codUnidade") String codUnidade);

    @GET("rest/profissionais/unidade/{codUnidade}")
    Call<List<Profissional>> getProfissionais(@Path("codUnidade") String codUnidade);

    @GET("GET /rest/servicos/unidade/{codUnidade}")
    Call<List<Servicos>> getServicos(@Path("codUnidade") String codUnidade);
}
