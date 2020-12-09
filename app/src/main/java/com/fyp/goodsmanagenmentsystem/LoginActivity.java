package com.fyp.goodsmanagenmentsystem;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static int APP_REQUEST_CODE = 99;
    Spinner usertype,cls,semester;
    TextView signin,chooseimage;
    LinearLayout linearLayout;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 7;
    JSONObject object;
    ArrayList<String> thelist,clslist,semesterlist;
    ArrayAdapter<String> adapter,clsadapter,semesteradaapter;
    String imagestring="";
    ImageView image;
    TextView btn_create_new,signup;
    Button cirsignupButton;
    int utype,idName;
    String classs,sem;
    EditText editname,editemail,editpass,editreg,editnumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
public static final String MY_PREFS_NAME = "MyPrefsFile";
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        clslist=new ArrayList<>();
        image=findViewById(R.id.image);
        signin=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.btn_create_new);
        editpass = findViewById(R.id.editTextPassword);
        editnumber=findViewById(R.id.editTextMobile);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(LoginActivity.this,SendnumberToVarifyActivity.class);
//                startActivity(intent);
            }
        });
        //   editcontact.setText(new SessionManager(Doctor_login.this).getVerifyingContact()+"");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editnumber.getText().toString().equals("03007382084")&&editpass.getText().toString().equals("12345"))
                {
//                    Intent intent=new Intent(LoginActivity.this,DoctorsList.class);
//                    startActivity(intent);
                }
                else {
                    registeruser(editnumber.getText().toString(), editpass.getText().toString());
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getcontactviatoken();
    }
    public void getcontactviatoken()
    {
        String url = "https://graph.accountkit.com/v1.3/me/?access_token="+new SessionManager(LoginActivity.this).getAccessToken();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);

                    JSONObject phone = object.getJSONObject("phone");

                    //  Toast.makeText(Doctor_login.this, " "+phone.getString("number"), Toast.LENGTH_SHORT).show();
                    editnumber.setText(phone.getString("number"));
                    new SessionManager(LoginActivity.this).setVerifyingContact(phone.getString("number"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(LoginActivity.this).setTitle("Exit").setMessage("All Data will be lost!").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    public void registeruser(final String contact_number, final String password)
    {
        Log.d("contact_number",contact_number);
        Log.d("password",password);
        try {
            imagestring = Utility.bitmapToString(((BitmapDrawable) image.getDrawable()).getBitmap());
        }
        catch (Exception e)
        {
        }
        CustomProgressDialog.show(LoginActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, new DBconfig().allhighlightsdeals, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("respone",response);
                    CustomProgressDialog.hide();
                    object = new JSONObject(response);
                    int success=object.getInt("success");
                    String msg=object.getString("msg");
                    if(success==1)
                    {
                                LoginActivity.this.startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                               String name =object.optString("name");
                                String image =object.optString("image");
                        int u_id=object.getInt("u_id");
                                utype=object.optInt("type");
                                SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                sessionManager.setKeyUserName(name+"");
                                Log.d("u_id",u_id+"");
                                sessionManager.setKeyUserCell(contact_number+"");
//                                sessionManager.setKeyUsertype(utype);
//                                sessionManager.setKeyUserId(u_id);
                                editor.putInt("userid",u_id);
                                editor.commit();
                                sessionManager.setLogin(true);
                                LoginActivity.this.finish();
                                Intent doctor=new Intent(LoginActivity.this,EnterAddress.class);
                                Intent pet=new Intent(LoginActivity.this,EnterAddress.class);
                                doctor.putExtra("my_id",u_id);
                        pet.putExtra("my_id",u_id);
                                if(utype==1)
                                {
                                    startActivity(doctor);
                                }
                                else if(utype==2)
                                {
                                    startActivity(pet);
                                }

                            }
                    else
                    {
                        new AlertDialog.Builder(LoginActivity.this).setMessage(msg).setTitle("Sorry!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomProgressDialog.hide();

                    new AlertDialog.Builder(LoginActivity.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

//                    Toast.makeText(getApplicationDoctor_login.this(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("jerror","Json error: " + e.getMessage());
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(Quiz.this, "Error: ", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(LoginActivity.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomProgressDialog.hide();
                        dialog.dismiss();
                    }
                }).show();
                Log.e("verror","Error: "+volleyError.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("password",password);
                parameters.put("contact","+92"+contact_number);
                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }
    private void selectImage() {
        try {
            PackageManager pm = LoginActivity.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, LoginActivity.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Camera", "Choose From Gallery","Cancell"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Select Action");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Camera")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,0);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, 1);
                        } else if (options[item].equals("Cancell")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(LoginActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }
    }
    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(LoginActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(
                                LoginActivity.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(LoginActivity.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = LoginActivity.this.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    image.setImageBitmap(decodedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(LoginActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}


