package lovera.kualpostinvou.conexao.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import lovera.kualpostinvou.modelos.ErrorObj;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ParserUtils {

    public ErrorObj parseError(Retrofit retrofit, Response<?> response){
        Converter<ResponseBody, ErrorObj> converter = retrofit.responseBodyConverter(ErrorObj.class, new Annotation[0]);

        ErrorObj errorObj = null;

        try {
            errorObj = converter.convert(response.errorBody()) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorObj;
    }

}
