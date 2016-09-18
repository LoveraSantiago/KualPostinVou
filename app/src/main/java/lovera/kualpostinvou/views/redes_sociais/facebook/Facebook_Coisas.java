package lovera.kualpostinvou.views.redes_sociais.facebook;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao;
import lovera.kualpostinvou.views.fragments.FragRedesSociais;

public class Facebook_Coisas {

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
    }

    private void inicializarAccessTokenTracker(){
        this.accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken != null){
                    inicializarCamposExtras();
                }
                if(currentAccessToken == null){
                    onLogoutFeito();
                }
            }
        };
        this.accessTokenTracker.startTracking();
    }

    private void inicializarCamposExtras(){
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
    }

    private Bundle gerarBundleParamsCamposExtras(){
        Bundle params = new Bundle();
        params.putString("fields", "email");
        return params;
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
            pessoa.setUriImgPerfil(profile.getProfilePictureUri(160, 160));
            pessoa.setEmail(this.campoEmail);
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
