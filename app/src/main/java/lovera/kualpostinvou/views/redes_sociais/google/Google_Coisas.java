package lovera.kualpostinvou.views.redes_sociais.google;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class Google_Coisas implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static Google_Coisas googleCoisasUnicaInstancia;

    private Application aplicacao;

    private final GoogleApiClient mGoogleApiClient;

    private HelperGeolocalizacao helperGps;

    public Google_Coisas(Application aplicacao) {
        this.aplicacao = aplicacao;

        this.mGoogleApiClient = new GoogleApiClient.Builder(aplicacao)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleCoisasUnicaInstancia = this;
    }

    public void connect(){
        this.mGoogleApiClient.connect();
    }

    public void disconnect(){
        this.mGoogleApiClient.disconnect();
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

    public static Google_Coisas getGoogleCoisasUnicaInstancia() {
        return googleCoisasUnicaInstancia;
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
}
