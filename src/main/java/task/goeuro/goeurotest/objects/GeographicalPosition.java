package task.goeuro.goeurotest.objects;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author developer
 */
public class GeographicalPosition {

    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

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

}
