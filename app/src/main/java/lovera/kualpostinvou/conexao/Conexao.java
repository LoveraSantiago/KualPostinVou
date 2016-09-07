package lovera.kualpostinvou.conexao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.conexao.apis.ApiEstabelecimentoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static lovera.kualpostinvou.conexao.ConstantesParametros.*;

public class Conexao {

    private final MsgFromConexao msg;

    public Conexao(MsgFromConexao msg) {
        this.msg = msg;
    }

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br/mapa-da-saude/";

    public void getEstabelecimentos(String municipio, String uf, List<String> campos, int pagina, int quantidade){

        Map<String, String> mapParams = factoryMapParametros(municipio, uf, campos, pagina, quantidade);

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL_BASE)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        ApiEstabelecimentoSaude saudeApi = retrofit.create(ApiEstabelecimentoSaude.class);
        Call<List<Estabelecimento>> call = saudeApi.getEstabelecimentos(mapParams);
        call.enqueue(new Callback<List<Estabelecimento>>() {

            @Override
            public void onResponse(Response<List<Estabelecimento>> response, Retrofit retrofit) {
                msg.passarListaDeEstabelecimentos(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Map<String, String> factoryMapParametros(String municipio, String uf, List<String> campos, int pagina, int quantidade){
        Map<String, String> map = new HashMap<>();

        if(stringNotNullOrEmpty(municipio)){
            map.put(MUNICIPIO, municipio);
        }
        if(stringNotNullOrEmpty(uf)){
            map.put(UF, uf);
        }
        if(isListNotNullOrEmpty(campos)){
            StringBuilder camposVirgula = new StringBuilder();
            for(String campo : campos){
                camposVirgula.append(campo + ",");
            }
            map.put(CAMPOS, camposVirgula.toString());
        }
        if(isNumberMaiorQueZero(pagina)){
            map.put(PAGINA, String.valueOf(pagina));
        }
        if(isNumberMaiorQueZero(quantidade)){
            map.put(QUANTIDADE, String.valueOf(quantidade));
        }
        return map;
    }

    private boolean stringNotNullOrEmpty(String texto){
        if(texto == null || texto.isEmpty()) return false;
        return true;
    }

    private boolean isListNotNullOrEmpty(List lista){
        if(lista == null || lista.size() <= 0) return false;
        return true;
    }

    private boolean isNumberMaiorQueZero(int numero){
        if(numero > 0) return true;
        return false;
    }
}
