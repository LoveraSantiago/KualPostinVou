package lovera.kualpostinvou.modelos;

import java.io.Serializable;

public class Localizacao implements Serializable{

    private Double latitude;
    private Double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void resetLocalizacao(){
        this.latitude = null;
        this.longitude = null;
    }
}
