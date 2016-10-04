package lovera.kualpostinvou.views.redes_sociais.google;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.receivers.ReceiversNames;

public class HelperGeolocalizacao{

    public static int USUARIO_ESCOLHENDO_OPCAO = 0;
    public static int DISPOSITIVO_NAO_TEM_GPS  = 1;

    private final GoogleApiClient mGoogleApiClient;
    private final Activity activity;


    public HelperGeolocalizacao(Activity activity) {
        this.activity = activity;
        this.mGoogleApiClient = Aplicacao.getGoogleCoisas().getmGoogleApiClient();

        Aplicacao.getGoogleCoisas().setHelperGps(this);
    }

    public boolean temLastLocation(){
        if(getLocalizacao() != null){
            return true;
        }

        if (ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
        if(mLastLocation != null){
            Localizacao localizaoTemp = new Localizacao();
            localizaoTemp.setLatitude(mLastLocation.getLatitude());
            localizaoTemp.setLongitude(mLastLocation.getLongitude());
            Aplicacao.getPessoaLogada().setLocalizacao(localizaoTemp);
            return true;
        }
        return false;
    }

    //TODO ver se tem alguma forma de desligar esse cara.
    public void popupLigarGps(final CommonsReceiver receiver){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setExpirationDuration(50000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest
                .Builder()
                .addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            Intent intent = new Intent();
                            intent.putExtra(ReceiversNames.GPSLOCALIZACAO, receiver);
                            activity.startIntentSenderForResult(null, USUARIO_ESCOLHENDO_OPCAO, intent, 0,0,0 );
//                            status.startResolutionForResult(fragment.getActivity(), USUARIO_ESCOLHENDO_OPCAO);
                        }
                        catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        try{
                            status.startResolutionForResult(activity, DISPOSITIVO_NAO_TEM_GPS);
                        }
                        catch (IntentSender.SendIntentException e) {
                        }
                        break;
                }
            }
        });
    }

    public Localizacao getLocalizacao() {
        return Aplicacao.getPessoaLogada().getLocalizacao();
    }
}
