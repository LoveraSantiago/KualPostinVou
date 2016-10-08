package lovera.kualpostinvou.views.components.helpers;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import lovera.kualpostinvou.modelos.Pessoa;

public class ImgViewDrawerNavigComponent {

    private ImageView espacoParaImg;

    public void setEspacoParaImg(ImageView espacoParaImg) {
        this.espacoParaImg = espacoParaImg;
    }

    public void configurarImgViewPessoaLogada(ImageView imgView){
        configurarTamanhoImgView(imgView);
        imgView.setBackgroundColor(Color.rgb(237, 140, 114));
        imgView.setPadding(15, 15, 15, 15);
    }

    public void configurarImgViewPessoaNLogada(ImageView imgView, Pessoa pessoa){
        configurarTamanhoImgView(imgView);
        imgView.setImageBitmap(null);
        imgView.setImageResource(pessoa.getIntImgPerfil());
        imgView.setBackgroundColor(Color.rgb(47, 73, 110));
        imgView.setPadding(0, 0, 0, 0);
    }

    private void configurarTamanhoImgView(ImageView imgView){
        imgView.setMaxWidth(80);
        imgView.setMinimumWidth(80);
        imgView.setMaxHeight(80);
        imgView.setMinimumHeight(80);
    }

    public void passarBitmapImg(Bitmap bitmap) {
        this.espacoParaImg.setImageBitmap(bitmap);
        this.espacoParaImg.invalidate();
    }
}
