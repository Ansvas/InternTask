package com.example.anshul.interntask;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private TextView resutText;
    Marker marker;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Databasehelper dh;
    double setlati;
    double setlong;
//   int USER_ID=dh.USER_ID;
   Button update;
   ImageView imagee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        resutText = (TextView) findViewById(R.id.locationedit);
        update=(Button)findViewById(R.id.updatebutton);
        imagee=(ImageView)findViewById(R.id.marker);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        configureCameraIdle();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

  //              insertdata(USER_ID, setlati, setlong);
                Toast.makeText(MapsActivity.this,"data inserted successfully",Toast.LENGTH_LONG).show();

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;

                if (marker != null){
                    marker.remove();
                }

                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Marker is on desired position"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                setlati=latLng.latitude;
                setlong=latLng.longitude;

               Geocoder geocoder = new Geocoder(MapsActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            resutText.setText(locality + "  " + country);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public void insertdata(Integer USER_ID,double setlati,double setlong)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Databasehelper.COL_1L,USER_ID);
        contentValues.put(Databasehelper.COL_2L,setlati);
        contentValues.put(Databasehelper.COL_3L,setlong);

        long id= db.insert(Databasehelper.TABLE_NAMEL,null,contentValues);

        if(id==-1)
        {
            Toast.makeText(getApplicationContext(), "something goes wrong, values not inserted", Toast.LENGTH_LONG).show();
        }
    }

}
