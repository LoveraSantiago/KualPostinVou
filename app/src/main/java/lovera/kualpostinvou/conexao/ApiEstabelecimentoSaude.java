package lovera.kualpostinvou.conexao;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface ApiEstabelecimentoSaude {

    @GET("rest/estabelecimentos")
    Call<List<Estabelecimento>> getEstabelecimentos(@QueryMap Map<String, String> params);
}
