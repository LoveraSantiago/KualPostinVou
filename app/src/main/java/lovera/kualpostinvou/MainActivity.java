package lovera.kualpostinvou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.conexao.ApiEstabelecimentoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Map<String, String> maparams = new HashMap<>();
        maparams.put("uf", "SP");

        Retrofit retrofit = new Retrofit
                                .Builder()
                                .baseUrl("http://mobile-aceite.tcu.gov.br/mapa-da-saude/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        ApiEstabelecimentoSaude saudeApi = retrofit.create(ApiEstabelecimentoSaude.class);
        Call<List<Estabelecimento>> call = saudeApi.getEstabelecimentos(maparams);
        call.enqueue(new Callback<List<Estabelecimento>>() {
            @Override
            public void onResponse(Response<List<Estabelecimento>> response, Retrofit retrofit) {
                List<Estabelecimento> estabelecimentos = response.body();
                for(Estabelecimento e : estabelecimentos){
                    Log.i("SUCESS", e.getNomeFantasia());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ERROR", "erro no retrofit", t);
            }
        });
    }
}
