package com.fyp.goodsmanagenmentsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class EditDealDetails extends AppCompatActivity {
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
    int catid,pos;
    public static ArrayList<dataclassmodule> thedatalist;
    ProgressDialog progressDialog;
    int syear,smonth,sday,eyear,emonth,eday;
    String check,action;
    EditText edtdiscount,edtaprice,edtfinalamount,edtlink,edtcoupancode,edtenddate,startdate,text,edtdealtags,edtdealtitle,edtdealdesc;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String title,desc,coupancode,enddate,sdate,dealimage,link,discount,aprice,offprice;
    int deal_Identity,user_Identity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal_details);
        desc=getIntent().getStringExtra("desc");
        link=getIntent().getStringExtra("link");
        title=getIntent().getStringExtra("title");
        offprice=getIntent().getStringExtra("offprice");
        discount=getIntent().getStringExtra("discount");
        coupancode=getIntent().getStringExtra("coupancode");
        aprice=getIntent().getStringExtra("aprice");
        enddate=getIntent().getStringExtra("enddate");
        sdate=getIntent().getStringExtra("sdate");
        dealimage=getIntent().getStringExtra("dealimage");
        deal_Identity=getIntent().getIntExtra("deal_Identity",0);
        user_Identity=getIntent().getIntExtra("user_Identity",0);
        pos=getIntent().getIntExtra("position",0);
        TextView textView = findViewById(R.id.text_dashboard);
        spinnerlist=new ArrayList<>();
        progressDialog=new ProgressDialog(EditDealDetails.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Submitting Your Deal");
        progressDialog.setIcon(R.drawable.ic_error_black_24dp);
        progressDialog.setCanceledOnTouchOutside(false);
        categorieslist=new ArrayList<>();
        sessionManager=new SessionManager(EditDealDetails.this);
        spinner=findViewById(R.id.spinner);
        ok=findViewById(R.id.ok);
        textcardview=findViewById(R.id.textcardview);
        text=findViewById(R.id.text);
        edtcoupancode=findViewById(R.id.edtcoupancode);
        edtdealtags=findViewById(R.id.edtdealtags);
        submitdeal=findViewById(R.id.submitbutton);
        Toolbar toolbar=findViewById(R.id.top);
        dBconfig=new DBconfig();
        TextView txttitle=findViewById(R.id.txttitle);
        TextView txtback=findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditDealDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
        TextView txtlogin=findViewById(R.id.txtlogin);
        txtlogin.setVisibility(View.GONE);


        txttitle.setText("Edit Deal");
        edttitle=findViewById(R.id.edttitle);
        edtdesc=findViewById(R.id.edtdescription);
        edtaprice=findViewById(R.id.edtactualprice);
        edtenddate=findViewById(R.id.edtenddate);
        edtdiscount=findViewById(R.id.edtdiscount);
        startdate=findViewById(R.id.edtstartdate);
        edtfinalamount=findViewById(R.id.edtnewprice);
        edtlink=findViewById(R.id.edtlink);
        selectstartdate=findViewById(R.id.selectstartdate);
        selectenddate=findViewById(R.id.selectenddate);
        profile_image=findViewById(R.id.profile_image);
        try {
            Glide.with(EditDealDetails.this)
                    .load(dBconfig.baseuurlimage+dealimage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(profile_image);
        }
        catch (Exception er){
            profile_image.setImageResource(R.drawable.ic_launcher_background);
        }
        edttitle.setText(title);
        edtdesc.setText(desc);
        edtaprice.setText(aprice+"");
        edtenddate.setText(enddate);
        startdate.setText(sdate);
        edtdiscount.setText(offprice+"");
        edtfinalamount.setText(discount+"");
        edtlink.setText(link);
        edtcoupancode.setText(coupancode);
        btnChoose=findViewById(R.id.btnChoose);
        edttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
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
                        if(edtdealtitle.getText().toString().equals(""))
                        {

                        }
                        else {
                            edtdesc.setText(edtdealdesc.getText().toString());

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
        selectenddate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                check="end";
                Calendar c=null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    c = Calendar.getInstance();
                }
                eyear= c.get(Calendar.YEAR);
                emonth = c.get(Calendar.MONTH);
                eday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDealDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtenddate.setText(year+ "-" +(monthOfYear + 1)  + "-" + dayOfMonth);

                            }
                        }, eyear, emonth, eday);
                datePickerDialog.show();
            }
        });
        selectstartdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                check="start";
                Calendar c1 = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    c1 = Calendar.getInstance();
                }
                syear= c1.get(Calendar.YEAR);
                smonth = c1.get(Calendar.MONTH);
                sday = c1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDealDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startdate.setText(year+ "-" +(monthOfYear + 1)  + "-" + dayOfMonth);
                            }
                        }, syear, smonth, sday);
                datePickerDialog.show();
            }
        });
        edtfinalamount.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (edtaprice.getText().toString().trim().equals("")) {
                    edtaprice.setError("Enter Actual Rate");
                    edtaprice.requestFocus();
                    Toast.makeText(EditDealDetails.this, "Please Enter Amount First", Toast.LENGTH_SHORT).show();
                } else
                {
                    try {
                        if (!cs.equals("")) {

                            double price = Double.parseDouble(edtaprice.getText().toString());
                            double discoun = Double.parseDouble(cs.toString());
                            double newprice = (discoun / price) * 100;
                            edtdiscount.setText(100 - newprice + " %");
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditDealDetails.this);
                    dialog.setTitle("Login First");
                    dialog.setCancelable(false);
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
                        &&!edtenddate.getText().toString().equals("")&&!edtlink.getText().toString().equals("")
                        &&!edtdealtags.getText().toString().equals("")&&!edtcoupancode.getText().toString().equals("")
                        &&!cat_id.equals("")&&!startdate.getText().toString().equals("")&&!edtfinalamount.getText().toString().equals(""))
                {
                    imageofdeal = Utility.bitmapToString(((BitmapDrawable) profile_image.getDrawable()).getBitmap());
                    if(Integer.parseInt(edtaprice.getText().toString().trim())>Integer.parseInt(edtaprice.getText().toString().trim()))
                    {
                        regsiter_customer();
                    }
                    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(EditDealDetails.this);
                        dialog.setTitle("Error");
                        dialog.setCancelable(false);
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
                    if (cat_id.equals("")) {
                        Toast.makeText(EditDealDetails.this, "Choose Category", Toast.LENGTH_SHORT).show();
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
                    if (edtcoupancode.getText().toString().trim().equals("")) {
                        edtcoupancode.setError("Coupan Coud Empty");
                        edtcoupancode.requestFocus();
                    }
                    if (edtdealtags.getText().toString().trim().equals("")) {
                        edtdealtags.setError("Deal Tags Empty");
                        edtdealtags.requestFocus();
                    }
                    if (edtenddate.getText().toString().trim().equals("")) {
                        edtenddate.setError("Endt Date Empty");
                        edtenddate.requestFocus();
                    }
                    if (edtlink.getText().toString().trim().equals("")) {
                        edtlink.setError("Link Empty");
                        edtlink.requestFocus();
                    }
                    if (startdate.getText().toString().trim().equals("")) {
                        startdate.setError("Start Date Empty");
                        startdate.requestFocus();
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
     btnChoose.setOnClickListener(new View.OnClickListener() {
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
            PackageManager pm = EditDealDetails.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, EditDealDetails.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDealDetails.this);
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
                Toast.makeText(EditDealDetails.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(EditDealDetails.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
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
                    final InputStream imageStream = EditDealDetails.this.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    profile_image.setImageBitmap(decodedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(EditDealDetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(EditDealDetails.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(EditDealDetails.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(EditDealDetails.this,
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
                        if (ContextCompat.checkSelfPermission(EditDealDetails.this, Manifest.permission.CAMERA)
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
        new AlertDialog.Builder(EditDealDetails.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void fillspinner()
    {
        StringRequest request = new StringRequest(Request.Method.GET, "https://www.khmerbargain.com/api/get-categories/"+1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for(int i =0; i<array.length();i++)
                    {
                        // Toast.makeText(Home.this, ""+array, Toast.LENGTH_SHORT).show();
                        String category_Identity = array.getJSONObject(i).optString("category_id");
                        int id=array.getJSONObject(i).optInt("id");
                        String category_Name = array.getJSONObject(i).optString("category_name");
                        String category_Image = array.getJSONObject(i).optString("category_image");
                        Log.d("caname",category_Name);
                        //(String p_id,String title,String sub_catagory,String sell_price,String actual_price,String quantity,String p_pic)
                        categorieslist.add(new dataclassmodule(id,category_Image,category_Identity,category_Name));
                        Log.d("catid",category_Identity);
//                        allproductsdata.add(new CategoryData(p_id,title,sub_catagory,sell_price,actual_price,quantity,pic));
//                        allproductsdata.add(new CategoryData(p_id,title,sub_catagory,sell_price,actual_price,quantity,pic));
////                        Toast.makeText(Categories.this, ""+title, Toast.LENGTH_SHORT).show();
                        spinnerlist.add(category_Name);
                    }
//                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Home.this, 2);
//                    recycler_view_one.setLayoutManager(mLayoutManager);
                    adapterspinner=new ArrayAdapter<>(EditDealDetails.this,R.layout.support_simple_spinner_dropdown_item,spinnerlist);
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

                Toast.makeText(EditDealDetails.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(EditDealDetails.this);
        rQueue.add(request);
    }
    public void regsiter_customer(){
        progressDialog.show();
        Log.d("category_id",cat_id+"");
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",sessionManager.getKeyUserId()+"");
        params.put("category_id",catid+"");
        params.put("deal_title",edttitle.getText().toString());
        params.put("deal_desc",edtdesc.getText().toString());
        params.put("image_string",imageofdeal);
        params.put("deal_link",edtlink.getText().toString());
        params.put("coupon_code",edtcoupancode.getText().toString());
        params.put("start_date",startdate.getText().toString());
        params.put("end_date", edtenddate.getText().toString());
        params.put("deal_price",Double.parseDouble(edtaprice.getText().toString())+"");
        params.put("deal_discount",Double.parseDouble(edtfinalamount.getText().toString())+"");
        params.put("is_Avtive", ""+1);
        params.put("deal_tags",edtdealtags.getText().toString());
        JSONObject objRegData = new JSONObject(params);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, dBconfig.baseurl+dBconfig.addnewdeal, objRegData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EditDealDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                        JSONObject object= null;
                        try {
                            int status = response.optInt("status");
                            String message = response.optString("message");
                            if(status==1) {
                                progressDialog.hide();
                                edtaprice.setText("");
                                edtcoupancode.setText("");
                                edtdealtags.setText("");
                                edtdesc.setText("");
                                edtdiscount.setText("");
                                edtenddate.setText("");
                                edtaprice.setText("");
                                edtfinalamount.setText("");
                                edtlink.setText("");
                                edttitle.setText("");
                                AlertDialog.Builder dialog = new AlertDialog.Builder(EditDealDetails.this);
                                dialog.setTitle("Succeffully Added");
                                dialog.setCancelable(false);
                                dialog.setMessage(message);
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
                                AlertDialog.Builder dialog = new AlertDialog.Builder(EditDealDetails.this);
                                dialog.setTitle("Failed");
                                dialog.setCancelable(false);
                                dialog.setMessage(message);
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
                        Toast.makeText(EditDealDetails.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(EditDealDetails.this);
        queue.add(jsObjRequest);
    }
    private void Retrofit(){
        Toast.makeText(EditDealDetails.this, "Called", Toast.LENGTH_SHORT).show();
        Retrofit retrofitRequest = new Retrofit.Builder()
                .baseUrl(IHttpRequest.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // set data to send
        HashMap<String,String> SendData =new HashMap<>();
        SendData.put("user_id", sessionManager.getKeyUserId()+"");
        SendData.put("category_id", cat_id+"");
        SendData.put("deal_tags",edtdealtags.getText().toString());
        SendData.put("start_date","2020-09-11");
        SendData.put("end_date","2020-09-12");
        SendData.put("is_Avtive", ""+1);
        SendData.put("deal_title",edttitle.getText().toString());
        SendData.put("deal_desc", edtdesc.getText().toString());
        SendData.put("coupon_code",edtcoupancode.getText().toString() );
        SendData.put("deal_link",edtlink.getText().toString());
        SendData.put("image_string", "DFJLSDJ");
        SendData.put("deal_price",""+Double.parseDouble(edtaprice.getText().toString()));
        SendData.put("deal_discount", ""+Double.parseDouble(edtfinalamount.getText().toString()));
        final IHttpRequest request=retrofitRequest.create(IHttpRequest.class);
        request.register(SendData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditDealDetails.this, "done", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(EditDealDetails.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
