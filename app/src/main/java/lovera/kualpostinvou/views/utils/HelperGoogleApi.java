package lovera.kualpostinvou.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import lovera.kualpostinvou.MainActivity;
import lovera.kualpostinvou.modelos.Localizacao;

public class HelperGoogleApi implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final GoogleApiClient mGoogleApiClient;
    private final MainActivity activity;

    private final Localizacao localizacao;

    public HelperGoogleApi(MainActivity activity) {
        this.activity = activity;

        this.localizacao = new Localizacao();

        this.mGoogleApiClient = new GoogleApiClient.Builder(this.activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean temLastLocation(){
        if (ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    //TODO ver se tem alguma forma de desligar esse cara.
    private void forceLocationRequest(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setExpirationDuration(50000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        tente5Vezes();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            status.startResolutionForResult(activity, activity.RESULT_FROM_HELPERGOOGLE);
                        }
                        catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private boolean tente5Vezes(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                temLastLocation();
            }
        };

        for(int i = 0; i < 5; i++){
            if(!temLastLocation()){
                handler.postDelayed(runnable, 1000);
            }
            else return true;
        }
        return false;
    }

    public Localizacao getLocalizacao() throws Exception {
        if(temLastLocation()) return this.localizacao;

        forceLocationRequest();
        if(temLastLocation()) return this.localizacao;
        throw new Exception();
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
}
