package lovera.kualpostinvou;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import lovera.kualpostinvou.views.adapters.FragBuscaEstabGeoLocalizacaoAdapter;
import lovera.kualpostinvou.views.contratos.MsgFromGpsService;
import lovera.kualpostinvou.views.redes_sociais.PessoaLogada;
import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;
import lovera.kualpostinvou.views.redes_sociais.google.Google_Coisas;

public class Aplicacao extends Application{

    private static Aplicacao instancia;

    private static PessoaLogada pessoaLogada;
    private static Google_Coisas googleCoisas;
    private static Facebook_Coisas faceCoisas;

    private static MsgFromGpsService mensageiroGps;

    @Override
    public void onCreate() {
        super.onCreate();

        googleCoisas = new Google_Coisas(this);
        faceCoisas = new Facebook_Coisas(this);
//        faceCoisas.printHashKey();
        pessoaLogada = new PessoaLogada();

        instancia = this;
    }

    public static PessoaLogada getPessoaLogada() {
        return pessoaLogada;
    }

    public String getAndroidId(){
        return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Facebook_Coisas getFaceCoisas() {
        return faceCoisas;
    }

    public static Google_Coisas getGoogleCoisas() {
        return googleCoisas;
    }

    public static Aplicacao getAplicacaoInstancia() {
        return instancia;
    }

    public static MsgFromGpsService getMensageiroGps() {
        return mensageiroGps;
    }

    public static void setMensageiroGps(MsgFromGpsService mensageiroGps) {
        Aplicacao.mensageiroGps = mensageiroGps;
    }
}
