package com.fyp.goodsmanagenmentsystem;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
public class NewDealFragment extends Fragment {
ImageView btnChoose;
ImageView profile_image;
CardView textcardview;
ArrayList<dataclassmodule> categorieslist;
String contentType,imageofdeal,cat_id;
    View popupView,popupViewdesc;
    PopupWindow popupWindow,popupWindowdesc;
SessionManager sessionManager;
ArrayList<String> spinnerlist;
TextView selectenddate,selectstartdate,ok,edtdesc,edttitle;
ArrayAdapter<String> adapterspinner;
Button submitdeal;
    DBconfig dBconfig;
    Spinner spinner;
    int catid;
    private  ProgressDialog progressDialog;
    private boolean isdarkmodeon;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String check,action;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private EditText edtdiscount,edtaprice,edtfinalamount,edtcoupancode,edtenddate,startdate,text,edtdealtags,edtdealtitle,edtdealdesc,edtquantity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_addnewdeal, container, false);
        profile_image=root.findViewById(R.id.profile_image);
        edtquantity=root.findViewById(R.id.edtquantity);
        TextView textView = root.findViewById(R.id.text_dashboard);
        spinnerlist=new ArrayList<>();
        sharedPreferences
                =getActivity().getSharedPreferences(
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
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Submitting Your Deal");
        progressDialog.setIcon(R.drawable.ic_error_black_24dp);
        progressDialog.setCanceledOnTouchOutside(false);
        categorieslist=new ArrayList<>();
        sessionManager=new SessionManager(getActivity());
        spinner=root.findViewById(R.id.spinner);
        ok=root.findViewById(R.id.ok);
        textcardview=root.findViewById(R.id.textcardview);
        text=root.findViewById(R.id.text);
        edtcoupancode=root.findViewById(R.id.edtcoupancode);
        edtdealtags=root.findViewById(R.id.edtdealtags);

        submitdeal=root.findViewById(R.id.submitbutton);
        Toolbar toolbar=root.findViewById(R.id.top);
        dBconfig=new DBconfig();
        TextView txttitle=root.findViewById(R.id.txttitle);
        TextView txtback=root.findViewById(R.id.txtback);
        txtback.setVisibility(View.GONE);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        TextView txtlogin=root.findViewById(R.id.txtlogin);
        if(sessionManager.isLoggedIn())
        {
            txtlogin.setText("Logout");
        }
        else
        {
            txtlogin.setText("Login");
        }
        txttitle.setText("New Deal");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        edttitle=root.findViewById(R.id.edttitle);
        edtdesc=root.findViewById(R.id.edtdescription);
        edtaprice=root.findViewById(R.id.edtactualprice);
        edtdiscount=root.findViewById(R.id.edtdiscount);
        edtfinalamount=root.findViewById(R.id.edtnewprice);

        btnChoose=root.findViewById(R.id.btnChoose);
        edttitle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                popupView = inflater.inflate(R.layout.popuptitle, null);
                //Specify the length and width through constants
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                //Make Inactive Items Outside Of PopupWindow
                boolean focusable = true;
                //Create a window with our parameters
                popupWindow = new PopupWindow(popupView, width, height, focusable);
                //Set the location of the window on the screen
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                //Initialize the elements of our window, install the handler
                if(edttitle.getText().toString().equals(""))
                {

                }
                else
                {
                    edtdealtitle.setText(edttitle.getText().toString());
                }
                edtdealtitle = (EditText) popupView.findViewById(R.id.dealtitle);
                Button cancell1 = (Button) popupView.findViewById(R.id.cancell);
                Button submit1 = (Button) popupView.findViewById(R.id.submit);
                submit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtdealtitle.getText().toString().equals(""))
                        {
                            edtdealtitle.setError("Empty");
                            edtdealtitle.requestFocus();
                        }
                        else
                            {
                        edttitle.setText(edtdealtitle.getText().toString());
                                popupWindow.dismiss();
                        }
                    }
                });
                cancell1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                          }
        });
        edtdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
                popupViewdesc = inflater.inflate(R.layout.popupdescription, null);
                //Specify the length and width through constants
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                //Make Inactive Items Outside Of PopupWindow
                boolean focusable = true;
                //Create a window with our parameters
                popupWindowdesc = new PopupWindow(popupViewdesc, width, height, focusable);
                //Set the location of the window on the screen
                popupWindowdesc.showAtLocation(v, Gravity.CENTER, 0, 0);
