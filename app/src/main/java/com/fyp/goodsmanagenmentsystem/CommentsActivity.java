package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class CommentsActivity extends AppCompatActivity {
    CircleImageView btnsend;
    EditText edtsendreply;
    DBconfig dBconfig;
    int dealid;
    Toolbar toolbar;
    SessionManager sessionManager;
    ArrayList<dataclassmodule> thelist;
    ArrayAdapter<dataclassmodule> adapter;
    ListView commentslistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        dealid = getIntent().getIntExtra("dealid", 0);
        Log.d("dealid",dealid+"");
        sessionManager = new SessionManager(CommentsActivity.this);
        thelist = new ArrayList<>();
        dBconfig = new DBconfig();
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarcomments);
        TextView txttitle = findViewById(R.id.txttitle);
        TextView txtback = findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txttitle.setText("Comments");
        TextView txtlogin = findViewById(R.id.txtlogin);
        txtlogin.setVisibility(View.GONE);
//        if (sessionManager.isLoggedIn()) {
//            txtlogin.setText("Logout");
//        } else {
//            txtlogin.setText("Login");
//        }
        commentslistview = findViewById(R.id.commentslistview);
        btnsend = findViewById(R.id.btnsend);
        edtsendreply = findViewById(R.id.tvcomment);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentsActivity.this, "clciketd", Toast.LENGTH_SHORT).show();
                if (sessionManager.isLoggedIn()) {
                    postcomment();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommentsActivity.this);
                    dialog.setTitle("Login First");
                    dialog.setCancelable(false);
                    dialog.setMessage("Login to put your comment");
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
        });
        getallcoments();
    }
        public void postcomment(){
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id",32+"");
            params.put("deal_id",dealid+"");
            params.put("comment",edtsendreply.getText().toString());
            JSONObject objRegData = new JSONObject(params);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, dBconfig.baseurl+dBconfig.putacomment, objRegData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(CommentsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject object= null;
                            try {
                                int status = response.optInt("status");
                                String message = response.optString("message");
                                if(status==1) {
                                    edtsendreply.setText("");
                                    getallcoments();
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommentsActivity.this);
                                    dialog.setTitle("Successfully Added");
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
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommentsActivity.this);
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
                            Toast.makeText(CommentsActivity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            RequestQueue queue = Volley.newRequestQueue(CommentsActivity.this);
            queue.add(jsObjRequest);
        }
    public void getallcoments()
    {
        //   url=dBconfig.baseurl+dBconfig.getalldeals;
        StringRequest request = new StringRequest(Request.Method.GET,dBconfig.baseurl+dBconfig.getallcomments+0+"/"+10+"/"+dealid , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("res",response);
                    JSONArray array =new JSONArray(response);
                    thelist=new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject deals=array.getJSONObject(i);
                        int user_id = deals.optInt("user_id");
                        int deal_id = deals.optInt("deal_id");
                        Log.d("dealdate",""+deal_id+user_id);
                        String comment = deals.optString("comment");
                        Log.d("createddate",comment);
                        String created_at = deals.optString("created_at");
                        Log.d("user_name",created_at);
                        String updated_at = deals.optString("updated_at");
                        Log.d("deal_tags",updated_at);
                        String deal_title = deals.optString("deal_title");
                        Log.d("category_name",deal_title);
                        String name = deals.optString("name");
                        Log.d("deal_slug",name);
                        thelist.add(new dataclassmodule("",comment , created_at, name));
                    }
                    adapter = new commentsadpater(CommentsActivity.this, thelist);
                    commentslistview.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CommentsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            public HashMap<String,String> getParams()
            {
                HashMap<String,String> p=new HashMap<>();
                return p;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(CommentsActivity.this);
        rQueue.add(request);
    }
}