package lovera.kualpostinvou.views.redes_sociais.facebook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Pessoa;

public class Facebook_Coisas {

    private boolean estouLogado;

    private final Aplicacao aplicacao;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private String campoEmail;

    public Facebook_Coisas(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        inicializarSdk();
    }

    private void inicializarSdk(){
        FacebookSdk.sdkInitialize(this.aplicacao.getApplicationContext());
        AppEventsLogger.activateApp(this.aplicacao);

        this.callbackManager = CallbackManager.Factory.create();
        inicializarAccessTokenTracker();
        inicializarProfileTracker();
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

    private void inicializarProfileTracker(){
        new ProfileTracker(){
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                inicializarCamposExtras();
                this.stopTracking();
            }
        };
    }

    private void inicializarCamposExtras(){
        final GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object.has("email")) {
                    try {
                        campoEmail = object.getString("email");
                        estouLogado = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        request.setParameters(gerarBundleParamsCamposExtras());
        request.executeAsync();
    }

    public void onDestroy(){
        this.accessTokenTracker.stopTracking();
    }

    public void onLoginFeito(){
        final GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object.has("email")) {
                    try {
                        campoEmail = object.getString("email");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Aplicacao.getPessoaLogada().inicializarPessoa();
            }
        });
        request.setParameters(gerarBundleParamsCamposExtras());
        request.executeAsync();
        this.estouLogado = true;

        Aplicacao.getGoogleCoisas().realizarLogout();
    }

    private Bundle gerarBundleParamsCamposExtras(){
        Bundle params = new Bundle();
        params.putString("fields", "email");
        return params;
    }

    public void realizarLogout(){
        if(this.estouLogado){
            LoginManager.getInstance().logOut();
            this.estouLogado = false;
        }
    }

    public void onLogoutFeito(){
        Aplicacao.getPessoaLogada().inicializarPessoa();
    }

    public void getPessoaLogada(){
        if(this.estouLogado){
            Profile profile = Profile.getCurrentProfile();
            if(profile != null){
                Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
                pessoa.setNomeUsuario(profile.getName());
                pessoa.setNomeCompleto(profile.getName());
                pessoa.setUriImgPerfil(profile.getProfilePictureUri(160, 160));
                pessoa.setTokenFacebook(profile.getId());
                pessoa.setEmail(this.campoEmail);
            }
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
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
