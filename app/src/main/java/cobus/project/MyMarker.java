package cobus.project;

/**
 * Created by Cobus Viviers on 2016/03/28.
 */
public class MyMarker {
    private double lat;
    private double lng;
    private String info;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public MyMarker(double lat, double lng, String info) {

        this.lat = lat;
        this.lng = lng;
        this.info = info;
    }
}
