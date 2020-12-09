package com.fyp.goodsmanagenmentsystem;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class commentsadpater extends ArrayAdapter<dataclassmodule> {
    Context context;
    SharedPreferences pref;
//    SessionManager sessionManager;
    private static final String PREF_NAME = "login_preference";
    int PRIVATE_MODE = 0;
    ArrayList<dataclassmodule> thedata;
    ProgressDialog progressDialog;
    public commentsadpater(Context context, ArrayList<dataclassmodule> thedata)
    {
        super(context, R.layout.commentslayout,thedata);
        this.context=context;
        this.thedata=thedata;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.commentslayout, null);
        TextView tvname = view.findViewById(R.id.tvname);
        TextView tvdate = view.findViewById(R.id.tvdate);
        TextView tvcomments = view.findViewById(R.id.tvcomment);

        tvdate.setText(thedata.get(position).getDatee());
        if(thedata.get(position).getName().equals(""))
        {tvname.setText("New User");

        }
        else {
            tvname.setText(thedata.get(position).getName());
        }
        tvcomments.setText(thedata.get(position).getComment());
              ImageView imageView=view.findViewById(R.id.image);
        try {
          //  imageView.setImageBitmap(Utility.stringToBitmap(thedata.get(position).getImage()));
            imageView.setImageResource(thedata.get(position).getDummyimage());
        }
        catch (Exception er){
           imageView.setImageResource(R.drawable.account);
        }
        return view;
    }
}