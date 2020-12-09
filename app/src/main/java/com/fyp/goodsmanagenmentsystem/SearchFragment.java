package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchFragment extends Fragment {
    Toolbar toolbar;
    ArrayList<dataclassmodule> thedatalist;
    TextView nodata;
    String cat_id,texttosearch;
    DBconfig dBconfig;
    ArrayAdapter<dataclassmodule> adapter;
    SearchView searchView;
    ListView listView;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.search_fragment,container,false);
        listView=root.findViewById(R.id.searchlistview);
        thedatalist=new ArrayList<>();
        dBconfig=new DBconfig();
        Toolbar toolbar=root.findViewById(R.id.toolbarwithsearch);
        final TextView txttitle=root.findViewById(R.id.txttitle);
        nodata=root.findViewById(R.id.nodata);
        TextView txtback=root.findViewById(R.id.txtback);
        searchView=root.findViewById(R.id.searchView);
        final TextView txtcancel=root.findViewById(R.id.txtcancell);
        final TextView txtsearch=root.findViewById(R.id.txtsearch);

        txtcancel.setVisibility(View.GONE);
        txtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
                txtcancel.setVisibility(View.VISIBLE);
                txttitle.setVisibility(View.INVISIBLE);
                txtsearch.setVisibility(View.INVISIBLE);
            }
        });
        txtback.setVisibility(View.GONE);
        txtback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                txttitle.setVisibility(View.VISIBLE);
                txtcancel.setVisibility(View.GONE);
                txtsearch.setVisibility(View.VISIBLE);
            }
        });
        txttitle.setText("Search Deal");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            texttosearch=query;
            fillgriddata(texttosearch);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void fillgriddata(final String texttosearch)
    {
        //   url=dBconfig.baseurl+dBconfig.getalldeals;
        StringRequest request = new StringRequest(Request.Method.GET,dBconfig.searchdeal+texttosearch , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomProgressDialog.show(getActivity());
                try {
                    thedatalist=new ArrayList<>();
                    JSONArray array =new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject deals=array.getJSONObject(i).getJSONObject("deals");
                        String deal_startDate = deals.optString("start_date");
                        String deal_endDate = deals.optString("end_date");
                        Log.d("dealdate",deal_endDate);
                        String createddate = deals.optString("createddate");
                        Log.d("createddate",createddate);
                        String user_name = deals.optString("user_name");
                        Log.d("user_name",user_name);
                        String deal_tags = deals.optString("deal_tags");
                        Log.d("deal_tags",deal_tags);
                        int is_Avtive=deals.optInt("is_Avtive");
                        Log.d("is_Avtive",is_Avtive+"");
                        String category_name = deals.optString("category_name");
                        Log.d("category_name",category_name);
                        String deal_slug = deals.optString("deal_slug");
                        Log.d("deal_slug",deal_slug);
                        String deal_Desc = deals.optString("deal_desc");
                        Log.d("deal_Desc",deal_Desc);
                        String deal_Title = deals.optString("deal_title");
                        Log.d("deal_Title",deal_Title);
                        String deal_Link = deals.optString("deal_link");
                        Log.d("deal_Link",deal_Link);
                        String deal_Coupon = deals.optString("coupon_code");
                        Log.d("deal_Coupon",deal_Coupon);
                        String img_url = deals.optString("img_url");
                        Log.d("img_url",img_url);
                        double deal_actualPrice = deals.optDouble("deal_price");
                        Log.d("deal_actualPrice",deal_actualPrice+"");
                        double deal_Discount = deals.optDouble("deal_discount");
                        Log.d("deal_Discount",deal_Discount+"");
                        int deal_Identity = deals.optInt("id");
                        Log.d("deal_Identity",deal_Identity+"");
                        int user_Identity =deals.optInt("user_Identity");
                        Log.d("user_Identity",user_Identity+"");
                        JSONObject other=array.getJSONObject(i);
                        String offprice=other.optString("off_price");
                        int comments=other.optInt("comment");
                        int expire_days=other.optInt("expire_days");
                        int likes=other.optInt("likes");
                        int dislike=other.optInt("dislikes");
                        int votedeal=other.optInt("votedeal");
                        Log.d("likes",likes+"");
                        Log.d("comments",comments+"");

                        thedatalist.add(new dataclassmodule(deal_startDate,deal_endDate,deal_Title,deal_Desc,deal_Link,deal_Coupon,img_url,
                                deal_actualPrice+"",deal_Discount+"",deal_Identity,user_Identity,offprice
                                ,likes,dislike,comments,expire_days,votedeal,createddate));
                    }
                    adapter = new itemsdetailadapter(getActivity(), thedatalist);
                    listView.setAdapter(adapter);
                    nodata.setVisibility(View.GONE);
                    CustomProgressDialog.hide();
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
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            public HashMap<String,String> getParams()
            {
                HashMap<String,String> p=new HashMap<>();
                return p;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }
}
