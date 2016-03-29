package cobus.project;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        try{
        mMap = mapFragment.getMap();
        mMap.setOnMapLongClickListener(this);}catch (Exception e){Log.v("DEBUG", e.getMessage());}

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            DatabaseHandler dh = new DatabaseHandler(this);
            MyMarker[] markers = dh.getLocations();
            for (int i = 0; i < markers.length; i++) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(markers[i].getLat(), markers[i].getLng()))
                        .title(markers[i].getInfo()));
            }
        }catch (Exception e){Log.v("DEBUG", e.getMessage());}
        }


    @Override
    public void onMapLongClick(final LatLng latLng) {
      //  LayoutInflater inflater = LayoutInflater.from(context);
        final LatLng lng = latLng;
       // final View view = inflater.inflate(R.layout.location_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edtTitle = new EditText(this);
        builder.setView(edtTitle);
        builder.setCancelable(false);
        builder.setTitle("Location");
        builder.setMessage("Enter the name for your location");
        final DatabaseHandler dh = new DatabaseHandler(this);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                   final String temp = edtTitle.getText().toString();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(temp));
                    double lat = latLng.latitude;
                    double lng = latLng.longitude;
                    String info = temp;
                    dh.saveLocation(lat, lng, info);
                } catch (Exception e) {
                    Log.v("DEBUG", e.getMessage());
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
