package lovera.kualpostinvou.views.redes_sociais.google;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.redes_sociais.PessoaLogada;

public class Google_Coisas implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private boolean estouLogado;

    private final Application aplicacao;

    private final GoogleApiClient mGoogleApiClient;
    private final GoogleSignInOptions gso;

    private HelperGeolocalizacao helperGps;

    private GoogleSignInAccount acount;

    public Google_Coisas(Application aplicacao) {
        this.aplicacao = aplicacao;

        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                         .requestEmail()
                                                         .build();

        this.mGoogleApiClient = new GoogleApiClient.Builder(aplicacao)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, this.gso)
                .addApi(LocationServices.API)
                .build();
    }

    public void connect(){
        this.mGoogleApiClient.connect();
        verEstadoLogin();
    }

    public void disconnect(){
        this.mGoogleApiClient.disconnect();
    }

    private void verEstadoLogin(){
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
        if(opr.isDone()){
            onLoginFeito(opr.get());
        }
    }

    public void onLoginFeito(GoogleSignInResult result){
        if(result.isSuccess()){
            this.acount = result.getSignInAccount();
            Aplicacao.getPessoaLogada().inicializarPessoa();

            this.estouLogado = true;
            Aplicacao.getFaceCoisas().realizarLogout();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void realizarLogout(){
        if(this.estouLogado){
            Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            estouLogado = false;
                        }
                    }
            );
        }
    }

    public void getPessoaLogada(){
        if(this.estouLogado){
            Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
            pessoa.setNomeCompleto(this.acount.getDisplayName());
            pessoa.setEmail(this.acount.getEmail());
            pessoa.setUriImgPerfil(this.acount.getPhotoUrl());
        }
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public HelperGeolocalizacao getHelperGps() {
        return helperGps;
    }

    public void setHelperGps(HelperGeolocalizacao helperGps) {
        this.helperGps = helperGps;
    }

    public GoogleSignInOptions getGso() {
        return gso;
    }
}
