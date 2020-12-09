package com.fyp.goodsmanagenmentsystem;
import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.goodsmanagenmentsystem.CategoriesDataActivity;
import com.fyp.goodsmanagenmentsystem.CustomProgressDialog;
import com.fyp.goodsmanagenmentsystem.DBconfig;
import com.fyp.goodsmanagenmentsystem.MainActivity;
import com.fyp.goodsmanagenmentsystem.R;
import com.fyp.goodsmanagenmentsystem.SessionManager;
import com.fyp.goodsmanagenmentsystem.dataclassmodule;
import com.fyp.goodsmanagenmentsystem.itemsdetailadapter;
import com.fyp.goodsmanagenmentsystem.nitificationadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsFragment extends Fragment {
    ArrayList<dataclassmodule> thedata;
    ArrayAdapter<dataclassmodule> adapter;
    SessionManager sessionManager;
    ListView notificationlistview;
    TextView nodata;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notification_fragment, container, false);
        notificationlistview = root.findViewById(R.id.notificationlsitview);
        Toolbar toolbar=(Toolbar)root.findViewById(R.id.top);
        sessionManager=new SessionManager(getActivity());
        TextView txttitle=root.findViewById(R.id.txttitle);
        nodata=root.findViewById(R.id.nodata);
        TextView txtback=root.findViewById(R.id.txtback);
        txtback.setVisibility(View.GONE);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        txttitle.setText("Notifications");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        notificationlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),CommentsActivity.class);
                intent.putExtra("dealid",thedata.get(position).getDeal_Identity());
                startActivity(intent);
            }
        });
        if(sessionManager.isLoggedIn())
        {
getnotifications();

            nodata.setVisibility(View.GONE);
        }
        else
        {
nodata.setVisibility(View.VISIBLE);
        }
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    public void getnotifications()
    {
            //   url=dBconfig.baseurl+dBconfig.getalldeals;
            StringRequest request = new StringRequest(Request.Method.GET,new DBconfig().baseurl+new DBconfig().getnotifications+new SessionManager(getActivity()).getKeyUserId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONArray array =new JSONArray(response);
                        thedata=new ArrayList<>();
                        if(array.length()>=0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject deals = array.getJSONObject(i);
                                String notification_body = deals.optString("notification_body");
                                String updated_at = deals.optString("updated_at");
                                String deal_url = deals.optString("deal_url");
                                Log.d("deal_url", deal_url);
                                thedata.add(new dataclassmodule(notification_body, updated_at, deal_url));
                            }
                            CustomProgressDialog.hide();
                            adapter=new nitificationadapter(getActivity(),thedata);
                            notificationlistview.setAdapter(adapter);
                            nodata.setVisibility(View.GONE);
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