if(edtdesc.getText().toString().equals(""))
{

}
else
{
    edtdealdesc.setText(edtdesc.getText().toString());
    popupWindowdesc.dismiss();
}
                //Initialize the elements of our window, install the handler
                edtdealdesc = (EditText) popupViewdesc.findViewById(R.id.dealdesc);
                Button cancell1 = (Button) popupViewdesc.findViewById(R.id.cancell);
                Button submit1 = (Button) popupViewdesc.findViewById(R.id.submit);
                submit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtdealdesc.getText().toString().equals(""))
                        {
edtdealdesc.setError("Empty");
edtdealdesc.requestFocus();
                        }
                        else {
                            edtdesc.setText(edtdealdesc.getText().toString());
                            popupWindowdesc.dismiss();

                        }
                    }
                });
                cancell1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowdesc.dismiss();
                    }
                });
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action.equalsIgnoreCase("title"))
                {
                    sessionManager.setKeyDealTitle(text.getText().toString().trim());
                    edttitle.setText(text.getText().toString().trim());
                }
                else if(action.equalsIgnoreCase("desc"))
                {
                    sessionManager.setKeyDealDesc(text.getText().toString().trim());
                    edtdesc.setText(text.getText().toString().trim());
                }
                else
                {
                }textcardview.setVisibility(View.GONE);
            }
        });
        edtfinalamount.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (edtaprice.getText().toString().trim().equals("")) {
                    edtaprice.setError("Enter Actual Rate");
                    edtaprice.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter Amount First", Toast.LENGTH_SHORT).show();
                } else
                {
                    try {
                        if (!cs.equals("")) {

                            double price = Double.parseDouble(edtaprice.getText().toString());
                            double discoun = Double.parseDouble(cs.toString());
                            double newprice = (discoun / price) * 100;
                            edtdiscount.setText(100 - newprice + " % OFF");
                            if (discoun > price) {
                                edtdiscount.setError("Discounted Price cannot be greater than actual price");
                                edtdiscount.requestFocus();
                                edtdiscount.setText("");
                            }
                        } else {

                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        fillspinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_id=categorieslist.get(position).getCat_id();
                catid=categorieslist.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sessionManager.isLoggedIn()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Login First");
                    dialog.setCancelable(false);
                    dialog.setIcon(R.drawable.ic_error_black_24dp);
                    dialog.setMessage("Login your account to add deal");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
                else if(!edtdesc.getText().toString().equals("")&&!edttitle.getText().toString().equals("")
                        &&!edtaprice.getText().toString().equals("")&&!edtdiscount.getText().toString().equals("")
                        &&!cat_id.equals("")&&!edtfinalamount.getText().toString().equals("")&&!edtquantity.getText().toString().trim().equals(""))
                {
                        imageofdeal = Utility.bitmapToString(((BitmapDrawable) profile_image.getDrawable()).getBitmap());
                    if(Integer.parseInt(edtaprice.getText().toString().trim())>Integer.parseInt(edtfinalamount.getText().toString().trim()))
                    {
                        add_newdeal();
                    }
                    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Error");
                        dialog.setCancelable(false);
                        dialog.setIcon(R.drawable.ic_error_black_24dp);
                        dialog.setMessage("Actual Price should be greater than discounted price");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();
                }
                    }
                else
                {
                    if(edtquantity.getText().toString().trim().equals(""))
                    {
                        edtquantity.setError("Quantity Empty");
                        edtquantity.requestFocus();
                    }
                    if (cat_id.equals("")) {
                        Toast.makeText(getActivity(), "Choose Category", Toast.LENGTH_SHORT).show();
                        spinner.requestFocus();
                    }
                    if (edtaprice.getText().toString().trim().equals("")) {
                        edtaprice.setError("Price Empty");
                        edtaprice.requestFocus();
                    }
                    if (edtdesc.getText().toString().trim().equals("")) {
                        edtdesc.setError("Description Empty");
                        edtdesc.requestFocus();
                    }
                    if (edtdiscount.getText().toString().trim().equals("")) {
                        edtdiscount.setError("Discount Empty");
                        edtdiscount.requestFocus();
                    }
                    if (edtfinalamount.getText().toString().trim().equals("")) {
                        edtfinalamount.setError("Final Amount Empty");
                        edtfinalamount.requestFocus();
                    }
                    if (edttitle.getText().toString().trim().equals("")) {
                        edttitle.setError("Title Empty");
                        edttitle.requestFocus();
                    }
                }
                }
        });
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    //main logic or main code
                    selectImage();
                    // . write your main code to execute, It will execute if the permission is already given.
                } else {
                    requestPermission();
                }

            }
        });
    }
    private void selectImage() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    profile_image.setImageBitmap(decodedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void fillspinner()
    {
        StringRequest request = new StringRequest(Request.Method.GET, dBconfig.baseurl+dBconfig.getallcategories, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i =0; i<array.length();i++)
                    {
                        // Toast.makeText(Home.this, ""+array, Toast.LENGTH_SHORT).show();
                     //   String category_Identity = array.getJSONObject(i).optString("c_id");
                        int id=array.getJSONObject(i).optInt("c_id");
                        String category_Name = array.getJSONObject(i).optString("c_title");
                        Log.d("caname",category_Name);
                        //(String p_id,String title,String sub_catagory,String sell_price,String actual_price,String quantity,String p_pic)
                        categorieslist.add(new dataclassmodule(id,"",id+"",category_Name));
//                        allproductsdata.add(new CategoryData(p_id,title,sub_catagory,sell_price,actual_price,quantity,pic));
//                        allproductsdata.add(new CategoryData(p_id,title,sub_catagory,sell_price,actual_price,quantity,pic));
////                        Toast.makeText(Categories.this, ""+title, Toast.LENGTH_SHORT).show();
                    spinnerlist.add(category_Name);
                    }
//                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Home.this, 2);
//                    recycler_view_one.setLayoutManager(mLayoutManager);
adapterspinner=new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,spinnerlist);
                    adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   // Log.d("check",spinnerlist.get(0));
                    spinner.setAdapter(adapterspinner);

//
//                    recycler_view_one.setNestedScrollingEnabled(false);
//                    new SessionManager(Home.this).setKeyAllProducts(allproductsdata);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }
    public void add_newdeal(){
        progressDialog.show();
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.POST, dBconfig.baseurl+dBconfig.addnewdeal, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                        JSONObject object= null;
                        int status=1;
                        try {
                         //   int status = response.optInt("status");
                         //   String message = response.optString("message");
                            if(status==1) {
                                progressDialog.hide();
                                edtdesc.setText("");
                                edtdiscount.setText("");
                                edtaprice.setText("");
                                edtfinalamount.setText("");
                                edttitle.setText("");
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                dialog.setTitle("Succeffully Added");
                                dialog.setCancelable(false);
                                dialog.setIcon(R.drawable.ticksign);
                                dialog.setMessage("");
                                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();
                            }
                            if(status==0) {
                                progressDialog.hide();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                dialog.setTitle("Failed");
                                dialog.setCancelable(false);
                                dialog.setIcon(R.drawable.ic_error_black_24dp);
                                dialog.setMessage("");
                                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();
                            }
                            // handle response data
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //   pDialog.hide();
                        Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("c_id",catid+"");
                params.put("p_title",edttitle.getText().toString());
                params.put("p_desc",edtdesc.getText().toString());
                params.put("p_image",imageofdeal);
                params.put("t_price",Double.parseDouble(edtaprice.getText().toString())+"");
                params.put("d_price",Double.parseDouble(edtfinalamount.getText().toString())+"");
                params.put("quantity",edtquantity.getText().toString()+"");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsObjRequest);
    }
}
