package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class ProfileActivity extends AppCompatActivity {
    SessionManager sessionManager;
    LinearLayout linearlayout1;
    ImageView profile_image;
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView txtloginmethode,txtusername,txtcontact,btnupdateprofile,edtname,txtemail,txtmembersince;
    ImageView addimage;
    SharedPreferences.Editor editor;
    boolean isdarkmodeon;
    SharedPreferences sharedPreferences;
    EditText edtemail,edtcountry;
    DBconfig dBconfig;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtemail=findViewById(R.id.txtemail);
        txtusername=findViewById(R.id.txtusername);
        profile_image=findViewById(R.id.profile_image);
        sharedPreferences
                =getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor
                = sharedPreferences.edit();
        isdarkmodeon
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);
        if(isdarkmodeon)
        {
            profile_image.setImageResource(R.drawable.pickimagedark);
        }
        else
        {
            profile_image.setImageResource(R.drawable.pickimage);
        }
        txtemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtemail.isEnabled())
                {
                    edtemail.setEnabled(true);
                }
            }
        });
        edtname=findViewById(R.id.edtname);
        sessionManager=new SessionManager(ProfileActivity.this);
        btnupdateprofile=findViewById(R.id.submitbutton);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
            }
        });
        txtusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtname.setEnabled(true);
            }
        });
        edtemail=findViewById(R.id.edtemail);
        edtemail.setText(sessionManager.getKeyUserAddress());
        txtcontact=findViewById(R.id.edtphone);
        dBconfig=new DBconfig();
        linearlayout1=findViewById(R.id.linearlayout1);
        sessionManager=new SessionManager(ProfileActivity.this);
        Toolbar toolbar=findViewById(R.id.top);
        setSupportActionBar(toolbar);
        TextView txttitle=findViewById(R.id.txttitle);
        TextView txtback=findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
                      }
        });
        txttitle.setText("User Profile");
        TextView txtlogin=findViewById(R.id.txtlogin);
        if(sessionManager.isLoggedIn())
        {
            txtlogin.setText("Logout");
            linearlayout1.setVisibility(View.VISIBLE);
        }
        else
        {
            linearlayout1.setVisibility(View.GONE);
            txtlogin.setText("Login");
        }

        txtcontact.setText(sessionManager.getKeyUserCell());
btnupdateprofile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getprofile();
    }
});
    }
    public void getprofile(){
        CustomProgressDialog.show(ProfileActivity.this);
        String imagefinal;
        try {
            imagefinal = Utility.bitmapToString(((BitmapDrawable) profile_image.getDrawable()).getBitmap());
        }
        catch (Exception e)
        {
            imagefinal="abc";
            e.printStackTrace();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("address","Pakistan");
        params.put("email",edtemail.getText().toString());
        params.put("name",edtname.getText().toString());
        params.put("mobile_number",txtcontact.getText().toString());
        params.put("image_string",imagefinal);
        params.put("user_id",sessionManager.getKeyUserId()+"");
        JSONObject objRegData = new JSONObject(params);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, dBconfig.baseurl+dBconfig.save_profile, objRegData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("verify",response+"");
                        JSONObject object= null;
                        CustomProgressDialog.hide();
                        try {
                            int status = response.optInt("status");
                            String message = response.optString("message");
                            JSONObject user=response.getJSONObject("user");
                            int user_id=user.optInt("id");
                            String name = user.optString("name");
                            String email = user.optString("email");
                            String mobile_number = user.optString("mobile_number");
                            String profile_picture = user.optString("profile_picture");
                            Log.d("profile_picture",profile_picture+"");
                            String country = user.optString("country");
                            if(status==1) {
                                sessionManager.setKeyUserCell(mobile_number);
                                sessionManager.setKeyUserId(user_id+"");
                                sessionManager.setKeyUserName(name);
                                sessionManager.setKeyUserAddress(email);
                                sessionManager.setProfileimage(profile_picture);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                                dialog.setTitle("Profile Updated!");
                                dialog.setCancelable(false);
                                dialog.setIcon(R.drawable.ticksign);
                                dialog.setMessage(message);
                                dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();
                            }
                            if(status==0) {
                                CustomProgressDialog.hide();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                                dialog.setTitle("Updation Failed!");
                                dialog.setCancelable(false);
                                dialog.setIcon(R.drawable.ic_error_black_24dp);
                                dialog.setMessage(message);
                                dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            CustomProgressDialog.hide();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomProgressDialog.hide();
                        //   pDialog.hide();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        queue.add(jsObjRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(bitmap);
            // img1.setImageBitmap(bitmap);
        }
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = ProfileActivity.this.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    profile_image.setImageBitmap(decodedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(ProfileActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void selectImage() {
        try {
            PackageManager pm = ProfileActivity.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, ProfileActivity.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,0);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, 1);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(ProfileActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(ProfileActivity.this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ProfileActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }
//  CustomProgressDialog.show(getActivity());

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ProfileActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
