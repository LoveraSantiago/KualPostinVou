package lovera.kualpostinvou;

import android.app.Application;

import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;
import lovera.kualpostinvou.views.redes_sociais.google.Google_Coisas;

public class Aplicacao extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Google_Coisas googleCoisas = new Google_Coisas(this);
        Facebook_Coisas faceCoisas = new Facebook_Coisas(this);
//        faceCoisas.printHashKey();
    }
}
