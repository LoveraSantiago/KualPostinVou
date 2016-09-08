package lovera.kualpostinvou.conexao;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackEspecialidades;
import lovera.kualpostinvou.conexao.callbacks.CallBackEstabelecimento;
import lovera.kualpostinvou.conexao.callbacks.CallBackEstabelecimentos;
import lovera.kualpostinvou.conexao.contratos.EndPointsSaude;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexao;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ConexaoSaude {

    private final MsgFromConexao msg;

    private final EndPointsSaude endpointSaude;

    private final HelperParams_EndPSaude helper;

    public ConexaoSaude(MsgFromConexao msg) {
        this.msg = msg;

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL_BASE)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        this. endpointSaude = retrofit.create(EndPointsSaude.class);

        this.helper = new HelperParams_EndPSaude();
    }

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br/mapa-da-saude/";

    public void getEstabelelecimento(String codUnidade){
        Call<Estabelecimento> call = this.endpointSaude.getEstabelecimento(codUnidade);
        call.enqueue(new CallBackEstabelecimento(this.msg));
    }

    public void getEstabelecimentos(String municipio, String uf, List<String> campos, int pagina, int quantidade){

        Map<String, String> mapParams = helper.factoryMap_EndP_Estabelecimentos(municipio, uf, campos, pagina, quantidade);

        Call<List<Estabelecimento>> call = this.endpointSaude.getEstabelecimentos(mapParams);
        call.enqueue(new CallBackEstabelecimentos(this.msg));
    }

    /**
     *
     * @param latitude * Obrigatório
     * @param longitude * Obrigatóriov
     * @param raio * Obrigatório
     */
    public void getEstabelecimentos(double latitude, double longitude, int raio, String texto, String categoria, String campos, int pagina, int quantidade){
        String infLongLatRaio = helper.factoryString_LongLatRaio(latitude, longitude, raio);
        Map<String, String> mapParams = helper.factoryMap_EndP_Estabelecimentos(texto, categoria, campos, pagina, quantidade);

        Call<List<Estabelecimento>> call = this.endpointSaude.getEstabelecimentos(infLongLatRaio, mapParams);
        call.enqueue(new CallBackEstabelecimentos(this.msg));
    }

    public void getEspecialidades(String codUnidade){
        Call<List<Especialidade>> call = this.endpointSaude.getEspecialidades(codUnidade);
        call.enqueue(new CallBackEspecialidades(this.msg));
    }
}
