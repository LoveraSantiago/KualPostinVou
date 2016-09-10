package lovera.kualpostinvou.conexao.contratos;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface EndPointsSaude {

    @GET("rest/estabelecimentos")
    Call<List<Estabelecimento>> getEstabelecimentos(@QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/latitude/{infLatitude}/longitude/{infLongitude}/raio/{infRaio}")
    Call<List<Estabelecimento>> getEstabelecimentos(@Path("infLatitude") String infLatitude,
                                                    @Path("infLongitude")   String infLongitude,
                                                    @Path("infRaio")        String infRaio,
                                                    @QueryMap Map<String, String> params);

    @GET("rest/estabelecimentos/unidade/{codUnidade}")
    Call<Estabelecimento> getEstabelecimento(@Path("codUnidade") String codUnidade);

    @GET("rest/especialidades/unidade/{codUnidade}")
    Call<List<Especialidade>> getEspecialidades(@Path("codUnidade") String codUnidade);

    @GET("rest/profissionais/unidade/{codUnidade}")
    Call<List<Profissional>> getProfissionais(@Path("codUnidade") String codUnidade);
}
