package com.fyp.goodsmanagenmentsystem;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class questionadapter extends ArrayAdapter<dataclassmodule> {
    private Context context;
    DBconfig dBconfig;
    SharedPreferences pref;
//    SessionManager sessionManager;
    private static final String PREF_NAME = "login_preference";
    int PRIVATE_MODE = 0;
    ArrayList<dataclassmodule> thedata;
    ProgressDialog progressDialog;
    public questionadapter(Context context, ArrayList<dataclassmodule> thedata)
    {
        super(context, R.layout.homeitems,thedata);
        this.context=context;
        this.thedata=thedata;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.homeitems, null);
        dBconfig=new DBconfig();
        TextView tvname = view.findViewById(R.id.description);
        tvname.setText(thedata.get(position).getCat_name());
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
              ImageView imageView=view.findViewById(R.id.image);
        try {
            Glide.with(context)
                    .load(dBconfig.baseuurlimage+thedata.get(position).getCategory_Image())
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.defaultimage)
                    .into(imageView);
        }
        catch (Exception er){
            imageView.setImageResource(R.drawable.defaultimage);
        }

        return view;
    }
}