package com.fyp.goodsmanagenmentsystem;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
public class MyDeals extends AppCompatActivity {
    String cat_id;
    TextView nodata;
    SessionManager sessionManager;
    ArrayList<dataclassmodule> thedatalist;
    ArrayAdapter<dataclassmodule> adapter;
    DBconfig dBconfig;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deallist_data);
        sessionManager=new SessionManager(MyDeals.this);
        dBconfig=new DBconfig();
        Toolbar toolbar=findViewById(R.id.top);
        nodata=findViewById(R.id.nodata);
        TextView txttitle=findViewById(R.id.txttitle);
        TextView txtback=findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txttitle.setText("My Deals");
        cat_id=getIntent().getStringExtra("cat_id");
        listView=findViewById(R.id.categoriesdata);
        thedatalist=new ArrayList<>();
        adapter=new itemsdetailadapter(MyDeals.this,thedatalist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyDeals.this,Dealdetail.class);
                intent.putExtra("deal_Identity",thedatalist.get(position).getDeal_Identity());
                intent.putExtra("user_Identity",thedatalist.get(position).getUser_Identity());
                startActivity(intent);
            }
        });
        fillgriddata(cat_id);
    }
    public void fillgriddata(final String cat_id)
    {
        StringRequest request = new StringRequest(Request.Method.GET,dBconfig.baseurl+dBconfig.mydeals+sessionManager.getKeyUserId() , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array =new JSONArray(response);
                    thedatalist=new ArrayList<>();
                    if(array.length()>=0) {
                        CustomProgressDialog.hide();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject deals = array.getJSONObject(i).getJSONObject("deals");
                            String deal_startDate = deals.optString("start_date");
                            String deal_endDate = deals.optString("end_date");
                            Log.d("dealdate", deal_endDate);
                            String createddate = deals.optString("createddate");
                            Log.d("createddate", createddate);
                            String user_name = deals.optString("user_name");
                            Log.d("user_name", user_name);
                            String deal_tags = deals.optString("deal_tags");
                            Log.d("deal_tags", deal_tags);
                            int is_Avtive = deals.optInt("is_Avtive");
                            Log.d("is_Avtive", is_Avtive + "");
                            String category_name = deals.optString("category_name");
                            Log.d("category_name", category_name);
                            String deal_slug = deals.optString("deal_slug");
                            Log.d("deal_slug", deal_slug);
                            String deal_Desc = deals.optString("deal_desc");
                            Log.d("deal_Desc", deal_Desc);
                            String deal_Title = deals.optString("deal_title");
                            Log.d("deal_Title", deal_Title);
                            String deal_Link = deals.optString("deal_link");
                            Log.d("deal_Link", deal_Link);
                            String deal_Coupon = deals.optString("coupon_code");
                            Log.d("deal_Coupon", deal_Coupon);
                            String img_url = deals.optString("img_url");
                            Log.d("img_url", img_url);
                            double deal_actualPrice = deals.optDouble("deal_price");
                            Log.d("deal_actualPrice", deal_actualPrice + "");
                            double deal_Discount = deals.optDouble("deal_discount");
                            Log.d("deal_Discount", deal_Discount + "");
                            int deal_Identity = deals.optInt("id");
                            Log.d("deal_Identity", deal_Identity + "");
                            int user_Identity = deals.optInt("user_Identity");
                            Log.d("user_Identity", user_Identity + "");
                            user_Identity = Integer.parseInt(new SessionManager(MyDeals.this).getKeyUserId() + "");
                            JSONObject other = array.getJSONObject(i);
                            String offprice = other.optString("off_price");
                            Log.d("off", offprice);
                            int comments = other.optInt("comment");
                            int expire_days = other.optInt("expire_days");
                            int likes = other.optInt("likes");
                            int votedeal = other.optInt("votedeal");
                            int dislike = other.optInt("dislikes");
                            Log.d("likes", likes + "");
                            Log.d("comments", comments + "");
                            thedatalist.add(new dataclassmodule(deal_startDate, deal_endDate, deal_Title, deal_Desc, deal_Link, deal_Coupon, img_url,
                                    deal_actualPrice + "", deal_Discount + "", deal_Identity, user_Identity, offprice
                                    , likes, dislike, comments, expire_days, votedeal,createddate));
                        }
                        adapter = new itemsdetailadapter(MyDeals.this, thedatalist);
                        listView.setAdapter(adapter);
                    }
                    else
                    {
                        CustomProgressDialog.hide();
                    }
                }
                catch (JSONException e) {
                    CustomProgressDialog.hide();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomProgressDialog.hide();
                Toast.makeText(MyDeals.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(MyDeals.this);
        rQueue.add(request);
    }

    @Override
    protected void onResume() {
        fillgriddata("123");
        super.onResume();
    }
}

