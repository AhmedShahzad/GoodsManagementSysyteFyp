package com.fyp.goodsmanagenmentsystem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;
public class EnterAddress extends AppCompatActivity implements OnMapReadyCallback {
    TextView txttitle,txtback;
    String name,pass,number,usertype,image="abc",petinfo;
    boolean result = true;
    LocationManager lm;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    double latitude,longitude;
    EditText edtarea,edtcity,edtcountry;
    private SessionManager sessionManager;
    private GoogleMap mMap;
    int status;
    int utype;
    String address;
    boolean flag=false;
    Button btnnext;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);
        petinfo=getIntent().getStringExtra("petinfo");
        pass=getIntent().getStringExtra("pass");
        image=getIntent().getStringExtra("image");
        if(petinfo.equals("no"))
        {
            status=0;
        }
        else
        {
            status=1;
        }
        number=getIntent().getStringExtra("number");
        name=getIntent().getStringExtra("name");
        Log.d("image",image);
        Log.d("pass",pass);
        Log.d("number",number);
        Log.d("name",name);
        utype=getIntent().getIntExtra("usertype",0);
        sessionManager=new SessionManager(EnterAddress.this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.top);
        setSupportActionBar(myToolbar);
        txttitle=findViewById(R.id.txttitle);
        txttitle.setText("Select Address");
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtarea=findViewById(R.id.edtarea);
        edtcity=findViewById(R.id.edtcity);
        edtcountry=findViewById(R.id.edtcountry);
        btnnext=findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtcity.getText().toString().trim().equals(""))
                {
                    edtcity.setError("Enter City");
                    edtcity.requestFocus();
                }

                else if (edtcountry.getText().toString().trim().equals(""))
                {
                    edtcountry.setError("Enter Country");
                    edtcountry.requestFocus();
                }
                else if(edtarea.getText().toString().trim().equals(""))
                {
                    edtarea.setError("Entry Area");
                    edtarea.requestFocus();
                }
                else
                {
                   registeruser(name,number,pass);
                }
            }
        });
        if (!checkPermission()) {
            showSettingsAlert();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(EnterAddress.this);
        }
        setUpMapIfNeeded();
        ////////////////////
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(!flag) {
                    flag = true;
                    LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions marker = new MarkerOptions()
                            .title("Hello")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .position(sydney1);
                    mMap.addMarker(marker);
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));
                    getCompleteAddressString(location.getLatitude(),location.getLongitude());
                }
            }
        });
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mMap.clear();
//                getCompleteAddressString(latLng.latitude,latLng.longitude);
//                latitude=latLng.latitude;
//                longitude=latLng.longitude;
//                LatLng pickedlocation = new LatLng(latitude, longitude);
//                mMap.addMarker(new MarkerOptions().position(pickedlocation).title("Salon Location")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.current));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
//                mMap.getUiSettings().setZoomControlsEnabled(true);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(pickedlocation));
//            }
//        });
    }
    @SuppressLint("LongLogTag")
    private void getCompleteAddressString(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        Log.e("latitude", "latitude--" + latitude);
        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                edtcity.setText(city);
                edtcountry.setText(country);
                edtarea.setText(knownName);
//                sessionManager.setKeyCountry(edtcountry.getText().toString().trim());
//                sessionManager.setKeyCity(edtcity.getText().toString().trim());
//                sessionManager.setKeyArea(edtarea.getText().toString().trim());
//                sessionManager.setKeyUpdateaddress("Yes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EnterAddress.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnterAddress.this);
        // Setting Dialog Title
        alertDialog.setTitle("Permission Required");
//        alertDialog.setIcon(R.drawable.ic_location_on_black_24dp);
        // Setting Dialog Message
        alertDialog.setMessage("Application Needs Location Permission");
        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.button_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        alertDialog.show();
    }
    public boolean canGetLocation() {

        if (lm == null)

            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        try {
            network_enabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {
        }
        if (!gps_enabled || !network_enabled) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MyMap", "onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MyMap", "onResume");
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            Log.d("MyMap", "setUpMapIfNeeded");
            ((SupportMapFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map)))
                    .getMapAsync(this);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void registeruser(final String name, final String contact_number, final String password)
    {
        CustomProgressDialog.show(EnterAddress.this);
        StringRequest request = new StringRequest(Request.Method.POST, new DBconfig().baseurl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("respone",response);
                    CustomProgressDialog.hide();
                    JSONObject data = new JSONObject(response);
                    int success=data.getInt("success");
                    String msg=data.getString("msg");
                    if(success==1)
                    {
                        new AlertDialog.Builder(EnterAddress.this).setMessage("Your Account Is Registered").setTitle("Successfully Registered!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EnterAddress.this.startActivity(new Intent(EnterAddress.this, LoginActivity.class));
                                dialog.dismiss();
                                SessionManager sessionManager = new SessionManager(EnterAddress.this);
                                sessionManager.setKeyUserName(name+"");
                                sessionManager.setKeyUserCell(contact_number+"");
                                sessionManager.setKeyUserPassword(password+"");
                                sessionManager.setLogin(true);
                                EnterAddress.this.finish();
                            }
                        }).show();
                    }
                    else
                    {
                        new AlertDialog.Builder(EnterAddress.this).setMessage(msg).setTitle("Sorry!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomProgressDialog.hide();

                    new AlertDialog.Builder(EnterAddress.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

//                    Toast.makeText(getApplicationEnterAddress.this(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("jerror","Json error: " + e.getMessage());
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(Quiz.this, "Error: ", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(EnterAddress.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomProgressDialog.hide();
                        dialog.dismiss();
                    }
                }).show();
                Log.d("verror","Error: "+volleyError.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("name",name);
                parameters.put("password",password);
                parameters.put("contact",contact_number);
                parameters.put("image",image);
                parameters.put("u_type",utype+"");
                parameters.put("petinfo",petinfo);
                parameters.put("status",""+status);
                parameters.put("address",edtarea.getText().toString()+","+edtcity.getText().toString()+""+edtcountry.getText().toString());
                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(EnterAddress.this);
        rQueue.add(request);
    }
}

