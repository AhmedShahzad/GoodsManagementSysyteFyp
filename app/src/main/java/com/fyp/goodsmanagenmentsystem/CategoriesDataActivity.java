package com.fyp.goodsmanagenmentsystem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
public class CategoriesDataActivity extends AppCompatActivity {
    int cat_id;
    SessionManager sessionManager;
    ArrayList<dataclassmodule> thedatalist;
    ArrayAdapter<dataclassmodule> adapter;
    DBconfig dBconfig;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deallist_data);
        cat_id=getIntent().getIntExtra("c_id",0);
        sessionManager=new SessionManager(CategoriesDataActivity.this);
        dBconfig=new DBconfig();
        Log.d("cat",cat_id+"");
        Toolbar toolbar=findViewById(R.id.top);
        TextView txttitle=findViewById(R.id.txttitle);
        TextView txtback=findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        txttitle.setText(getIntent().getStringExtra("deal_title"));
        listView=findViewById(R.id.categoriesdata);
        Log.d("userid",sessionManager.getKeyUserId()+"");
        Log.d("username",sessionManager.getKeyUserName()+"");
        fillgriddata(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CategoriesDataActivity.this,Dealdetail.class);
                Dealdetail.thedatalist=thedatalist;
                startActivity(intent);
            }
        });
    }
    public void fillgriddata(final int cat_id)
    {
        CustomProgressDialog.show(CategoriesDataActivity.this);
     //   url=dBconfig.baseurl+dBconfig.getalldeals;
        StringRequest request = new StringRequest(Request.Method.POST,dBconfig.baseurl+dBconfig.getalldeals , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("res",response);
                    JSONObject object=new JSONObject(response);

                    JSONArray array =object.getJSONArray("result");
                    thedatalist=new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject deals=array.getJSONObject(i);
                        Log.d("deals",deals+"");
                        int c_id=deals.optInt("c_id");
                        int p_id=deals.optInt("p_id");
                        int quantity=deals.optInt("quantity");
                        String deal_Desc = deals.optString("p_desc");
                        Log.d("deal_Desc",deal_Desc);
                        String deal_Title = deals.optString("p_title");
                        Log.d("deal_Title",deal_Title);
                        String img_url = deals.optString("p_image");
                        Log.d("img_url",img_url);
                        double deal_actualPrice = deals.optDouble("deal_price");
                        Log.d("deal_actualPrice",deal_actualPrice+"");
                        double deal_Discount = deals.optDouble("deal_discount");
                        int deal_Identity = deals.optInt("p_id");

                        thedatalist.add(new dataclassmodule(deal_Title,deal_Desc,img_url,deal_actualPrice+"",deal_Discount+"",deal_Identity,deal_Discount+""));
                    }
                    CustomProgressDialog.hide();
                    adapter = new itemsdetailadapter(CategoriesDataActivity.this, thedatalist);
                    listView.setAdapter(adapter);

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
                Toast.makeText(CategoriesDataActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            public HashMap<String,String> getParams()
            {
                HashMap<String,String> p=new HashMap<>();
                p.put("c_id",""+cat_id);
                return p;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(CategoriesDataActivity.this);
        rQueue.add(request);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}