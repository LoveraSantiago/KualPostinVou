package lovera.kualpostinvou.views.redes_sociais.facebook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lovera.kualpostinvou.Aplicacao;

public class Facebook_Coisas {

    private static Facebook_Coisas faceCoisasUnicaInstancia;

    private final Aplicacao aplicacao;
    CallbackManager callbackManager;

    public Facebook_Coisas(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        this.faceCoisasUnicaInstancia = this;

        iniciliazarSdk();
    }

    private void iniciliazarSdk(){
        FacebookSdk.sdkInitialize(this.aplicacao.getApplicationContext());
        AppEventsLogger.activateApp(this.aplicacao);

        this.callbackManager = CallbackManager.Factory.create();
    }

    public void printHashKey(){
        try{
            PackageInfo info = this.aplicacao.getPackageManager().getPackageInfo(
                    "lovera.kualpostinvou",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("SSL_KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public static Facebook_Coisas getFaceCoisasUnicaInstancia() {
        return faceCoisasUnicaInstancia;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
