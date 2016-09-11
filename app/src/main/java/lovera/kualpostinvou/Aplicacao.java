package lovera.kualpostinvou;

import android.app.Application;

import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;

public class Aplicacao extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Facebook_Coisas faceCoisas = new Facebook_Coisas(this);
//        faceCoisas.printHashKey();
    }
}
