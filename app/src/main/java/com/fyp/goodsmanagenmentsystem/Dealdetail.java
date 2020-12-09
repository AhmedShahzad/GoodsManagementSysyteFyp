package com.fyp.goodsmanagenmentsystem;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Dealdetail extends AppCompatActivity {
    private static ViewPager mPager;
    public static ArrayList<dataclassmodule> thedatalist;
    public static ArrayAdapter<dataclassmodule> adapter;
    TextView comments,description,discountpercentage,prommocode,descriptionofdeal,disountamount,textright;
    TextView add,minus,expired;
    Button availnow;
    private static int currentPage = 0,position;
    View popupView;
    PopupWindow popupWindow;
    DBconfig dBconfig;
    SessionManager sessionManager;
    ImageView image;
    String formattedDate;
    int deal_Identity,likes,dislike,user_Identity;
    private static int NUM_PAGES = 0;
    EditText reason1;
    private static final Integer[] IMAGES= {R.drawable.hotelimages,R.drawable.hotelimagetwo,R.drawable.hotelimagethree};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealdetail);
        sessionManager=new SessionManager(Dealdetail.this);
        disountamount=findViewById(R.id.disountamount);
        availnow=findViewById(R.id.availnow);
        image=findViewById(R.id.image);
        position=getIntent().getIntExtra("position",0);
        description=findViewById(R.id.description);
        descriptionofdeal=findViewById(R.id.descriptionofdeal);
        discountpercentage=findViewById(R.id.discountpercentage);
        dBconfig=new DBconfig();
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
        description.setText(thedatalist.get(position).getDeal_title());
        descriptionofdeal.setText(thedatalist.get(position).getDescription());
        discountpercentage.setText(thedatalist.get(position).getDiscount()+" OFF");
        disountamount.setText("$ "+thedatalist.get(position).getDeal_Discount());
        txttitle.setText("Deal Details");
        user_Identity=getIntent().getIntExtra("user_Identity",0);
        deal_Identity=getIntent().getIntExtra("deal_Identity",0);
        try {
            Glide.with(Dealdetail.this)
                    .load(dBconfig.baseuurlimage+thedatalist.get(position).getDeal_Image())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(image);
        }
        catch (Exception er){
            image.setImageResource(R.drawable.ic_launcher_background);
        }
        availnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(thedatalist.get(position).getDeal_link()));
                    startActivity(browserIntent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        Log.d("username",new SessionManager(Dealdetail.this).getKeyUserId());
       // fillgriddata(deal_Identity,user_Identity);
    }
    public void showdialoge(String message,String title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Dealdetail.this);
        dialog.setTitle(title);
        dialog.setCancelable(false);
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, v.getId(),0, "Copy");
            TextView textView = (TextView) v;
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", textView.getText());
            manager.setPrimaryClip(clipData);
        }
}