package lovera.kualpostinvou.conexao;

import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackEspecialidades;
import lovera.kualpostinvou.conexao.callbacks.CallBackEstabelecimentos;
import lovera.kualpostinvou.conexao.callbacks.CallBackEstabelecimentos2;
import lovera.kualpostinvou.conexao.callbacks.CallBackProfissionais;
import lovera.kualpostinvou.conexao.callbacks.CallBackServicos;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.conexao.endpoints.EndPointsSaude;
import lovera.kualpostinvou.conexao.utils.Factory;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ConexaoSaude {

    private final MsgFromConexaoSaude msg;

    private final EndPointsSaude endpointSaude;

    private final HelperParams_EndPSaude helper;

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br/mapa-da-saude/";

    public ConexaoSaude(MsgFromConexaoSaude msg) {
        this.msg = msg;

        Retrofit retrofit = Factory.factoryRetrofit(URL_BASE);
        this.endpointSaude = retrofit.create(EndPointsSaude.class);

        this.helper = new HelperParams_EndPSaude();
    }

    public void getEstabelelecimento(String codUnidade){
        Call<ResponseBody> call = this.endpointSaude.getEstabelecimento(codUnidade);
        call.enqueue(new CallBackEstabelecimentos2(this.msg));
    }

    public void getEstabelecimentos(String municipio, String uf, List<String> campos, String especialidade, int pagina, int quantidade){
        Map<String, String> mapParams = helper.factoryMap_EndP_Estabelecimentos(municipio, uf, campos, especialidade, pagina, quantidade);

        Call<List<Estabelecimento>> call = this.endpointSaude.getEstabelecimentos(mapParams);
        call.enqueue(new CallBackEstabelecimentos(this.msg));
    }

    /**
     *
     * @param latitude * Obrigatório
     * @param longitude * Obrigatóriov
     * @param raio * Obrigatório
     */
    public void getEstabelecimentos(double latitude, double longitude, float raio, String texto, String categoria, String campos, int pagina, int quantidade){
        Map<String, String> mapParams = helper.factoryMap_EndP_Estabelecimentos(texto, categoria, campos, pagina, quantidade);

        Call<ResponseBody> call = this.endpointSaude.getEstabelecimentos(String.valueOf(latitude), String.valueOf(longitude), String.valueOf(raio) , mapParams);
        call.enqueue(new CallBackEstabelecimentos2(this.msg));
    }

    public void getEspecialidades(String codUnidade){
        Call<List<Especialidade>> call = this.endpointSaude.getEspecialidades(codUnidade);
        call.enqueue(new CallBackEspecialidades(this.msg));
    }

    public void getProfissionais(String codUnidade){
        Call<List<Profissional>> call = this.endpointSaude.getProfissionais(codUnidade);
        call.enqueue(new CallBackProfissionais(this.msg));
    }

    public void getServicos(String codUnidade){
        Call<List<Servicos>> call = this.endpointSaude.getServicos(codUnidade);
        call.enqueue(new CallBackServicos(this.msg));
    }
}
