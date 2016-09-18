package lovera.kualpostinvou.views.redes_sociais.facebook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Pessoa;

public class Facebook_Coisas {

    private final Aplicacao aplicacao;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    public Facebook_Coisas(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        inicializarSdk();
    }

    private void inicializarSdk(){
        FacebookSdk.sdkInitialize(this.aplicacao.getApplicationContext());
        AppEventsLogger.activateApp(this.aplicacao);

        this.callbackManager = CallbackManager.Factory.create();
        inicializarAccessTokenTracker();
    }

    private void inicializarAccessTokenTracker(){
        this.accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    onLogoutFeito();
                }
            }
        };
        this.accessTokenTracker.startTracking();
    }

    public void onDestroy(){
        this.accessTokenTracker.stopTracking();
    }

    public void onLoginFeito(){
        Aplicacao.getPessoaLogada().inicializarPessoa();
    }

    public void onLogoutFeito(){
        Aplicacao.getPessoaLogada().inicializarPessoa();
    }

    public void getPessoaLogada(){
        Profile profile = Profile.getCurrentProfile();

        if(profile != null){
            Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
            Log.i("FacebookCoisas", "entrou");
            pessoa.setNomeCompleto(profile.getName());
            pessoa.setUriImgPerfil(profile.getProfilePictureUri(150, 150));
            pessoa.setEmail("apegar@apegar");
        }
        else{
//            Aplicacao.getPessoaLogada().resetPessoa();
            Log.i("FacebookCoisas", "nao entrou");
        }
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
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
}
