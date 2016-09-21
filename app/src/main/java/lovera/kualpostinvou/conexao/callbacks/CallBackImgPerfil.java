package lovera.kualpostinvou.conexao.callbacks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import lovera.kualpostinvou.Aplicacao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackImgPerfil implements Callback<ResponseBody> {

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.body() != null){
            Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
            Aplicacao.getPessoaLogada().passarBitmapImg(bm);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}
