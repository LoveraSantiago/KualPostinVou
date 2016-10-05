package lovera.kualpostinvou.conexao.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FactoryConexao {

    public static Retrofit factoryRetrofit(String url){
        return new Retrofit.Builder()
                           .baseUrl(url)
                           .addConverterFactory(GsonConverterFactory.create())
                           .build();
    }

}
