package lovera.kualpostinvou.views.redes_sociais.google;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao;

public class HelperGeolocalizacao {

    public static int USUARIO_ESCOLHENDO_OPCAO = 0;
    public static int DISPOSITIVO_NAO_TEM_GPS  = 1;

    private final GoogleApiClient mGoogleApiClient;
    private final FragBuscaEstabGeoLocalizacao tempFragment;

    private final Localizacao localizacao;

    public HelperGeolocalizacao(FragBuscaEstabGeoLocalizacao fragment) {
        this.tempFragment = fragment;

        this.localizacao = new Localizacao();

        this.mGoogleApiClient = Google_Coisas.getGoogleCoisasUnicaInstancia().getmGoogleApiClient();
    }

    public boolean temLastLocation(){
        if (ActivityCompat.checkSelfPermission(this.tempFragment.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.tempFragment.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
        if(mLastLocation != null){
            this.localizacao.setLatitude(mLastLocation.getLatitude());
            this.localizacao.setLongitude(mLastLocation.getLongitude());
            return true;
        }
        return false;
    }

    public void connect(){
        this.mGoogleApiClient.connect();
    }

    public void disconnect(){
        this.mGoogleApiClient.disconnect();
    }

    //Importante esse cara deve ser chamado ou refatorado.
    public void onConnected(@Nullable Bundle bundle) {
        if(temLastLocation()){
            this.tempFragment.passarLocalizacao(this.localizacao);
        }
        else{
            popupLigarGps();
        }
    }

    public void passarLocalizacao(){
        this.tempFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tempFragment.passarLocalizacao(localizacao);
            }
        });
    }

    //TODO ver se tem alguma forma de desligar esse cara.
    private void popupLigarGps(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setExpirationDuration(50000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest
                                                        .Builder()
                                                        .addLocationRequest(mLocationRequest);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                Log.i("LocationSettings", status.getStatusCode() +"");
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try{
                            status.startResolutionForResult(tempFragment.getActivity(), USUARIO_ESCOLHENDO_OPCAO);
                        }
                        catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        try{
                            status.startResolutionForResult(tempFragment.getActivity(), DISPOSITIVO_NAO_TEM_GPS);
                        }
                        catch (IntentSender.SendIntentException e) {
                        }
                        break;
                }
            }
        });
    }
}
