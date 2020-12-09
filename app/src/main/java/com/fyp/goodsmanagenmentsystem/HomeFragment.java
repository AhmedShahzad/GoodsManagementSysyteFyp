package com.fyp.goodsmanagenmentsystem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;
public class HomeFragment extends Fragment {
    public DBconfig dBconfig;
    public static ArrayList<dataclassmodule> itemsdata;
    public static ArrayList<dataclassmodule> thedatalist;
    public static ArrayAdapter<dataclassmodule> adapter;
    public static ArrayAdapter<dataclassmodule> gridadapter;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    ImageView txtback;
    ConstraintLayout constraintlayout;
    SharedPreferences.Editor editor;
    View view;
    GridView gridView;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check, container, false);
        txtback=root.findViewById(R.id.txtback);
        Toolbar toolbar=root.findViewById(R.id.top);
        view=root.findViewById(R.id.view);
        constraintlayout=root.findViewById(R.id.constraintlayout);
        sharedPreferences
                =getActivity().getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor
                = sharedPreferences.edit();
        thedatalist=new ArrayList<>();
        itemsdata=new ArrayList<>();
        TextView txttitle=root.findViewById(R.id.txttitle);
        sessionManager=new SessionManager(getActivity());
        txttitle.setText("GTMS");
        gridView = root.findViewById(R.id.gridview);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), CategoriesDataActivity.class);
                    intent.putExtra("deal_title", itemsdata.get(position).getCat_name());
                    intent.putExtra("cat_id", itemsdata.get(position).getCat_id());
                    sessionManager.setSubCategory(itemsdata.get(position).getCat_id());
                    startActivity(intent);
            }
        });
        gridView.setFastScrollEnabled(true);
        ///////////////////
        // When user reopens the app
        // after applying dark/light mode
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            fillgriddata();
        }
        else
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("No Internet!");
            dialog.setCancelable(false);
            dialog.setMessage("There is no internet connection please connect to the internet to view the data.");
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
    public void fillgriddata()
    {
     StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://www.studentfyp.online/GoodsManagement/showallcategories.php", new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
             try {
                 Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                 JSONObject object=new JSONObject(response);
                 JSONArray array = object.getJSONArray("result");
                 Log.d("objext",array+"");
                 itemsdata=new ArrayList<>();
                 for(int i =0; i<array.length();i++)
                 {
                     String category_Identity = array.getJSONObject(i).optString("category_id");
                     //  int id=array.getJSONObject(i).optInt("id");
                     String category_Name = array.getJSONObject(i).optString("c_title");
                     String category_Image = array.getJSONObject(i).optString("c_desc");
                     //Log.d("catimage",category_Image);
                     itemsdata.add(new dataclassmodule(3,category_Image,category_Identity,category_Name));
                 }
                 if (getActivity() != null) {
                     gridadapter=new questionadapter(getActivity(),itemsdata);
                     gridView.setAdapter(gridadapter);
                     setGridViewHeightBasedOnChildren(gridView,3);
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }
     }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomProgressDialog.hide();
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(requireActivity());
        rQueue.add(stringRequest);
    }
    private void setGridViewHeightBasedOnChildren(GridView gridView, int noOfColumns) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // adapter is not set yet
            return;
        }

        int totalHeight; //total height to set on grid view
        int items = gridViewAdapter.getCount(); //no. of items in the grid
        int rows; //no. of rows in grid

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x;
        if( items > noOfColumns ){
            x = items/noOfColumns;

            //Check if exact no. of rows of rows are available, if not adding 1 extra row
            if(items%noOfColumns != 0) {
                rows = (int) (x + 1);
            }else {
                rows = (int) (x);
            }
            totalHeight *= rows;

            //Adding any vertical space set on grid view
            totalHeight += gridView.getVerticalSpacing() * rows;
        }

        //Setting height on grid view
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        // get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // Change Height of ListView
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight
                + (myListView.getDividerHeight() * (adapterCount)));
        myListView.setLayoutParams(params);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public void getVCF()

    {

        final String vfile = "POContactsRestore.vcf";

        Cursor phones =getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        phones.moveToFirst();
        for(int i =0;i<phones.getCount();i++)
        {
            String lookupKey =  phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);

            AssetFileDescriptor fd;
            try
            {
                fd = getActivity().getContentResolver().openAssetFileDescriptor(uri, "r");
                FileInputStream fis = fd.createInputStream();
                byte[] buf = new byte[(int) fd.getDeclaredLength()];
                fis.read(buf);
                String VCard = new String(buf);
                String path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;
                FileOutputStream mFileOutputStream = new FileOutputStream(path, true);
                mFileOutputStream.write(VCard.toString().getBytes());
                phones.moveToNext();
                Log.d("Vcard",  VCard);
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    }
}