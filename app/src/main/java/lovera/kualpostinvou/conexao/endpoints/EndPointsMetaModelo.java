package lovera.kualpostinvou.conexao.endpoints;


import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.Instalacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.TipoObjeto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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

    @GET("appCivicoRS/rest/grupos")
    Call<List<GrupoR>> getGrupos(@QueryMap Map<String, String> params);

    @POST("appCivicoRS/rest/grupos")
    Call<ResponseBody> cadastrarGrupo(@Header("appToken") String appToken, @Body Grupo grupo);

    @POST("appCivicoRS/rest/postagens")
    Call<ResponseBody> cadastrarPostagem(@Header("appIdentifier") String codApp, @Header("appToken") String appToken, @Body Postagem postagem);

    @GET("appCivicoRS/rest/postagens")
    Call<List<PostagemR>> getPostagens(@Header("appToken") String appToken, @Query("codGrupoDestino") long codGrupoDestino);

    @POST("appCivicoRS/rest/postagens/{codPostagem}/conteudos")
    Call<ResponseBody> cadastrarConteudoPostagem(@Header("appToken") String appToken, @Path("codPostagem") String codPostagem, @Body ConteudoPostagem conteudoPostagem);

    @GET("appCivicoRS/rest/postagens/tipopostagem/{codTipoPostagem}/tipoobjeto/{codTipoObjetoDestino}/objeto/{codObjetoDestino}")
    Call<Media> getMedia(@Path("codTipoPostagem") String codTipoPostagem, @Path("codTipoObjetoDestino") String codTipoObjetoDestino, @Path("codObjetoDestino") String codObjetoDestino);

    @POST("appCivicoRS/rest/tipos-objeto")
    Call<ResponseBody> cadastrarTipoObjeto(@Header("appToken") String appToken, @Body TipoObjeto tipoObjeto);
}

