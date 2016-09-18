package lovera.kualpostinvou;

import android.app.Application;

import lovera.kualpostinvou.views.redes_sociais.PessoaLogada;
import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;
import lovera.kualpostinvou.views.redes_sociais.google.Google_Coisas;

public class Aplicacao extends Application{

    private static PessoaLogada pessoaLogada;
    private static Google_Coisas googleCoisas;
    private static Facebook_Coisas faceCoisas;

    @Override
    public void onCreate() {
        super.onCreate();

        googleCoisas = new Google_Coisas(this);
        faceCoisas = new Facebook_Coisas(this);
//        faceCoisas.printHashKey();
        pessoaLogada = new PessoaLogada();
    }

    public static PessoaLogada getPessoaLogada() {
        return pessoaLogada;
    }

    public static Facebook_Coisas getFaceCoisas() {
        return faceCoisas;
    }

    public static Google_Coisas getGoogleCoisas() {
        return googleCoisas;
    }
}
