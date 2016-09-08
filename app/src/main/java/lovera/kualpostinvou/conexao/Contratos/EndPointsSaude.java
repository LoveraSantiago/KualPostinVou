package lovera.kualpostinvou.conexao.contratos;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface EndPointsSaude {

    @GET("rest/estabelecimentos")
    Call<List<Estabelecimento>> getEstabelecimentos(@QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/{infLongLatRaio}")
    Call<List<Estabelecimento>> getEstabelecimentos(@Path("infLongLatRaio") String infLongLatRario, @QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/unidade/{codUnidade}")
    Call<Estabelecimento> getEstabelecimento(@Path("codUnidade") String codUnidade);

    @GET("GET /rest/especialidades/unidade/{codUnidade}")
    Call<List<Especialidade>> getEspecialidades(@Path("codUnidade") String codUnidade);
}
