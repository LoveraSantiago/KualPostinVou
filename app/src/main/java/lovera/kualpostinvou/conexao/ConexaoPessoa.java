package lovera.kualpostinvou.conexao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import lovera.kualpostinvou.conexao.contratos.MsgFromPessoa;
import lovera.kualpostinvou.conexao.endpoints.EndPointsPessoa;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ConexaoPessoa {

    private MsgFromPessoa msg;

    public ConexaoPessoa(MsgFromPessoa msg) {
        this.msg = msg;
    }

    public void download(Uri uri){
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(uri.toString())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        EndPointsPessoa endPointPessoa = retrofit.create(EndPointsPessoa.class);
        endPointPessoa.downloadImgFace(uri.toString())
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                    if(response.body() != null){
                        try {
                            Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                            msg.passarBitmapImg(bm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

    }

}
