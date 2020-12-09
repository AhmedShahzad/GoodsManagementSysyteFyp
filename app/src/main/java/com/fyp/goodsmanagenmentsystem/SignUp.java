package com.fyp.goodsmanagenmentsystem;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class SignUp extends AppCompatActivity {
    Spinner usertype;
    Button signup;
    TextView signin,chooseimage;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 7;
    ArrayList<String> thelist,clslist;
    String imagestring="",petinfo="",number="";
    ImageView image;
    int utype;
    EditText editname,editpass,editnumber,edt_petinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_signup);
        chooseimage=findViewById(R.id.chooseimage);
        number=getIntent().getStringExtra("number");
        Log.d("number",number);
        edt_petinfo=findViewById(R.id.edt_petinfo);
        clslist=new ArrayList<>();
        image=findViewById(R.id.image);
        signin=findViewById(R.id.link_login);
        signup=findViewById(R.id.btn_signup_signup_doc);
        thelist=new ArrayList<>();
        editname = findViewById(R.id.edt_name_signup_doc);
        editpass = findViewById(R.id.edt_pass_signup_doc);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_petinfo.getText().toString().trim().equals("")) {

                    if (editname.getText().toString().trim().equals("") ||
                            editpass.getText().toString().trim().equals("")) {
                        if (editname.getText().toString().trim().equals("")) {
                            editname.setError("Enter Name");
                            editname.requestFocus();
                        }
                        if (editpass.getText().toString().trim().equals("")) {
                            editpass.setError("Enter Password");
                            editpass.requestFocus();
                        }
                        if (utype == 2) {
                            if (edt_petinfo.getText().toString().trim().equals("")) {
                                edt_petinfo.setError("Enter Pet Info");
                                edt_petinfo.requestFocus();
                            } else {
                            }
                        }
                    } else {
                        if(editpass.getText().toString().length()<8)
                        {
                            editpass.setError("Invalid Password");
                            editpass.requestFocus();
                        }
                        else {
                            try {
                                imagestring = Utility.bitmapToString(((BitmapDrawable) image.getDrawable()).getBitmap());
                                Log.d("image", imagestring);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(SignUp.this, EnterAddress.class);
                            intent.putExtra("name", editname.getText().toString());
                            intent.putExtra("pass", editpass.getText().toString());
                            intent.putExtra("number",number);
                            intent.putExtra("usertype", utype);
                            intent.putExtra("image", imagestring);
                            intent.putExtra("petinfo", imagestring);
                            startActivity(intent);
                        }
                    }
                }
                else
                {
                    petinfo=edt_petinfo.getText().toString();
                    Intent intent = new Intent(SignUp.this, EnterAddress.class);
                    intent.putExtra("name", editname.getText().toString());
                    intent.putExtra("pass", editpass.getText().toString());
                    intent.putExtra("number",number);
                    intent.putExtra("usertype", utype);
                    intent.putExtra("image", imagestring);
                    intent.putExtra("petinfo",petinfo);
                    startActivity(intent);
                  //  registeruser(editname.getText().toString(),number,editpass.getText().toString());
                   // startActivity(intent);
                }
                }
        });
        //   editcontact.setText(new SessionManager(Doc_signup.this).getVerifyingContact()+"");
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndroidVersion();
                selectImage();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        thelist.add("Select User");
        thelist.add("Doctor");
        thelist.add("Pet");
        usertype=findViewById(R.id.spinner);
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<>(SignUp.this,R.layout.support_simple_spinner_dropdown_item,thelist);
        usertype.setAdapter(adapter);
        usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Select User"))
                {
utype=0;
                    edt_petinfo.setVisibility(View.GONE);
                }
               else if(adapterView.getItemAtPosition(i).toString().equals("Doctor"))
                {
utype=1;
                    edt_petinfo.setVisibility(View.GONE);
}
               else
                {
utype=2;
                    edt_petinfo.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(SignUp.this).setTitle("Exit").setMessage("All Data will be lost!").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

    private void selectImage() {
        try {
            PackageManager pm = SignUp.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, SignUp.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Camera", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
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
                Toast.makeText(SignUp.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(SignUp.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
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
        int camera = ContextCompat.checkSelfPermission(SignUp.this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE);
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
            ActivityCompat.requestPermissions(SignUp.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
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
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(
                                SignUp.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this,
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
                            Toast.makeText(SignUp.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SignUp.this)
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
                    final InputStream imageStream = SignUp.this.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    image.setImageBitmap(decodedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(SignUp.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void registeruser(final String name, final String contact_number, final String password)
    {
        try {
            imagestring = Utility.bitmapToString(((BitmapDrawable) image.getDrawable()).getBitmap());
            Log.d("image",imagestring);
        }
        catch (Exception e)
        {
        }
        CustomProgressDialog.show(SignUp.this);
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
                        new AlertDialog.Builder(SignUp.this).setMessage("Your Account Is Registered").setTitle("Successfully Registered!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SignUp.this.startActivity(new Intent(SignUp.this, LoginActivity.class));
                                dialog.dismiss();
                                SessionManager sessionManager = new SessionManager(SignUp.this);
                                sessionManager.setKeyUserName(name+"");
                                sessionManager.setKeyUserCell(contact_number+"");
                                sessionManager.setKeyUserPassword(password+"");
                                sessionManager.setLogin(true);
                                SignUp.this.finish();
                            }
                        }).show();
                    }
                    else
                    {
                        new AlertDialog.Builder(SignUp.this).setMessage(msg).setTitle("Sorry!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomProgressDialog.hide();

                    new AlertDialog.Builder(SignUp.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

//                    Toast.makeText(getApplicationDoc_signup.this(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("jerror","Json error: " + e.getMessage());
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(Quiz.this, "Error: ", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(SignUp.this).setMessage("An error Occured! Please try again!").setTitle("Alert!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                parameters.put("name",name);
                parameters.put("password",password);
                parameters.put("contact",contact_number);
                parameters.put("image",imagestring);
                parameters.put("u_type",utype+"");
                parameters.put("petinfo",petinfo);
                parameters.put("address","");
                parameters.put("status",""+1);
                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(SignUp.this);
        rQueue.add(request);
    }
}


